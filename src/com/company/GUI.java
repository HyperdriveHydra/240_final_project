package com.company;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

enum ButtonType {
    getPass,
    addPass,
    changePass,
    addUser,
    with,
    without,
    site,
    user,
    signIn
}
enum FieldType {
    username,
    site,
    question,
    answer
}
enum State {
    home,
    getPass,
    addPass,
    setSQ,
    changePass,
    addUser
}
class ButtonListener implements ActionListener {
    private ButtonType button;
    private GUI gui;

    public ButtonListener(ButtonType button, GUI gui) {
        this.button = button;
        this.gui = gui;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(button) {
            case getPass:
                gui.setState(State.getPass);
                gui.signIn();
            case addPass:
                gui.setState(State.addPass);
                gui.signIn();
            case addUser:
                gui.setState(State.addUser);
                gui.addUser();
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
        switch(field) {
            case username:
                User user;
                switch(gui.getState()) {
                    case getPass:
                        user = new User(text.getText());
                        //JOptionPane.showMessageDialog(null, user.getPassword());
                    case addPass:
                        user = new User(text.getText());
                        gui.toHome();
                    case changePass:
                        user = new User(text.getText());
                        gui.toHome();
                    case addUser:
                        user = new User(text.getText(), true);
                        gui.toHome();
                }
        }

    }
}

public class GUI extends JLabel{

    private State state;
    private JFrame frame;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
        toHome();
    }
    public void toHome() {
        state = State.home;
        passWorks();
    }

    public void passWorks() {

        frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2,2));
        for (int i = 0; i < 4; i++) {
            ButtonType b = ButtonType.values()[i];
            String name = buttonName(b);
            JButton button = new JButton(name);
            ButtonListener listener = new ButtonListener(b, this);
            button.addActionListener(listener);
            panel.add(button);
        }
        frame.add(panel, BorderLayout.CENTER);
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

    public void addUser() {
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
}
