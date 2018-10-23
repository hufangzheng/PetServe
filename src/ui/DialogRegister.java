package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.UserManager;
import model.*;

public class DialogRegister extends JDialog {
    private bean_user_information user;

    private JPanel checkPane = new JPanel();
    private JPanel buttonPane = new JPanel();
    private JPanel textPane = new JPanel();
    private JLabel jlbUserId = new JLabel("User ID:");
    private JLabel jlbUserName = new JLabel("User Name:");
    private JLabel jlbPassword = new JLabel("Password");
    private JLabel jlbPhone = new JLabel("Phone Number:");
    private JLabel jlbEmail = new JLabel("E-mail Address:");
    private JLabel jlbOtherContact = new JLabel("Other Contact:");
    private JTextField jtfUserId = new JTextField(20);
    private JTextField jtfUserName = new JTextField(20);
    private JPasswordField jpfPassword = new JPasswordField(20);
    private JTextField jtfPhone = new JTextField(20);
    private JTextField jtfUserEmail = new JTextField(20);
    private JTextArea jtfOtherContact = new JTextArea(7, 20);

    private JCheckBox jcbIsAdministrator = new JCheckBox("Administrator", false);
    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtCancel = new JButton("Cancel");

    public DialogRegister(JDialog dialog,String title, boolean model) {
        super(dialog, title, model);

        checkPane.add(jcbIsAdministrator, FlowLayout.LEFT);
        textPane.add(jlbUserId);
        textPane.add(jtfUserId);
        textPane.add(jlbUserName);
        textPane.add(jtfUserName);
        textPane.add(jlbPassword);
        textPane.add(jpfPassword);
        textPane.add(jlbPhone);
        textPane.add(jtfPhone);
        textPane.add(jlbEmail);
        textPane.add(jtfUserEmail);
        textPane.add(jlbOtherContact);
        textPane.add(jtfOtherContact);
        this.getContentPane().add(checkPane, BorderLayout.NORTH);
        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.setSize(300, 500);

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = jtfUserId.getText();
                String userName = jtfUserName.getText();
                String password = new String(jpfPassword.getPassword());
                String phone = jtfPhone.getText();
                String email = jtfUserEmail.getText();
                String otherContact = jtfOtherContact.getText();
                String authority = "";
                if (jcbIsAdministrator.isSelected() == false)
                    authority = "user";
                else
                    authority = "administrator";
                user = new bean_user_information();
                user.setUser_id(userId);
                user.setUser_name(userName);
                user.setPassword(password);
                user.setPhone_number(phone);
                user.setEmail(email);
                user.setOther_contact(otherContact);
                user.setAuthority(authority);
                try {
                    UserManager userManager = new UserManager();
                    userManager.createUser(user);
                    JOptionPane.showMessageDialog(null, "Succeed.", null, JOptionPane.INFORMATION_MESSAGE, null);
                    setVisible(false);
                } catch (Exception e1) {
                    user = null;
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Fail", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(jbtCancel);
        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }
}
