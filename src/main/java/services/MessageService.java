package services;

import models.Message;
import models.User;
import org.apache.log4j.Logger;
import repoitories.MessageRepository;

import java.util.LinkedList;
import java.util.Queue;

public class MessageService {
    static Logger logger = Logger.getLogger(MessageService.class.getName());

    public static Queue<String> chiefsMessagesQueue = new LinkedList<>();
    public static Queue<String> subordinatesMessagesQueue = new LinkedList<>();
    public static Queue<String> clientsMessagesQueue = new LinkedList<>();

    public static void addMessageToQueue(Message message){
        User messageSender = message.getSender();
        if(messageSender.checkIsChief()){
            chiefsMessagesQueue.add(message.getMessageInfo());
            message.isAddedToQueue = true;
            logger.info(String.format("The message info was added in a queue for chiefs.\nMessage info:\n%s", message.getMessageInfo()));
        }
        else if(messageSender.checkIsSubordinate()){
            subordinatesMessagesQueue.add(message.getMessageInfo());
            message.isAddedToQueue = true;
            logger.info(String.format("The message info was added in a queue for subordinates.\nMessage info:\n%s", message.getMessageInfo()));
        }
        else if(messageSender.checkIsClient()){
            clientsMessagesQueue.add(message.getMessageInfo());
            message.isAddedToQueue = true;
            logger.info(String.format("The message info was added in a queue for clients.\nMessage info:\n%s", message.getMessageInfo()));
        }
        else{
            logger.info("The message info wasn't added in a some queue.");
        }
    }

    public static void checkWasNewMessageAddedInMessages(){
        logger.info(String.format("Messages count in messages list: %d\n", MessageRepository.messages.size()));
        for(int i = 0; i < MessageRepository.messages.size(); i++){
            if(!MessageRepository.messages.get(i).isAddedToQueue){
                addMessageToQueue(MessageRepository.messages.get(i));
            }
            else{
                continue;
            }
        }
    }
}
