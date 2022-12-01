package com.company;
import java.io.*;
import java.util.*;

//A PassWorks user, which contains multiple Accounts stored in a .user file
public class User {
    private String username;

    //constructor for making a new PassWorks user
    public User(String username, Boolean n) {
        username = username.substring(username.indexOf(':')+2);
        this.username = username;
        File file = new File("users/"+username+".user");
        try {
            if (!file.createNewFile()) {
                System.out.println("User name already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred with the User name given.");
            e.printStackTrace();
        }
    }
    //constructor for given PassWorks user
    public User(String username) {
        username = username.substring(username.indexOf(':')+2);
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    //Writes an Account's details into the .user file, allowing it
    //to work for both adding new accounts and changing details of
    //old ones
    public void addAccount(Account account) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("users/"+username+".user", true));
            account.save(out);
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
