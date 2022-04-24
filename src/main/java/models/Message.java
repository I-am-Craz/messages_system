package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
public class Message {
    @NonNull
    private String text;
    private LocalDate sendingDate;
    private String sender;
    private String recipient;
}