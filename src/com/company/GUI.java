package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.Boolean.parseBoolean;

//enumerations for button and text box types,
//as well as the state of the UI
enum ButtonType {
    getPass,
    addPass,
    changePass,
    addUser,
    with,
    without,
    site,
    user,
}
enum FieldType {
    username,
    site,
    question,
    answer,
    user,
    password,
    prot
}
enum State {
    home,
    getPass,
    addPass,
    setSQ,
    askSQ,
    changePass,
    addUser
}

//listener for my buttons
class ButtonListener implements ActionListener {
    private ButtonType button;
    private GUI gui;
    private String name;

    public ButtonListener(ButtonType button, GUI gui, String name) {
        this.button = button;
        this.gui = gui;
        this.name = name;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (button == ButtonType.getPass) {
            gui.setState(State.getPass);
            gui.signIn();
        } else if (button == ButtonType.addPass) {
            gui.setState(State.addPass);
            gui.signIn();
        } else if (button == ButtonType.addUser) {
            gui.setState(State.addUser);
            gui.signIn();
        } else if (button == ButtonType.changePass) {
            gui.setState(State.changePass);
            gui.signIn();
        } else if (button == ButtonType.site) {
            gui.setCompany(name);
            gui.getAcc();
        } else if (button == ButtonType.user) {
            if (gui.getState() == State.getPass) {
                gui.setState(State.askSQ);
            }
            gui.askSQ(name);
        } else if (button == ButtonType.with) {
            gui.setIsProt(true);
            gui.getAccount().setIsProtected(true);
            gui.setState(State.setSQ);
            gui.addSQ();
        } else if (button == ButtonType.without) {
            gui.getAccount().setPassword(gui.getPassword());
            try {
                PrintWriter clear = new PrintWriter(new FileWriter("users/"+gui.getUser().getUsername()+".user", false));
                clear.append("");
                for (Account a : gui.getAccounts()) {
                    gui.getUser().addAccount(a);
                }
            } catch (IOException error) {
                error.printStackTrace();
            }
            gui.toHome();
        }
    }
}

//listener for my text fields
class TextListener implements ActionListener {
    private FieldType field;
    private JTextField text;
    private GUI gui;

    public TextListener(FieldType field, JTextField text, GUI gui) {
        this.field = field;
        this.text = text;
        this.gui = gui;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (field == FieldType.username) {
            if (gui.getState() == State.getPass || gui.getState() == State.changePass) {
                gui.setUser(new User(text.getText()));
                gui.getSite();
            } else if (gui.getState() == State.addPass) {
                gui.setUser(new User(text.getText()));
                gui.addPass();
            } else if (gui.getState() == State.changePass) {
                gui.setUser(new User(text.getText()));
            } else if (gui.getState() == State.addUser) {
                new User(text.getText(), true);
                gui.toHome();
            }
        } else if (field == FieldType.question) {
            if (gui.getState() == State.askSQ) {
                if (text.getText().substring(0, text.getText().indexOf(':')).equals(gui.getQ1())) {
                    if (!text.getText().substring(text.getText().indexOf(':')+2).equals(gui.getA1())) {
                        JOptionPane.showMessageDialog(null, "Incorrect answer for security question 1. " +
                                "The password will not be given at this time.");
                        gui.toHome();
                    } else {
                        gui.setAnswers(true);
                    }
                } else {
                    if (text.getText().substring(text.getText().indexOf(':')+2).equals(gui.getA2())) {
                        if (gui.getAnswers()) {
                            JOptionPane.showMessageDialog(null, gui.getAccount().getPassword());
                            gui.toHome();
                        } else {
                            JOptionPane.showMessageDialog(null, "Security question 1 not answered.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect answer for security question 2. " +
                                "The password will not be given at this time.");
                        gui.toHome();
                    }
                }
            } else if (gui.getState() == State.addPass) {
                if (text.getText().charAt(0)=='F') {
                    gui.setQ1(text.getText().substring(text.getText().indexOf(':')+2));
                } else {
                    gui.setQ2(text.getText().substring(text.getText().indexOf(':')+2));
                }
            } else if (gui.getState() == State.changePass) {
                if (text.getText().substring(0, text.getText().indexOf(':')).equals(gui.getQ1())) {
                    if (!text.getText().substring(text.getText().indexOf(':')+2).equals(gui.getA1())) {
                        JOptionPane.showMessageDialog(null, "Incorrect answer for security question 1. " +
                                "The password will not be changed at this time.");
                        gui.toHome();
                    } else {
                        gui.setAnswers(true);
                    }
                } else {
                    if (text.getText().substring(text.getText().indexOf(':')+2).equals(gui.getA2())) {
                        if (gui.getAnswers()) {
                            gui.changePassword();
                        } else {
                            JOptionPane.showMessageDialog(null, "Security question 1 not answered.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect answer for security question 2. " +
                                "The password will not be changed at this time.");
                        gui.toHome();
                    }
                }
            } else if (gui.getState() == State.setSQ) {
                if (text.getText().charAt(0)=='F') {
                    gui.setQ1(text.getText().substring(text.getText().indexOf(':')+2));
                } else {
                    gui.setQ2(text.getText().substring(text.getText().indexOf(':')+2));
                }
            }
        } else if (field == FieldType.answer) {
            if (gui.getState() == State.askSQ) {
                if (text.getText().substring(0, text.getText().indexOf(':')).equals(gui.getQ1())) {
                    if (!text.getText().substring(text.getText().indexOf(':')+2).equals(gui.getA1())) {
                        JOptionPane.showMessageDialog(null, "Incorrect answer for security question 1. " +
                                "The password will not be given at this time.");
                        gui.toHome();
                    } else {
                        gui.setAnswers(true);
                    }
                } else {
                    if (text.getText().substring(text.getText().indexOf(':')+2).equals(gui.getA2())) {
                        if (gui.getAnswers()) {
                            JOptionPane.showMessageDialog(null, gui.getAccount().getPassword());
                            gui.toHome();
                        } else {
                            JOptionPane.showMessageDialog(null, "Security question 1 not answered.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect answer for security question 2. " +
                                "The password will not be given at this time.");
                        gui.toHome();
                    }
                }
            } else if (gui.getState() == State.addPass) {
                if (text.getText().charAt(0)=='F') {
                    gui.setA1(text.getText().substring(text.getText().indexOf(':')+2));
                } else {
                    gui.setA2(text.getText().substring(text.getText().indexOf(':')+2));
                    gui.getUser().addAccount(new Account(gui.getCompany(), gui.getUsername(), gui.getPassword(), gui.getIsProt(),
                            gui.getQ1()+"/"+gui.getA1(), gui.getQ2()+"/"+gui.getA2()));
                    gui.toHome();
                }
            } else if (gui.getState() == State.setSQ) {
                if (text.getText().charAt(0)=='F') {
                    gui.getAccount().setSQ1(gui.getQ1()+"/"+text.getText().substring(text.getText().indexOf(':')+2));
                } else {
                    gui.getAccount().setSQ2(gui.getQ2()+"/"+text.getText().substring(text.getText().indexOf(':')+2));
                    try {
                        PrintWriter clear = new PrintWriter(new FileWriter("users/"+gui.getUser().getUsername()+".user", false));
                        clear.append("");
                        for (Account a : gui.getAccounts()) {
                            gui.getUser().addAccount(a);
                        }
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                    gui.toHome();
                }
            }
        } else if (field == FieldType.site) {
            gui.setCompany(text.getText().substring(text.getText().indexOf(':')+2));
        } else if (field == FieldType.user) {
            gui.setUsername(text.getText().substring(text.getText().indexOf(':')+2));
        } else if (field == FieldType.password) {
            gui.setPassword(text.getText().substring(text.getText().indexOf(':')+2));
            if (gui.getState() == State.changePass) {
                gui.getAccount().setPassword(text.getText().substring(text.getText().indexOf(':')+2));
            }
        } else if (field == FieldType.prot) {
            gui.setIsProt(parseBoolean(text.getText().substring(text.getText().indexOf(':')+2)));
            if (gui.getIsProt()) {
                gui.addSQ();
            } else {
                gui.getUser().addAccount(new Account(gui.getCompany(), gui.getUsername(), gui.getPassword(), gui.getIsProt(),
                        gui.getQ1()+"/"+gui.getA1(), gui.getQ2()+"/"+gui.getA2()));
                gui.toHome();
            }
        }
    }
}

//the user interface, with all its instance variables and methods
public class GUI extends JLabel{

    //oh my, that's a lot of instance variables!
    private State state;
    private JFrame frame;
    private User user;
    private Account account;
    private String company;
    private String username;
    private String password;
    private Boolean isProt;
    private String a1;
    private String a2;
    private String q1;
    private String q2;
    private Boolean answers;
    private ArrayList<Account> accounts;
    private ArrayList<String> companies;

    //getters for instance variables
    public State getState() {
        return state;
    }
    public User getUser() {
        return user;
    }
    public String getCompany() {
        return company;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getIsProt() {
        return isProt;
    }
    public String getA1() {
        return a1;
    }
    public String getA2() {
        return a2;
    }
    public String getQ1() {
        return q1;
    }
    public String getQ2() {
        return q2;
    }
    public Boolean getAnswers() {
        return answers;
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public Account getAccount() {
        return account;
    }

    //setters for instance variables
    public void setState(State state) {
        this.state = state;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setIsProt(Boolean isProt) {
        this.isProt = isProt;
    }
    public void setQ1(String q) {
        this.q1 = q;
    }
    public void setQ2(String q) {
        this.q2 = q;
    }
    public void setA1(String a) {
        this.a1 = a;
    }
    public void setA2(String a) {
        this.a2 = a;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setAnswers(Boolean a) {
        answers = a;
    }

    //given a FieldType return a string to title the text field
    public static String fieldName(FieldType f) {
        switch(f) {
            case username:
                return "Username: ";
            case question:
                return "Security question: ";
            case answer:
                return "Answer: ";
            case site:
                return "Site: ";
            case user:
                return "User: ";
            case password:
                return "Password: ";
            case prot:
                return "Protected (true/false): ";
        }
        throw new IllegalArgumentException("Invalid field type"+f.name());
    }

    //given a ButtonType return a string to title that button
    public static String buttonName(ButtonType b) {
        switch(b) {
            case getPass:
                return "Get a password";
            case addPass:
                return "Add a new password";
            case changePass:
                return "Change a password";
            case addUser:
                return "Add a PassWorks user";
            case with:
                return "Add/Change security questions";
            case without:
                return "Change password only";
        }
        throw new IllegalArgumentException("Invalid button type"+b.name());
    }

    //initializes the JFrame, then calls toHome()
    public GUI() {
        frame = new JFrame();
        toHome();
    }

    //resets all instance variables and returns to the home screen
    public void toHome() {

        state = State.home;
        accounts = new ArrayList<>();
        companies = new ArrayList<>();
        company = "";
        username = "";
        password = "";
        isProt = false;
        a1 = "";
        a2 = "";
        q1 = "";
        q2 = "";
        answers = false;
        passWorks();
    }

    //draws the PassWorks home screen, with the four basic option buttons of
    //get password, add password, change password, and add PassWorks user
    public void passWorks() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2,2));
        for (int i = 0; i < 4; i++) {
            ButtonType b = ButtonType.values()[i];
            String name = buttonName(b);
            JButton button = new JButton(name);
            ButtonListener listener = new ButtonListener(b, this, name);
            button.addActionListener(listener);
            panel.add(button);
        }
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PassWorks (Pre-alpha)");
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //draws the sign-in page, which is included in 3/4 PassWorks functions
    public void signIn() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(200, 30, 200, 30));
        panel.setLayout(new GridLayout(1,1));
        FieldType f = FieldType.username;
        JTextField text = new JTextField("Username: ");
        TextListener listener = new TextListener(f, text, this);
        text.addActionListener(listener);
        panel.add(text);
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //draws the list of sites for which the user has an account for as buttons
    public void getSite() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        //opening their .user file
        File file = new File("users/"+user.getUsername()+".user");
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

        //asking about product/site and user to determine which account to get or change the password for
        for (Account a : accounts) {
            if(!companies.contains(a.getCompany())) {
                companies.add(a.getCompany());
                ButtonType b = ButtonType.site;
                JButton button = new JButton(a.getCompany());
                ButtonListener listener = new ButtonListener(b, this, a.getCompany());
                button.addActionListener(listener);
                panel.add(button);
            }
        }
        panel.setLayout(new GridLayout(companies.size(),1));
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //draws the list of accounts that a user has on a site as buttons
    public void getAcc() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        int count = 0;
        for (Account a : accounts) {
            if (a.getCompany().equals(company)) {
                count++;
                ButtonType b = ButtonType.user;
                JButton button = new JButton(a.getUsername());
                ButtonListener listener = new ButtonListener(b, this, a.getUsername());
                button.addActionListener(listener);
                panel.add(button);
            }
        }
        panel.setLayout(new GridLayout(count,1));
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //if getting a password, it shows the password if unprotected or asks security questions
    //first if protected, if changing a password it lets you change the password immediately
    //if unprotected, or asks security questions first if protected
    public void askSQ(String acc) {
        for (Account a : accounts) {
            if (a.getCompany().equals(company) && a.getUsername().equals(acc)) {
                this.account = a;
                if (a.getIsProtected()) {
                    JPanel panel = new JPanel();
                    ArrayList<String> qs = new ArrayList<>();
                    panel.setBorder(BorderFactory.createEmptyBorder(150, 30, 150, 30));
                    panel.setLayout(new GridLayout(2,1));
                    q1 = a.getSQ_1().substring(0,a.getSQ_1().indexOf('/'));
                    qs.add(q1);
                    a1 = a.getSQ_1().substring(a.getSQ_1().indexOf('/')+1);
                    q2 = a.getSQ_2().substring(0,a.getSQ_2().indexOf('/'));
                    qs.add(q2);
                    a2 = a.getSQ_2().substring(a.getSQ_2().indexOf('/')+1);
                    for (String q : qs) {
                        FieldType f = FieldType.question;
                        JTextField text = new JTextField(q+": ");
                        TextListener listener = new TextListener(f, text, this);
                        text.addActionListener(listener);
                        panel.add(text);
                    }
                    frame.setContentPane(panel);
                    frame.pack();
                    frame.setSize(500,500);
                    frame.setVisible(true);
                } else {
                    if (state == State.askSQ){
                        JOptionPane.showMessageDialog(null, a.getPassword());
                        toHome();
                    } else if (state == State.changePass) {
                        changePassword();
                    }
                }
            }
        }
    }

    //draws the screen for adding a new Account/password to your PassWorks user
    public void addPass() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4,1));
        ArrayList<FieldType> fields = new ArrayList<>();
        fields.add(FieldType.site);
        fields.add(FieldType.user);
        fields.add(FieldType.password);
        fields.add(FieldType.prot);
        for (FieldType field : fields) {
            JTextField text = new JTextField(fieldName(field));
            TextListener listener = new TextListener(field, text, this);
            text.addActionListener(listener);
            panel.add(text);
        }
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //draws the screen for adding or changing the security questions of an Account
    public void addSQ() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4,1));
        ArrayList<FieldType> fields = new ArrayList<>();
        fields.add(FieldType.question);
        fields.add(FieldType.answer);
        fields.add(FieldType.question);
        fields.add(FieldType.answer);
        int i = 0;
        for (FieldType field : fields) {
            JTextField text;
            if (i < 2) {
                text = new JTextField("First "+fieldName(field));
                TextListener listener = new TextListener(field, text, this);
                text.addActionListener(listener);
            } else {
                text = new JTextField("Second "+fieldName(field));
                TextListener listener = new TextListener(field, text, this);
                text.addActionListener(listener);
            }
            panel.add(text);
            i++;
        }
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //draws the screen for changing a password, showing the old password, having a
    //text field for the new password, and buttons at the bottom for whether
    //the user wants to change/add the security questions as well
    public void changePassword() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 100, 30));
        panel.setLayout(new GridLayout(2,2));
        JTextField text = new JTextField("Old password: "+account.getPassword());
        panel.add(text);
        FieldType f = FieldType.password;
        text = new JTextField("New password: ");
        TextListener listener = new TextListener(f, text, this);
        text.addActionListener(listener);
        panel.add(text);
        for (int i = 4; i < 6; i++) {
            ButtonType b = ButtonType.values()[i];
            String name = buttonName(b);
            JButton button = new JButton(name);
            ButtonListener bListener = new ButtonListener(b, this, name);
            button.addActionListener(bListener);
            panel.add(button);
        }
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }
}
