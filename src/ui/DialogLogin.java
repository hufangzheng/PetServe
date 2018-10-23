package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import model.*;
import control.UserManager;

public class DialogLogin extends JDialog {
    private JPanel buttonPane = new JPanel();
    private JPanel textPane = new JPanel();
    private JButton jbtLogin = new JButton("Login");
    private JButton jbtRegister = new JButton("Register");
    private JButton jbtQuit = new JButton("Quit");
    private JLabel jlbUser = new JLabel("User Id");
    private JLabel jlbPassword = new JLabel("Password");
    private JTextField jtfUserId = new JTextField(20);
    private JPasswordField jpfPassword = new JPasswordField(20);

    public DialogLogin(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        /*buttonPane*/
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(jbtLogin);
        jbtLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserManager userManager = new UserManager();
                String userId = new String(jtfUserId.getText());
                String password = new String(jpfPassword.getPassword());

                try {
                    bean_user_information user = userManager.loadUser(userId);
                    if (userId == null)
                        JOptionPane.showMessageDialog(null, "Please input UserId", "Hint", JOptionPane.ERROR_MESSAGE);
                    if (user == null)
                        JOptionPane.showMessageDialog(null, "The user is not exist.", "Hint", JOptionPane.ERROR_MESSAGE);
                    else if (password.equals(user.getPassword())) {
                        UserManager.currentUser = user;         //set current User
                        setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The password is not mach.", "Hint", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Hint",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(jbtRegister);
        jbtRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogRegister register = new DialogRegister(null, "Register", true);
                register.setLocationRelativeTo(null);
                register.setVisible(true);
            }
        });
        buttonPane.add(jbtQuit);
        jbtQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);

        /*textPane*/
        textPane.add(jlbUser);
        textPane.add(jtfUserId);
        textPane.add(jlbPassword);
        textPane.add(jpfPassword);
        this.getContentPane().add(textPane, BorderLayout.CENTER);

        /*Location*/
        this.setLocationRelativeTo(frame);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}