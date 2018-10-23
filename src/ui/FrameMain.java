package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import control.AppointmentManager;
import control.UserManager;
import model.*;

public class FrameMain extends JFrame {
    private JMenuBar userMenuBar = new JMenuBar();
    private JMenuBar adMenuBar = new JMenuBar();
    private JMenu userMenu = new JMenu("User");
    private JMenu purchaseMenu = new JMenu("Store");
    private JMenu administratorMenu = new JMenu("Administrator");
    private JMenu productMenu = new JMenu("Products Management");
    /*User's MenuItem*/
    private JMenuItem user_information = new JMenuItem("Information");
    private JMenuItem user_POA = new JMenuItem("View My Pet & Order & Appointment");
    private JMenuItem user_modifyPassword = new JMenuItem("Change Password");
    private JMenuItem user_Logout = new JMenuItem("Logout");
    /*Products&Appointment's MenuItem*/
    private JMenuItem pa_pruchasePet = new JMenuItem("Pet & Products & Appointment");
    /*Administrator's MenuItem*/
    private JMenuItem ad_information = new JMenuItem("Administrator Information");
    private JMenuItem ad_userInformation = new JMenuItem("User Management");
    private JMenuItem ad_order = new JMenuItem("Order Management");
    private JMenuItem ad_Logout = new JMenuItem("Logout");
    /*Products Management's MenuItem*/
    private JMenuItem pm_petManagement = new JMenuItem("Pet Management");
    private JMenuItem pm_productManagement = new JMenuItem("Product Management");
    private JMenuItem pm_productType = new JMenuItem("Product Type Management");
    private JMenuItem pm_appointment = new JMenuItem("Appointment Management");

    public FrameMain() {
        userMenuBar.add(userMenu);
        userMenuBar.add(purchaseMenu);
        adMenuBar.add(administratorMenu);
        adMenuBar.add(productMenu);

        this.validate();
        /*Show Login Dialog*/
        JDialog Login = new DialogLogin(this, "~ Login ~", true);
        Login.setSize(300, 200);
        Login.setLocationRelativeTo(null);
        Login.setVisible(true);

        reloadMenuBar();

        /*Add to User Menu*/
        userMenu.add(user_information);
        user_information.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogAdministratorInformation adInf =
                        new DialogAdministratorInformation(null, "~ Administrator Information ~", true);
                adInf.setLocationRelativeTo(null);
                adInf.setVisible(true);
            }
        });
        userMenu.add(user_POA);
        user_POA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserRetrieve userRetrieve = new UserRetrieve(null, "~ " + UserManager.currentUser.getUser_id() + " ~", true, UserManager.currentUser.getUser_id());
                userRetrieve.setLocationRelativeTo(null);
                userRetrieve.setVisible(true);
            }
        });
        userMenu.addSeparator();
        userMenu.add(user_modifyPassword);
        user_modifyPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyUserPassword modifyUserPassword = new ModifyUserPassword(null, "~ Change Password ~", true);
                modifyUserPassword.setLocationRelativeTo(null);
                modifyUserPassword.setVisible(true);
            }
        });
        userMenu.addSeparator();
        userMenu.add(user_Logout);
        user_Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog Login = new DialogLogin(null, "~ Login ~", true);
                Login.setSize(300, 200);
                Login.setLocationRelativeTo(null);
                Login.setVisible(true);


                reloadMenuBar();
            }
        });

        /*Add to Products&Appointment Menu*/
        purchaseMenu.add(pa_pruchasePet);
        pa_pruchasePet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserPurchase userPurchase = new UserPurchase(null, "~ Store ~", true);
                userPurchase.setLocationRelativeTo(null);
                userPurchase.setVisible(true);
            }
        });

        /*Add to Administrator Menu*/
        administratorMenu.add(ad_information);
        administratorMenu.addSeparator();
        ad_information.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogAdministratorInformation adInf =
                        new DialogAdministratorInformation(null, "~ Administrator Information ~", true);
                adInf.setLocationRelativeTo(null);
                adInf.setVisible(true);
            }
        });
        administratorMenu.add(ad_userInformation);
        ad_userInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogUserList dialogUserList = new DialogUserList(null, "~ User Management ~", true);
                dialogUserList.setLocationRelativeTo(null);
                dialogUserList.setVisible(true);
            }
        });
        administratorMenu.add(ad_order);
        ad_order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogAD_OrderManager dialogAD_orderManager = new DialogAD_OrderManager(null, "~ Order Manager ~", true);
                dialogAD_orderManager.setLocationRelativeTo(null);
                dialogAD_orderManager.setVisible(true);
            }
        });
        administratorMenu.addSeparator();
        administratorMenu.add(ad_Logout);
        ad_Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog Login = new DialogLogin(null, "~ Login ~", true);
                Login.setSize(300, 200);
                Login.setLocationRelativeTo(null);
                Login.setVisible(true);

                reloadMenuBar();
            }
        });
        /*Add to Product Management Menu*/
        productMenu.add(pm_petManagement);
        pm_petManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PetManagement petManagement = new PetManagement(null, "~ Pet Management ~", true);
                petManagement.setLocationRelativeTo(null);
                petManagement.setVisible(true);
            }
        });
        productMenu.add(pm_productManagement);
        pm_productManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductManagement productManagement = new ProductManagement(null, "~ Products Management ~", true);
                productManagement.setLocationRelativeTo(null);
                productManagement.setVisible(true);
            }
        });
        productMenu.add(pm_productType);
        pm_productType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypeManage typeManage = new TypeManage(null, "Type Management", true);
                typeManage.setLocationRelativeTo(null);
                typeManage.setVisible(true);
            }
        });
        productMenu.addSeparator();
        productMenu.add(pm_appointment);
        pm_appointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppoManagement appoManagement = new AppoManagement(null, "~ Appointment Management ~", true);
                appoManagement.setLocationRelativeTo(null);
                appoManagement.setVisible(true);
            }
        });
    }

    public void reloadMenuBar() {
        if (UserManager.currentUser.getAuthority().equals("user")) {
            adMenuBar.setVisible(false);
            this.setJMenuBar(userMenuBar);
            userMenuBar.setVisible(true);
        }
        if (UserManager.currentUser.getAuthority().equals("administrator")) {
            userMenuBar.setVisible(false);
            this.setJMenuBar(adMenuBar);
            adMenuBar.setVisible(true);
        }
    }
}
