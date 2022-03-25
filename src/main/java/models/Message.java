package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    private String text;
    private LocalDate sendingDate;
    private User sender;
    private User recipient;
    public boolean isAddedToQueue = false;

    public Message(String text, User sender, User recipient){
        this.text = text;
        this.sendingDate = LocalDate.now();
        this.sender = sender;
        this.recipient = recipient;
    }

    public User getSender(){
        return this.sender;
    }

    public String getMessageInfo(){
        return String.format("Sending date: %s\n" +
                "Sender full name: %s\n" +
                "Sender username: %s\n" +
                "Message text: %s\n" +
                "Recipient full name: %s\n" +
                "Recipient username: %s",
                this.sendingDate.toString(),
                this.sender.getFullName(),
                this.sender.getUserName(),
                this.text,
                this.recipient.getFullName(),
                this.recipient.getUserName());
    }
}