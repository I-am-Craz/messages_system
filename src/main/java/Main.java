import configs.Configurable;
import configs.MessageTextConfig;
import configs.SenderConfig;
import configs.queues.MessageQueue;
import lombok.NonNull;
import models.Message;
import repositories.MessageRepository;
import services.config.ConfigService;
import services.log.LogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Main {
    static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start date (yyyy-mm-dd)");
        LocalDate startDate = askForDate(scanner);
        System.out.println("Enter end date (yyyy-mm-dd)");
        LocalDate endDate = askForDate(scanner);

        ArrayList<Configurable> configs = generateConfigs(scanner);
        ArrayList<Message> messages = new ArrayList<>();
        Scheduler scheduler = new Scheduler();
        scheduler.startScheduleTask(startDate, endDate, messages, configs);
    }

    private static class Scheduler{
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        public void startScheduleTask(LocalDate startDate, LocalDate endDate, ArrayList<Message> messages, ArrayList<Configurable> configs) {
            final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try {
                            MessageRepository.getMessages(startDate, endDate, 10, messages);
                            ConfigService configService = new ConfigService();
                            List<MessageQueue> queues =  configService.createMessageQueues(messages, configs);
                            LogService logService = new LogService();
                            logService.generateLogFiles(configs, queues);
                            logService.writeToLogFiles(queues);
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 10, TimeUnit.MINUTES);
        }
    }

    private static ArrayList<Configurable> generateConfigs(@NonNull Scanner scanner){
        ArrayList<Configurable> configs = new ArrayList<>();
        while(true){
            SenderConfig senderConfig = new SenderConfig(null);
            senderConfig.askForConfig(scanner, configs);
            MessageTextConfig messageTextConfig = new MessageTextConfig(null);
            messageTextConfig.askForConfig(scanner, configs);
            break;
        }
        return configs;
    }

    private static LocalDate askForDate(Scanner scan){
        String date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        date = scan.nextLine();
        if(DATE_PATTERN.matcher(date).matches()){
            return LocalDate.parse(date, formatter);
        } else{
            System.out.println("Wrong date type. Try again");
            System.exit(0);
            return null;
        }
    }
}
