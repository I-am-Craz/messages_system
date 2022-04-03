package config;

import models.Message;

import java.util.List;

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
}
