package config;

import models.Message;

public interface Configurable {
    public boolean configMatch(Message message);
}
