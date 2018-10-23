package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.OrderManager;
import control.UserManager;
import model.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.*;

public class DialogBuyPet extends JDialog {
    private JPanel textPane = new JPanel();

    private JLabel jblNickName = new JLabel("Nick Name:");
    private JTextField jtfNickName = new JTextField(20);
    private JButton jbtOK = new JButton("OK");

    public DialogBuyPet(JDialog jDialog, String title, boolean modal, int productId) {
        super(jDialog, title, modal);

        this.getContentPane().add(textPane);
        textPane.add(jblNickName);
        textPane.add(jtfNickName);
        textPane.add(jbtOK);
        jbtOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bean_pet_information newPet = new bean_pet_information();
                Session session = HibernateUtil.getSession();
                Transaction transaction = session.getTransaction();
                try {
                    transaction.begin();

                    bean_products_information product = session.get(bean_products_information.class, productId);

                    newPet.setNick_name(jtfNickName.getText());
                    newPet.setPet_name(product.getProduct_name());
                    newPet.setPet_owner(UserManager.currentUser.getUser_id());
                    newPet.setPet_age(1);
                    newPet.setHealthy("health");

                    session.save(newPet);

                    transaction.commit();

                    setVisible(false);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.setSize(300, 150);
    }
}
