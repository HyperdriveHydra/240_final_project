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

    //when the user wants to get a password, this method goes through the options of
    //which site/product it is for, then which user of that site it is for, then calls
    //Account.getPassword() on that account
    public String getPassword() {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<String> companies = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        String password;

        //opening their .user file
        File file = new File("users/"+username+".user");
        try {
            Scanner in = new Scanner(file);
            while(in.hasNextLine()) {
                Account account = new Account(in);
                in.nextLine();
                accounts.add(account);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        //asking about product/site and user to determine which account to get the password for
        System.out.println("Which company/product/site do you need the password for?");
        for (Account a : accounts) {
            if(!companies.contains(a.getCompany())) {
                System.out.println(a.getCompany());
                companies.add(a.getCompany());
            }
        }
        String company = scan.nextLine();
        int count = 0;
        for (Account a : accounts) {
            if (a.getCompany().equals(company)) {
                count++;
            }
        }
        if (count > 1) {
            System.out.println("Which user from "+company+" do you want the password for?");
            for (Account a : accounts) {
                if (a.getCompany().equals(company)) {
                    System.out.println(a.getUsername());
                }
            }
            String user = scan.nextLine();
            for (Account a : accounts) {
                if (a.getUsername().equals(user) && a.getCompany().equals(company)) {
                    count = accounts.indexOf(a);
                }
            }
        } else {
            count = companies.indexOf(company);
        }
        password = accounts.get(count).getPassword();
        return password;
    }
}
