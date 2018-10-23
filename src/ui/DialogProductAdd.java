package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.OrderManager;
import control.ProductManager;
import model.*;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import util.*;

public class DialogProductAdd extends JDialog {
    JPanel buttonPane = new JPanel();
    JPanel textPane = new JPanel();
    JPanel checkPane = new JPanel();

    JButton jbtContinue = new JButton("Continue");
    JButton jbtCancel = new JButton("Cancel");

    JLabel jblProductName = new JLabel("Product Name:");
    JLabel jblProductType = new JLabel("Type:");
    JLabel jblProductBrand = new JLabel("Brand *");
    JLabel jblProductCode = new JLabel("Code:");
    JLabel jblProductPrice = new JLabel("Price:");
    JTextField jtfProductName = new JTextField(20);
    JTextField jtfProductBrand = new JTextField(20);
    JTextField jtfProductCode = new JTextField(20);
    JTextField jtfProductPrice = new JTextField(20);
    String[] grades = {"Pet", "Pet's Food", "Other Products", "Appointment"};
    java.util.List list = new ArrayList(Arrays.asList(grades));
    SpinnerListModel model = new SpinnerListModel(list);
    JSpinner jsProductType = new JSpinner(model);

    public DialogProductAdd(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        bean_products_information newProduct = new bean_products_information();

        textPane.setLayout(new GridLayout(15, 1));
        textPane.add(jblProductName);
        textPane.add(jtfProductName);
        textPane.add(jblProductType);
        textPane.add(jsProductType);
        jsProductType.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jsProductType.getValue() == "Pet")
                    newProduct.setTypes(1);
                else if (jsProductType.getValue() == "Pet's Food")
                    newProduct.setTypes(2);
                else if (jsProductType.getValue() == "Other Products")
                    newProduct.setTypes(3);
                else
                    newProduct.setTypes(4);
            }
        });
        textPane.add(jblProductBrand);
        textPane.add(jtfProductBrand);
        textPane.add(jblProductCode);
        textPane.add(jtfProductCode);
        textPane.add(jblProductPrice);
        textPane.add(jtfProductPrice);

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newProduct.setProduct_name(jtfProductName.getText());
                newProduct.setBrand(jtfProductBrand.getText());
                newProduct.setProduct_code(jtfProductCode.getText());
                newProduct.setPrice(new Double(jtfProductPrice.getText()).doubleValue());

                try {
                    new ProductManager().addProduct(newProduct);
                    JOptionPane.showMessageDialog(null, "Succeed", "Add Product", JOptionPane.INFORMATION_MESSAGE);
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

        this.setSize(300, 500);
        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

    public DialogProductAdd(JDialog jDialog, String title, boolean modal, int i) {
        super(jDialog, title, modal);

        bean_products_information newProduct = new bean_products_information();

        textPane.setLayout(new GridLayout(15, 1));
        textPane.add(jblProductName);
        textPane.add(jtfProductName);
        textPane.add(jblProductType);
        textPane.add(jsProductType);
        jsProductType.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jsProductType.getValue() == "Pet")
                    newProduct.setTypes(1);
                else if (jsProductType.getValue() == "Pet's Food")
                    newProduct.setTypes(2);
                else if (jsProductType.getValue() == "Other Products")
                    newProduct.setTypes(3);
                else
                    newProduct.setTypes(4);
            }
        });
        textPane.add(jblProductBrand);
        textPane.add(jtfProductBrand);
        textPane.add(jblProductCode);
        textPane.add(jtfProductCode);
        textPane.add(jblProductPrice);
        textPane.add(jtfProductPrice);

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newProduct.setProduct_name(jtfProductName.getText());
                newProduct.setBrand(jtfProductBrand.getText());
                newProduct.setProduct_code(jtfProductCode.getText());
                newProduct.setPrice(new Double(jtfProductPrice.getText()).doubleValue());

                try {
                    new ProductManager().addProduct(newProduct);
                    JOptionPane.showMessageDialog(null, "Succeed", "Add Product", JOptionPane.INFORMATION_MESSAGE);
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

        this.setSize(300, 500);
        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }
}
