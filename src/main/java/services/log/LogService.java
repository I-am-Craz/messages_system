package services.log;

import configs.Configurable;
import configs.queues.MessageQueue;
import org.apache.log4j.Logger;
import repositories.MessageRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogService {
    private static final Logger logger = Logger.getLogger(LogService.class.getName());
    private static final Path LOGS_DIR_PATH = Path.of(System.getenv("LOGS_DIR"));

    public  void generateLogFiles(ArrayList<Configurable> configs, List<MessageQueue> queues){
        try {
            if(!Files.exists(LOGS_DIR_PATH)){
               Files.createDirectory(LOGS_DIR_PATH);
            }

            for (int i = 0; i < configs.size(); i++){
                Path path = Path.of(
                        LOGS_DIR_PATH + "/" +
                             configs.get(i).getClass().getName().substring(8) +
                             i + ".log");
                queues.get(i).setLogFilePath(path);
                if(Files.notExists(path)){
                    Files.createFile(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public  void writeToLogFiles(List<MessageQueue> queues){
        for(MessageQueue queue : queues){
            for (Object o : queue) {
                new Thread(new LogWriter(new File(queue.getLogFilePath().toString()), o.toString())).start();
            }
        }
    }

    private static class LogWriter implements Runnable{
        private File logFile;
        private String messageInfo;

        public LogWriter(File file, String info){
            this.logFile = file;
            this.messageInfo = info;
        }

        @Override
        public void run(){
            try( FileWriter fw = new FileWriter(logFile, true);
                 BufferedWriter bw = new BufferedWriter(fw);){
                 synchronized (bw){
                     bw.write(messageInfo + "\n");
                 }
            } catch (IOException e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}