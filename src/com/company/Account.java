package com.company;
import java.util.*;
import static java.lang.Boolean.*;


public class Account {

    //these are all the pieces of data we need to know for a given account on a website
    private String company;
    private String username;
    private String password;
    private Boolean isProtected;
    private String sQuestion_1;
    private String sQuestion_2;

    //this is a constructor for when the user is creating a new account
    public Account(String company, String username, String password, Boolean isProtected) {
        Scanner scan = new Scanner(System.in);
        this.company = company;
        this.username = username;
        this.password = password;
        this.isProtected = isProtected;
        if (isProtected) {
            System.out.print("Enter the first security question that you would like to protect this password: ");
            sQuestion_1 = scan.nextLine();
            System.out.print("Please enter the answer to this security question (case sensitive): ");
            sQuestion_1 += "/" + scan.nextLine();
            System.out.print("Enter the second security question that you would like to protect this password: ");
            sQuestion_2 = scan.nextLine();
            System.out.print("Please enter the answer to this security question (case sensitive): ");
            sQuestion_2 += "/" + scan.nextLine();
        } else {
            sQuestion_1 = "";
            sQuestion_2 = "";
        }
    }

    //initializes an account from a file
    public Account(Scanner in) {
        company = in.nextLine();
        username = in.nextLine();
        password = in.nextLine();
        isProtected = parseBoolean(in.nextLine());
        sQuestion_1 = in.nextLine();
        sQuestion_2 = in.nextLine();
    }

    public String getUsername() {
        return username;
    }
    public String getCompany() {
        return company;
    }

    //returns the password immediately if unprotected, otherwise asks security questions
    //and only returns the password if both are correct
    public String getPassword() {
        Scanner scan = new Scanner(System.in);
        if (isProtected) {
            String q1 = sQuestion_1.substring(0,sQuestion_1.indexOf('/'));
            String a1 = sQuestion_1.substring(sQuestion_1.indexOf('/')+1);
            String q2 = sQuestion_2.substring(0,sQuestion_2.indexOf('/'));
            String a2 = sQuestion_2.substring(sQuestion_2.indexOf('/')+1);
            System.out.println("Security question 1: "+q1+": ");
            String a = scan.nextLine();
            if (!a.equals(a1)) {
                return "Security question 1 failed, password will not be given";
            }
            System.out.println("Security question 2: "+q2+": ");
            a = scan.nextLine();
            if (!a.equals(a2)) {
                return "Security question 2 failed, password will not be given";
            }
        }
        return password;
    }
}
