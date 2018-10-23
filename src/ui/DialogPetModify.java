package ui;

import control.PetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;
import util.*;

public class DialogPetModify extends JDialog{
    private JPanel textPane = new JPanel();
    private JPanel buttonPane = new JPanel();
    private JPanel checkPane = new JPanel();
    private JPanel southSubPane = new JPanel();

    private JLabel jblProductId = new JLabel("Product ID:");
    private JLabel jblNickName = new JLabel("Nick Name:");
    private JLabel jblPetName = new JLabel("Pet Name:");
    private JLabel jblOwner = new JLabel("Owner:");
//    private JLabel jblPicture = new JLabel("Picture Path:");
    private JLabel jblPetAge = new JLabel("Age:");
    private JLabel jblHealth = new JLabel("Select A Health State:");
    private JTextField jtfProductId = new JTextField( 20);
    private JTextField jtfNickName = new JTextField(20);
//    private JTextField jtfPicture = new JTextField(20);
    private JTextField jtfPetName = new JTextField(20);
    private JTextField jtfOwner = new JTextField(20);
    private JTextField jtfPetAge = new JTextField(20);

    private JCheckBox jcbHealth = new JCheckBox("health");
    private JCheckBox jcbIll = new JCheckBox("ill");
    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtConcel = new JButton("Cancel");

    public DialogPetModify(JDialog jDialog, String title, boolean modal, int petid) {
        super(jDialog, title, modal);

        bean_pet_information pet = HibernateUtil.getSession().get(bean_pet_information.class, petid);

        textPane.setLayout(new GridLayout(15, 1));
        textPane.add(jblProductId);
        textPane.add(jtfProductId);
        jtfProductId.setText((pet.getProduct_id()).toString());
        textPane.add(jblNickName);
        textPane.add(jtfNickName);
        jtfNickName.setText(pet.getNick_name());
        textPane.add(jblPetName);
        textPane.add(jtfPetName);
        jtfPetName.setText(pet.getPet_name());
        textPane.add(jblOwner);
        textPane.add(jtfOwner);
        jtfOwner.setText(pet.getPet_owner());
        textPane.add(jblPetAge);
        textPane.add(jtfPetAge);
        jtfPetAge.setText(pet.getPet_age().toString());

        checkPane.setLayout(new GridLayout(3, 1));
        checkPane.add(jblHealth);
        checkPane.add(jcbHealth);
        checkPane.add(jcbIll);
        if (pet.getHealthy() == "health")
            jcbHealth.setSelected(true);
        else
            jcbIll.setSelected(true);

        buttonPane.setLayout(new GridLayout(2, 1));
        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pet.setProduct_id(new Integer(jtfProductId.getText()).intValue());
                    pet.setNick_name(jtfNickName.getText());
                    pet.setPet_name(jtfPetName.getText());
                    pet.setPet_age(new Integer(jtfPetAge.getText()).intValue());
                    if (jcbHealth.isSelected() == true)
                        pet.setHealthy("health");
                    else
                        pet.setHealthy("ill");
                    pet.setPet_owner(jtfOwner.getText());

                    new PetManager().modifyPet(pet);

                    JOptionPane.showMessageDialog(null, "Succeed", "Modify Pet", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Hint" ,JOptionPane.ERROR_MESSAGE);
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
