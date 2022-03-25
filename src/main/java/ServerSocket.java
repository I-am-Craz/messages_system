import services.MessageService;

import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ServerSocket {
    static Logger logger = Logger.getLogger(ServerSocket.class.getName());

    public static void main(String[] args){
        try{
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(8080);
            logger.info("The server start working.");
            ScheduledTask scheduledTask = new ScheduledTask();
            scheduledTask.startScheduleTask();
            logger.info("The schedule task was at");
            while (true) {
                Socket s = serverSocket.accept();
                Thread st = new Thread(new ServerThread(s));
                st.start();
                logger.info("A client was connected.");
            }
        }
        catch (Throwable t){
            logger.error("The server was crashed.");
        }
    }

    private static class ScheduledTask{
        private final ScheduledExecutorService scheduler = Executors
                .newScheduledThreadPool(1);

        public void startScheduleTask() {
            final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try {
                            MessageService.checkWasNewMessageAddedInMessages();
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 10, TimeUnit.MINUTES);
        }
    }
}
