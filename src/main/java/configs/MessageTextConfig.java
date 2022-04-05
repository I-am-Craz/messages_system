package configs;

import models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MessageTextConfig implements Configurable{
    private List<String> wordsInText;
    public  MessageTextConfig(List<String> words) {
        this.wordsInText = words;
    }

    public List<String> getWords() {
        return this.wordsInText;
    }

    @Override
    public boolean configMatch(Message message){
        boolean contains = false;
        for(String word : this.getWords()){
            if(message.getText().contains(word)){
                contains = true;
            } else{
                contains = false;
            }
        }
        return contains;
    }

    @Override
    public void askForConfig(Scanner scanner, ArrayList<Configurable> configs){
        while(true){
            System.out.println("Configure by words in the text of the message?");
            if(scanner.nextLine().equals("y")){
                System.out.println("Which words ? Example: word1, word2...");
                configs.add( new MessageTextConfig(Arrays.asList(scanner.nextLine().split(", "))));
            } else {
                break;
            }
        }
    }
}
