import models.Message;
import repositories.MessageRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        ArrayList<Message> messages = new ArrayList<>();
//        Scheduler scheduler = new Scheduler();
//        scheduler.startScheduleTask(messages);
        MessageRepository.getMessages(LocalDate.of(2022,3,31), LocalDate.now(), 10, messages);
        System.out.println(messages.size());
    }

    private static class Scheduler{
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        public void startScheduleTask(ArrayList<Message> messages) {
            final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try {
                            MessageRepository.getMessages(LocalDate.of(2022,3,31), LocalDate.now(), 10, messages);
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 10, TimeUnit.SECONDS);
        }
    }
}
