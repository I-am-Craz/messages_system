package configs;

import lombok.NonNull;
import models.Message;

import java.util.ArrayList;
import java.util.Scanner;

public interface Configurable {
     boolean configMatch(@NonNull Message message);
     void askForConfig(@NonNull Scanner scanner, @NonNull ArrayList<Configurable> configs);
}
