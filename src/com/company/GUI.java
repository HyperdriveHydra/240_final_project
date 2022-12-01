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
        } else if (button == ButtonType.site) {
            gui.setCompany(name);
            gui.getAcc();
        } else if (button == ButtonType.user) {
            if (gui.getState() == State.getPass) {
                gui.setState(State.askSQ);
                gui.askSQ(name);
            }
        }
    }
}

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
            if (gui.getState() == State.getPass) {
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
            }
        } else if (field == FieldType.site) {
            gui.setCompany(text.getText().substring(text.getText().indexOf(':')+2));
        } else if (field == FieldType.user) {
            gui.setUsername(text.getText().substring(text.getText().indexOf(':')+2));
        } else if (field == FieldType.password) {
            gui.setPassword(text.getText().substring(text.getText().indexOf(':')+2));
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

public class GUI extends JLabel{

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
    public Account getAccount() {
        return account;
    }

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
        }
        throw new IllegalArgumentException("Invalid button type"+b.name());
    }

    public GUI() {
        frame = new JFrame();
        toHome();
    }

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

        //asking about product/site and user to determine which account to get the password for
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

    //returns the password immediately if unprotected, otherwise asks security questions
    //and only returns the password if both are correct
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
                    JOptionPane.showMessageDialog(null, a.getPassword());
                    toHome();
                }
            }
        }
    }

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
}
