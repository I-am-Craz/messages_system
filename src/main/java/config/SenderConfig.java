package config;

import models.Message;

public class SenderConfig implements Configurable{
    private String sender;
    public SenderConfig(String sender) {
        this.sender = sender;
    }
    public String getSender() {
        return this.sender;
    }

    @Override
    public boolean configMatch(Message message) {
        if(message.getSender().equals(this.getSender())){
            return true;
        }
        return false;
    }
}
