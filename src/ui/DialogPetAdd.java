package ui;

import javax.swing.*;
import control.PetManager;
import model.*;
import util.BaseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DialogPetAdd extends  JDialog{
    private JPanel textPane = new JPanel();
    private JPanel buttonPane = new JPanel();
    private JPanel checkPane = new JPanel();
    private JPanel southSubPane = new JPanel();

    private JLabel jblNickName = new JLabel("Nick Name:");
    private JLabel jblPetName = new JLabel("Pet Name:");
    private JLabel jblOwner = new JLabel("Owner:");
    private JLabel jblPicture = new JLabel("Picture Path:");
    private JLabel jblPetAge = new JLabel("Age:");
    private JLabel jblHealth = new JLabel("Select A Health State:");
    private JTextField jtfNickName = new JTextField(20);
    private JTextField jtfPicture = new JTextField(20);
    private JTextField jtfPetName = new JTextField(20);
    private JTextField jtfOwner = new JTextField(20);
    private JTextField jtfPetAge = new JTextField(20);

    private JCheckBox jcbHealth = new JCheckBox("health");
    private JCheckBox jcbIll = new JCheckBox("ill");
    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtConcel = new JButton("Cancel");

    public DialogPetAdd(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, true);

        textPane.setLayout(new GridLayout(15, 1));
        textPane.add(jblNickName);
        textPane.add(jtfNickName);
        textPane.add(jblPetName);
        textPane.add(jtfPetName);
        textPane.add(jblOwner);
        textPane.add(jtfOwner);
        textPane.add(jblPicture);
        textPane.add(jtfPicture);
        textPane.add(jblPetAge);
        textPane.add(jtfPetAge);

        checkPane.setLayout(new GridLayout(3, 1));
        checkPane.add(jblHealth);
        checkPane.add(jcbHealth);
        checkPane.add(jcbIll);

        buttonPane.setLayout(new GridLayout(2, 1));
        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PetManager petManager = new PetManager();
                    bean_pet_information newPet = new bean_pet_information();
                    newPet.setNick_name(jtfNickName.getText());
                    newPet.setPet_name(jtfPetName.getText());
                    newPet.setPet_owner(jtfOwner.getText());
                    newPet.setPet_age(new Integer(jtfPetAge.getText()));
                    if (jcbHealth.isSelected() == true) {
                        newPet.setHealthy("health");
                    }
                    else {
                        newPet.setHealthy("ill");
                    }
                    if (jtfPicture.getText() == null)
                        petManager.writePicture(newPet, jtfPicture.getText());

                    petManager.addPetInf(newPet);
                    JOptionPane.showMessageDialog(null, "Succeed", "Add Pet", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                }
//                catch (BaseException e2) {
//                    JOptionPane.showMessageDialog(null, e2, "Hint", JOptionPane.ERROR_MESSAGE);
//                }
                catch (Exception e3) {
                    e3.printStackTrace();
                    JOptionPane.showMessageDialog(null, e3, "Hint", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(jbtConcel);
        jbtConcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        southSubPane.setSize(300, 200);
        southSubPane.add(checkPane, BorderLayout.CENTER);
        southSubPane.add(buttonPane, BorderLayout.SOUTH);
        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(southSubPane, BorderLayout.SOUTH);
        this.setSize(300, 500);
    }
}
