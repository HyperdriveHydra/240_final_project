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
    public Boolean getIsProtected() {
        return isProtected;
    }
    public String getSQ_1() {
        return sQuestion_1;
    }
    public String getSQ_2() {
        return sQuestion_2;
    }
    public String getPassword() {
        return password;
    }
}
