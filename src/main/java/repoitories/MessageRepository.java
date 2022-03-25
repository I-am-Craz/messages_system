package repoitories;

import models.Message;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {
    static Logger logger = Logger.getLogger(MessageRepository.class.getName());
    public static final List<Message> messages = new ArrayList<>();

    public static void addMessageToList(Message message){
        messages.add(message);
        logger.info("A message was added into the messages list.");
    }

}
