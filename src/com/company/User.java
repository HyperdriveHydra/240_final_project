package com.company;
import java.io.*;
import java.util.*;

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
