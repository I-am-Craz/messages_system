import models.Message;
import models.User;
import org.apache.log4j.Logger;
import repoitories.MessageRepository;
import services.MessageService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{
    static Logger logger = Logger.getLogger(ServerSocket.class.getName());
    Socket serverClient;
    ServerThread(Socket inSocket){
        serverClient = inSocket;
    }
    public void run(){
        try{
            ObjectInputStream ois = new ObjectInputStream(serverClient.getInputStream());
            Message message = (Message) ois.readObject();
            MessageRepository.addMessageToList(message);
            logger.info("The message is successfully added to another messages.");
            ois.close();
            serverClient.close();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
}
