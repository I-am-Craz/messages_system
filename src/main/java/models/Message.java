package models;

import java.sql.Date;
import java.time.LocalDate;

public class Message {
    private String text;
    private LocalDate sendingDate;
    private String sender;
    private String recipient;

    public Message(String text, Date date, String sender, String recipient){
        this.text = text;
        this.sendingDate = date.toLocalDate();
        this.sender = sender;
        this.recipient = recipient;
    }

    @Override
    public String toString(){
        return String.format(
                "Sending date: %s\n" +
                "Sender : %s\n" +
                "Message text: %s\n" +
                "Recipient: %s\n",
                this.sendingDate,
                this.sender,
                this.text,
                this.recipient);
    }

    public String getText(){
        return this.text;
    }
    public String getSender(){
        return this.sender;
    }
}