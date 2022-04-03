package services;

import config.Configurable;
import config.MessageTextConfig;
import config.SenderConfig;
import models.Message;

import java.util.*;

public class ConfigService {
    public static List<Queue> analyze(ArrayList<Message> messages, ArrayList<Configurable> configs){
        List<Queue> queues = new ArrayList<>();
        for(Configurable conf : configs){
           LinkedList<Message> queue = new LinkedList<>();
           queues.add(queue);
           for(Message message : messages){
               if(conf.configMatch(message)){
                   queue.add(message);
               }
           }
        }
        return queues;
    }
}

/// insert into messages (text, sender, recipient) values('hello, I want to sell my soul to the devil.', 'sender2', 'recipient');
