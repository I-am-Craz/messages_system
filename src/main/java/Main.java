import config.Configurable;
import config.MessageTextConfig;
import config.SenderConfig;
import models.Message;
import repositories.MessageRepository;
import services.ConfigService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        ArrayList<Configurable> configs = generateConfigs();
        ArrayList<Message> messages = new ArrayList<>();
        Scheduler scheduler = new Scheduler();
        scheduler.startScheduleTask(messages, configs);
    }

    private static class Scheduler{
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        public void startScheduleTask(ArrayList<Message> messages, ArrayList<Configurable> configs) {
            final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try {
                            MessageRepository.getMessages(LocalDate.of(2022,3,31), LocalDate.now(), 10, messages);
                            ConfigService.analyze(messages, configs);
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 2, TimeUnit.SECONDS);
        }
    }

    private static ArrayList<Configurable> generateConfigs(){
        ArrayList<Configurable> configs = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        boolean isRunning = true;
        while(isRunning){
            System.out.println("Config by sender? (y or n)");
            if(scan.nextLine().equals("y")){
                System.out.println("Name:");
                configs.add(new SenderConfig(scan.nextLine()));
                isRunning = false;
            }
            else {
                System.out.println("Config by words in message text? (y or n)");
                if(scan.nextLine().equals("y")){
                    System.out.println("Words:");
                    configs.add( new MessageTextConfig(Arrays.asList(scan.nextLine().split(", "))));
                    isRunning = false;
                } else{
                    break;
                }
            }
        }
        return configs;
    }
}
