package services;

import config.Configurable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class LogService {
    public static void generateLogFiles(ArrayList<Configurable> configs){
        try {
            Path logsDirPath = Path.of(System.getenv("LOGS_DIR"));
            if(!Files.exists(logsDirPath)){
               Files.createDirectory(logsDirPath);
            } else{
                File[] files = new File(logsDirPath.toString()).listFiles();
                for(File file : files){
                    file.delete();
                }
            }

            for (Configurable config : configs){
                Files.createFile(Path.of(logsDirPath + "/" +
                    config.getClass().getName().substring(7) +
                    configs.indexOf(config) + ".log" ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}