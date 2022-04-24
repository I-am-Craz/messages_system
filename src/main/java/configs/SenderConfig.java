package configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import models.Message;

import java.util.ArrayList;
import java.util.Scanner;

@Getter
@AllArgsConstructor
public class SenderConfig implements Configurable{
    private String sender;

    @Override
    public boolean configMatch(@NonNull Message message) {
        return message.getSender().equals(this.getSender());
    }
    @Override
    public void askForConfig(@NonNull Scanner scanner, @NonNull ArrayList<Configurable> configs){
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
