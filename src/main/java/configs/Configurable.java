package configs;

import models.Message;

import java.util.ArrayList;
import java.util.Scanner;

public interface Configurable {
     boolean configMatch(Message message);
     void askForConfig(Scanner scanner, ArrayList<Configurable> configs);
}
