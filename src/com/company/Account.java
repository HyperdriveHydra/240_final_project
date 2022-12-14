package com.company;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Boolean.*;

//an individual account on a specific company/site
public class Account {

    //these are all the pieces of data we need to know for a given account on a website
    private String company;
    private String username;
    private String password;
    private Boolean isProtected;
    private String sQuestion_1;
    private String sQuestion_2;

    //this is a constructor for when the user is creating a new account
    public Account(String company, String username, String password, Boolean isProtected, String q1, String q2) {
        this.company = company;
        this.username = username;
        this.password = password;
        this.isProtected = isProtected;
        if (isProtected) {
            sQuestion_1 = q1;
            sQuestion_2 = q2;
        } else {
            sQuestion_1 = "-";
            sQuestion_2 = "-";
        }
    }

    //method for writing the account's contents into the user file,
    //for both adding and changing
    public void save(PrintWriter out) {
        out.append(company+"\n");
        out.append(username+"\n");
        out.append(password+"\n");
        out.append(isProtected.toString()+"\n");
        out.append(sQuestion_1+"\n");
        out.append(sQuestion_2+"\n\n");
        out.flush();
        out.close();
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

    //getters for account info, used lots in GUI.java
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

    //setters accessed when changing a password
    public void setPassword(String password) {
        this.password = password;
    }
    public void setSQ1(String sQuestion_1) {
        this.sQuestion_1 = sQuestion_1;
    }
    public void setSQ2(String sQuestion_2) {
        this.sQuestion_2 = sQuestion_2;
    }
    public void setIsProtected(Boolean isProtected) {
        this.isProtected = isProtected;
    }
}
