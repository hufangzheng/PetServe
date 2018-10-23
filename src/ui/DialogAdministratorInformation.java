package ui;

import javax.swing.*;
import control.UserManager;
import model.bean_user_information;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogAdministratorInformation extends JDialog{
    private JPanel infPane = new JPanel();
    private JPanel butPane = new JPanel();

    private JButton jbtCancel = new JButton("Cancel");

    private JLabel jblAuthority = new JLabel("Authority:");
    private JLabel jblUserId = new JLabel("User Id:");
    private JLabel jblPhoneNumber = new JLabel("Phone Number:");
    private JLabel jblPassword = new JLabel("Password:");
    private JLabel jblUserName = new JLabel("User Name:");
    private JLabel jblEmail = new JLabel("Email Address:");
    private JLabel jblOtherContact = new JLabel("Other Contact:");

    private bean_user_information user = UserManager.currentUser;
    private JTextField authority = new JTextField(user.getAuthority());
    private JTextField userId = new JTextField(user.getUser_id());
    private JTextField phoneNumber = new JTextField(user.getPhone_number());
    private JTextField password = new JTextField(user.getPassword());
    private JTextField userName = new JTextField(user.getUser_name());
    private JTextField Email = new JTextField(user.getEmail());
    private JTextArea otherContact = new JTextArea(user.getOther_contact());

    public DialogAdministratorInformation(JDialog dialog, String title, boolean modal) {
        super(dialog, title, modal);

        authority.setText(user.getAuthority());
        userId.setText(user.getUser_id());
        phoneNumber.setText(user.getPhone_number());
        password.setText(user.getPassword());
        userName.setText(user.getUser_name());
        Email.setText(user.getEmail());

        infPane.add(jblAuthority);
        infPane.add(authority);
        infPane.add(jblUserId);
        infPane.add(userId);
        infPane.add(jblPhoneNumber);
        infPane.add(phoneNumber);
        infPane.add(jblPassword);
        infPane.add(password);
        infPane.add(jblUserName);
        infPane.add(userName);
        infPane.add(jblEmail);
        infPane.add(Email);
        infPane.add(jblOtherContact);
        infPane.add(otherContact);

        infPane.setLayout(new GridLayout(20, 1));
        this.getContentPane().add(infPane, BorderLayout.CENTER);
        this.setSize(300, 500);

        butPane.add(jbtCancel, new FlowLayout(FlowLayout.CENTER));
        this.getContentPane().add(butPane, BorderLayout.SOUTH);
        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
