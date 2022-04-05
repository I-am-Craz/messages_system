package services;

import config.Configurable;
import models.Message;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogService {
    public static void generateLogFiles(ArrayList<Configurable> configs){
        try {
            Path logsDirPath = Path.of(System.getenv("LOGS_DIR"));
            if(!Files.exists(logsDirPath)){
                Files.createDirectory(logsDirPath);
            }

            for (int i = 0; i < configs.size(); i++){
                Path path = Path.of(logsDirPath + "/" +
                        configs.get(i).getClass().getName().substring(8) +
                        i + ".log");
                if(Files.notExists(path)){
                    Files.createFile(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}