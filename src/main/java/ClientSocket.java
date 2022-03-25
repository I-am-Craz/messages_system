import controllers.UserController;
import models.Message;
import org.apache.log4j.Logger;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
    static Logger logger = Logger.getLogger(ClientSocket.class.getName());
    public static void main(String[] args){
        try{
            logger.info("The client connected to the server.");
            Socket socket = new Socket("localhost", 8080);
            Message message = UserController.getMessage();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            oos.close();
            socket.close();
        }
        catch (Exception e){
            logger.error("The client has some problems.");
            logger.error(e.getMessage());
        }
        finally {
            logger.info("Disconnected.");
        }
    }
}
