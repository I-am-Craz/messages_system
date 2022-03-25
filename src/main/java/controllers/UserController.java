package controllers;

import models.Message;
import models.User;
import org.apache.log4j.Logger;
import repoitories.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class UserController {
    static Logger logger = Logger.getLogger(UserController.class.getName());

    private static User messageSender = null;

    public static void login() throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            if(br.readLine().equals("exit")){
                disconnect();
            }
            else{
                System.out.println("Do you have a account? Type y or n.");
                if(br.readLine().equals("y")){
                    System.out.println("Enter your username");
                    String username = br.readLine();
                    if(UserRepository.getUserByUserName(username) != null){
                        messageSender = UserRepository.getUserByUserName(username);
                        logger.info("The client was logged in.");
                    }
                }else{
                    System.out.println("Create new account? y or n.");
                    if(br.readLine().equals("y")){
                        messageSender = createNewAccount(br);
                        logger.info("The client created a new account.");
                    }else{
                        disconnect();
                    }
                }
            }
        }

        public static User createNewAccount(BufferedReader br){
            User user = null;
            try{
                System.out.println("Enter a firstname");
                String firstname = br.readLine().trim();
                System.out.println("Enter a lastname");
                String lastname = br.readLine().trim();
                System.out.println("Enter a username");
                String username = br.readLine().trim();
                System.out.println("Are you a chief? (true/false)");
                boolean is_chief = Boolean.getBoolean(br.readLine().trim().toLowerCase(Locale.ROOT));
                System.out.println("Are you a subordinate? (true/false)");
                boolean is_subordinate = Boolean.getBoolean(br.readLine().trim().toLowerCase(Locale.ROOT));
                System.out.println("Are you a client? (true/false)");
                boolean is_client = Boolean.getBoolean(br.readLine().trim().toLowerCase(Locale.ROOT));

                UserRepository.createUser(firstname, lastname, username, is_chief, is_subordinate, is_client);
                user = UserRepository.getUserByUserName(username);
            }
            catch (IOException e){
                logger.error("Can't create a new account.");
                logger.error(e.getMessage());
            }
            return user;
    }

    public static Message getMessage() throws IOException {
        login();
        Message message = null;
        if(messageSender != null){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a message text.");
            String messageText = br.readLine();
            System.out.println("Enter the recipient's username.");
            User messageRecipient = UserRepository.getUserByUserName(br.readLine());
            if(messageRecipient != null){
                message = new Message(messageText, messageSender, messageRecipient);
                logger.info("The message was sended.");
            } else{
                System.out.println("No user with this username.");
                disconnect();
            }
        }
        return message;
    }

    public static void disconnect(){
        logger.info("Disconnected.");
        System.exit(0);
    }
}
