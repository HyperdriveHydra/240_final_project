package com.company;

public class Main {

    public static void main(String[] args) {

        new GUI();
        User test = loadUser("test");
        System.out.println(test.getPassword());
    }

    public static User loadUser(String username) {
        User user = new User(username);
        return user;
    }

    public static void addUser() {
        User newUser = new User();
    }
}