package services.config;

import configs.Configurable;
import configs.queues.MessageQueue;
import lombok.NonNull;
import models.Message;

import java.util.*;

public class ConfigService {
    public List<MessageQueue> createMessageQueues(@NonNull ArrayList<Message> messages,
                                                  @NonNull ArrayList<Configurable> configs){
        List<MessageQueue> queues = new ArrayList<>();
        for(Configurable conf : configs){
            MessageQueue queue = new MessageQueue();
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