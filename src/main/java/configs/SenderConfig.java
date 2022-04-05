package configs;

import models.Message;

import java.util.ArrayList;
import java.util.Scanner;

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
    @Override
    public void askForConfig(Scanner scanner, ArrayList<Configurable> configs){
        while(true){
            System.out.println("Configure by message sender?");
            if(scanner.nextLine().equals("y")){
                System.out.println("Sender name:");
                configs.add(new SenderConfig(scanner.nextLine()));
            } else {
                break;
            }
        }
    }
}
