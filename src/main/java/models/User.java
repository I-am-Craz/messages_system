package models;

import java.io.Serializable;

public class User implements Serializable {
    private String firsName;
    private String lastName;
    private String userName;
    private boolean isChief;
    private boolean isSubordinate;
    private boolean isClient;


    public User(String firstName, String lastName, String userName,
                boolean isChief, boolean isSubordinate, boolean isClient){
        this.firsName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isChief = isChief;
        this.isSubordinate = isSubordinate;
        this.isClient = isClient;
    }

    public String getFirstName(){
        return this.firsName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getFullName(){
        return String.format("%s %s", this.firsName, this.lastName);
    }
    public String getUserName(){
        return this.userName;
    }
    public boolean checkIsChief(){
        return this.isChief;
    }
    public boolean checkIsSubordinate(){
        return this.isSubordinate;
    }
    public boolean checkIsClient(){
        return this.isClient;
    }
}
