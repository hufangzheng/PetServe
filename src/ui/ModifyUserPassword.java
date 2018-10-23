package ui;

import javax.swing.*;

import control.UserManager;
import util.BaseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyUserPassword extends JDialog {
    private JPanel buttonPane = new JPanel();
    private JPanel textPane = new JPanel();
    private JPanel useridPane = new JPanel();

    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtCancel = new JButton("Cancel");

    private JLabel jlbUserid = new JLabel(UserManager.currentUser.getUser_id());
    private JLabel jlbOldPassword = new JLabel("Old Password:");
    private JLabel jlbNewPassword = new JLabel("New Password:");
    private JLabel jlbConfirmPassword = new JLabel("Confirm New Password:");
    private JPasswordField jpfOldPassword = new JPasswordField(20);
    private JPasswordField jpfNewPassword = new JPasswordField(20);
    private JPasswordField jpfConfirmPassword = new JPasswordField(20);

    public ModifyUserPassword(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        this.setSize(300, 300);
        this.getContentPane().add(useridPane, BorderLayout.NORTH);
        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);

        useridPane.add(jlbUserid);

        textPane.add(jlbOldPassword);
        textPane.add(jpfOldPassword);
        textPane.add(jlbNewPassword);
        textPane.add(jpfNewPassword);
        textPane.add(jlbConfirmPassword);
        textPane.add(jpfConfirmPassword);

        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UserManager().modifyUserPassword(UserManager.currentUser.getUser_id(), new String(jpfOldPassword.getPassword()), new String(jpfNewPassword.getPassword()), new String(jpfConfirmPassword.getPassword()));

                    JOptionPane.showMessageDialog(null, "Susseed", "Modify Password", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
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
    }
}
