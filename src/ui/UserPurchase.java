package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import control.*;
import model.bean_appointment;
import model.bean_pet_information;
import model.bean_products_information;
import ui.*;
import util.BaseException;

public class UserPurchase extends JDialog{
    private JTabbedPane jtp = new JTabbedPane();
    private JPanel petPane = new JPanel();
    private JPanel foodPane = new JPanel();
    private JPanel otherPane = new JPanel();
    private JPanel appointmentPane = new JPanel();
    private JPanel buttonPane = new JPanel();

//    private JLabel jlbQuantity = new JLabel("Number of product:");
//    private JTextField jtfQuantity = new JTextField(10);
    private JButton jbtBuy = new JButton("< Buy >");

    private String[] petColumnName = {"Product ID", "Product Type", "Product Name", "Brand", "Price", "Code"};
    private Object[][] petData;
    private DefaultTableModel petTableModel = new DefaultTableModel(petData, petColumnName);
    private JTable petTable = new JTable(petTableModel);

    private String[] foodColumnName = {"Product ID", "Product Type", "Product Name", "Brand", "Price", "Code"};
    private Object[][] foodData;
    private DefaultTableModel foodTableModel = new DefaultTableModel(foodData, foodColumnName);
    private JTable foodTable = new JTable(foodTableModel);

    private String[] otherColumnName = {"Product ID", "Product Type", "Product Name", "Brand", "Price", "Code"};
    private Object[][] otherData;
    private DefaultTableModel otherTableModel = new DefaultTableModel(otherData, otherColumnName);
    private JTable otherTable = new JTable(otherTableModel);

    private String[] appoColumnName = {"Product ID", "Product Type", "Product Name", "Brand", "Price", "Code"};
    private Object[][] appoData;
    private DefaultTableModel appoTableModel = new DefaultTableModel(appoData, appoColumnName);
    private JTable appoTable = new JTable(appoTableModel);

    public UserPurchase(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        jtp.add(petPane, "Pet");
        jtp.add(foodPane, "Pet's Food");
        jtp.add(otherPane, "Other Products");
        jtp.add(appointmentPane, "Appointment");
        this.setSize(700, 500);
        this.getContentPane().add(jtp, BorderLayout.CENTER);

        petTable.setGridColor(Color.GRAY);
        petTable.setBackground(Color.LIGHT_GRAY);
        petPane.add(petTable, BorderLayout.CENTER);
        petPane.add(new JScrollPane(petTable));
        reloadPetTable("Pet");

        foodTable.setGridColor(Color.GRAY);
        foodTable.setBackground(Color.LIGHT_GRAY);
        foodPane.add(foodTable, BorderLayout.CENTER);
        foodPane.add(new JScrollPane(foodTable));
        reloadfoodTable("Pet's Food");

        otherTable.setGridColor(Color.GRAY);
        otherTable.setBackground(Color.LIGHT_GRAY);
        otherPane.add(otherTable, BorderLayout.CENTER);
        otherPane.add(new JScrollPane(otherTable));
        reloadotherTable("Other Products");

        appoTable.setGridColor(Color.GRAY);
        appoTable.setBackground(Color.LIGHT_GRAY);
        appointmentPane.add(appoTable, BorderLayout.CENTER);
        appointmentPane.add(new JScrollPane(appoTable));
        reloadAppointment("Appointment");

//        buttonPane.add(jlbQuantity);
//        buttonPane.add(jtfQuantity);
        buttonPane.add(jbtBuy);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
        jbtBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jtp.getSelectedIndex();
                /*Buy Pet*/
                if (i == 0) {
                    int j = petTable.getSelectedRow();
                    if (j < 0)
                        JOptionPane.showMessageDialog(null, "Please select a pet.", "Hint", JOptionPane.ERROR_MESSAGE);
                    else {
                        DialogOrderAdd dialogOrderAdd = new DialogOrderAdd(null, "Build Pet Order", true, (int) petData[j][0]);
                        dialogOrderAdd.setLocationRelativeTo(null);
                        dialogOrderAdd.setVisible(true);

                        DialogBuyPet dialogBuyPet = new DialogBuyPet(null, "Buy Pet", true, (int) petData[j][0]);
                        dialogBuyPet.setLocationRelativeTo(null);
                        dialogBuyPet.setVisible(true);
                    }
                }
                /*Buy Food*/
                else if (i == 1) {
                    int j = foodTable.getSelectedRow();
                    if (j < 0)
                        JOptionPane.showMessageDialog(null, "Please select a pet's food.", "Hint", JOptionPane.ERROR_MESSAGE);
                    else {
                        DialogOrderAdd dialogOrderAdd = new DialogOrderAdd(null, "Build Pet's Food Order", true, (int) foodData[j][0]);
                        dialogOrderAdd.setLocationRelativeTo(null);
                        dialogOrderAdd.setVisible(true);
                    }
                }
                else if (i == 2) {
                    int j = otherTable.getSelectedRow();
                    if (j < 0)
                        JOptionPane.showMessageDialog(null, "Please select a product.", "Hint", JOptionPane.ERROR_MESSAGE);
                    else {
                        DialogOrderAdd dialogOrderAdd = new DialogOrderAdd(null, "Build Order", true, (int) otherData[j][0]);
                        dialogOrderAdd.setLocationRelativeTo(null);
                        dialogOrderAdd.setVisible(true);
                    }
                }
                else {
                    int j = appoTable.getSelectedRow();
                    if (j < 0)
                        JOptionPane.showMessageDialog(null, "Please select an appointment.", "Hint", JOptionPane.ERROR_MESSAGE);
                    else {
                        DialogOrderAdd dialogOrderAdd = new DialogOrderAdd(null, "Appointment", true, (int) appoData[j][0]);
                        dialogOrderAdd.setLocationRelativeTo(null);
                        dialogOrderAdd.setVisible(true);

                        DialogBuyAppointment dialogBuyAppointment = new DialogBuyAppointment(null, "Appointment", true, (int) appoData[j][0]);
                        dialogBuyAppointment.setLocationRelativeTo(null);
                        dialogBuyAppointment.setVisible(true);
                    }
                }
            }
        });
    }

    public void reloadPetTable(String typeName) {
//        List<bean_products_information> list = new ProductManager().loadAllProducts();
        List<bean_products_information> list = new ProductManager().retrieveProduct("", typeName);
        petData = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i ++) {
            petData[i][0] = list.get(i).getProduct_id();
            petData[i][1] = new TypeManagement().typeName(list.get(i).getTypes());
            petData[i][2] = list.get(i).getProduct_name();
            petData[i][3] = list.get(i).getBrand();
            petData[i][4] = list.get(i).getPrice();
            petData[i][5] = list.get(i).getProduct_code();
        }
        petTableModel.setDataVector(petData, petColumnName);
        petTable.validate();
        petTable.repaint();
    }

    public void reloadfoodTable(String typeName) {
//        List<bean_products_information> list = new ProductManager().loadAllProducts();
        List<bean_products_information> list = new ProductManager().retrieveProduct("", typeName);
        foodData = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i ++) {
            foodData[i][0] = list.get(i).getProduct_id();
            foodData[i][1] = new TypeManagement().typeName(list.get(i).getTypes());
            foodData[i][2] = list.get(i).getProduct_name();
            foodData[i][3] = list.get(i).getBrand();
            foodData[i][4] = list.get(i).getPrice();
            foodData[i][5] = list.get(i).getProduct_code();
        }
        foodTableModel.setDataVector(foodData, foodColumnName);
        foodTable.validate();
        foodTable.repaint();
    }

    public void reloadotherTable(String typeName) {
//        List<bean_products_information> list = new ProductManager().loadAllProducts();
        List<bean_products_information> list = new ProductManager().retrieveProduct("", typeName);
        otherData = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i ++) {
            otherData[i][0] = list.get(i).getProduct_id();
            otherData[i][1] = new TypeManagement().typeName(list.get(i).getTypes());
            otherData[i][2] = list.get(i).getProduct_name();
            otherData[i][3] = list.get(i).getBrand();
            otherData[i][4] = list.get(i).getPrice();
            otherData[i][5] = list.get(i).getProduct_code();
        }
        otherTableModel.setDataVector(otherData, otherColumnName);
        otherTable.validate();
        otherTable.repaint();
    }

    public void reloadAppointment(String typeName) {
//        List<bean_products_information> list = new ProductManager().loadAllProducts();
        List<bean_products_information> list = new ProductManager().retrieveProduct("", typeName);
        appoData = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i ++) {
            appoData[i][0] = list.get(i).getProduct_id();
            appoData[i][1] = new TypeManagement().typeName(list.get(i).getTypes());
            appoData[i][2] = list.get(i).getProduct_name();
            appoData[i][3] = list.get(i).getBrand();
            appoData[i][4] = list.get(i).getPrice();
            appoData[i][5] = list.get(i).getProduct_code();
        }
        appoTableModel.setDataVector(appoData, appoColumnName);
        appoTable.validate();
        appoTable.repaint();
    }
}
