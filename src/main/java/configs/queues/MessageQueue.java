package configs.queues;

import models.Message;

import java.nio.file.Path;
import java.util.LinkedList;

public class MessageQueue extends LinkedList<Message> {
    private Path logFilePath;
    public MessageQueue(){
        super();
    }
    public void setLogFilePath(Path path){
        this.logFilePath = path;
    }
    public Path getLogFilePath(){
        return this.logFilePath;
    }
}