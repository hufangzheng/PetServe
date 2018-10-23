package ui;

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
import util.*;

public class DialogOrderAdd extends JDialog{
    private JPanel buttonPane = new JPanel();
    private JPanel textPane = new JPanel();
    private JPanel checkPane = new JPanel();

    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtCancel = new JButton("Cancel");
    private JComboBox jcbTransport = new JComboBox(new Object[] {"Prepare Transport", "During Transport", "Accomplish"});
    private JComboBox jcbYear = new JComboBox(new Object[] {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"});
    private JComboBox jcbMonth = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
    private JComboBox jcbDate = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"});
    private JComboBox jcbYear2 = new JComboBox(new Object[] {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"});
    private JComboBox jcbMonth2 = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
    private JComboBox jcbDate2 = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"});

    private JLabel jlbProductId = new JLabel("Product ID:");
    private JLabel jlbUserId = new JLabel("User ID:");
    private JLabel jlbBeginTime = new JLabel("Begin Time:");
    private JLabel jlbEndTime = new JLabel("End Time:");
    private JLabel jlbQuantity = new JLabel("Quantity:");
    private JLabel jlbOrderPrice = new JLabel("Price:");
    private JLabel jlbTransferState = new JLabel("Select A Transport State:");
    private JTextField jtfProductId = new JTextField(20);
    private JTextField jtfUserId = new JTextField(20);
    private JTextField jtfBeginTime = new JTextField(20);
    private JTextField jtfEndTime = new JTextField(20);
    private JTextField jtfQuantity = new JTextField(20);

    private bean_order newOrder  = new bean_order();

    public DialogOrderAdd(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        textPane.setLayout(new GridLayout(15, 1));
        textPane.add(jlbProductId);
        textPane.add(jtfProductId);
        textPane.add(jlbUserId);
        textPane.add(jtfUserId);
        textPane.add(jlbBeginTime);
        textPane.add(jcbYear);
        jcbYear.setSelectedItem("2018");
        textPane.add(jcbMonth);
        jcbMonth.setSelectedItem("1");
        textPane.add(jcbDate);
        jcbDate.setSelectedItem("1");
//        textPane.add(jtfBeginTime);
        textPane.add(jlbEndTime);
        textPane.add(jcbYear2);
        jcbYear2.setSelectedItem("2018");
        textPane.add(jcbMonth2);
        jcbMonth2.setSelectedItem("1");
        textPane.add(jcbDate2);
        jcbDate2.setSelectedItem("1");
//        textPane.add(jtfEndTime);
        textPane.add(jlbQuantity);
        textPane.add(jtfQuantity);
//        textPane.add(jlbOrderPrice);

        checkPane.setLayout(new GridLayout(4, 1));
        checkPane.add(jlbTransferState);
        checkPane.add(jcbTransport);
        jcbTransport.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            }
        });

        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(checkPane, BorderLayout.NORTH);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
        this.setSize(300, 500);

        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateTime1();
                    updateTime2();

                    newOrder.setProduct_id(new Integer(jtfProductId.getText()));
                    newOrder.setUser_id(jtfUserId.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    newOrder.setOrder_begin_time(new java.sql.Date(sdf.parse(jtfBeginTime.getText()).getTime()));
//                    newOrder.setOrder_end_time(new java.sql.Date(sdf.parse(jtfEndTime.getText()).getTime()));
                    newOrder.setQuantity(new Integer(jtfQuantity.getText()));
                    newOrder.setTransfer_state(jcbTransport.getSelectedItem().toString());

                    OrderManager orderManager = new OrderManager();
                    newOrder.setOrder_price(orderManager.orderPrice(newOrder));

                    orderManager.addOrder(newOrder);

                    JOptionPane.showMessageDialog(null, "Succeed", "Add Order", JOptionPane.INFORMATION_MESSAGE);

                    setVisible(false);
                } catch (BaseException e3) {
                    e3.printStackTrace();
                    JOptionPane.showMessageDialog(null, e3.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
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

    public DialogOrderAdd(JDialog jDialog, String title, boolean modal, int productId) {
        super(jDialog, title, modal);

        textPane.setLayout(new GridLayout(15, 1));
        textPane.add(jlbProductId);
        textPane.add(jtfProductId);
        jtfProductId.setText(new Integer(productId).toString());
        textPane.add(jlbUserId);
        textPane.add(jtfUserId);
        jtfUserId.setText(UserManager.currentUser.getUser_id());
        textPane.add(jlbBeginTime);
        textPane.add(jtfBeginTime);
        jtfBeginTime.setText(new java.sql.Date(System.currentTimeMillis()).toString());
        textPane.add(jlbEndTime);
        textPane.add(jtfEndTime);
//        textPane.add(jlbQuantity);
//        textPane.add(jtfQuantity);

        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(checkPane, BorderLayout.NORTH);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
        this.setSize(300, 500);

        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateTime1();
                    updateTime2();

                    newOrder.setProduct_id(new Integer(jtfProductId.getText()));
                    newOrder.setUser_id(jtfUserId.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    newOrder.setOrder_begin_time(new java.sql.Date(sdf.parse(jtfBeginTime.getText()).getTime()));
//                    newOrder.setOrder_end_time(new java.sql.Date(sdf.parse(jtfEndTime.getText()).getTime()));
                    newOrder.setQuantity(1);
                    newOrder.setTransfer_state(jcbTransport.getSelectedItem().toString());

                    OrderManager orderManager = new OrderManager();
                    newOrder.setOrder_price(orderManager.orderPrice(newOrder));

                    orderManager.addOrder(newOrder);

                    JOptionPane.showMessageDialog(null, "Succeed", "Add Order", JOptionPane.INFORMATION_MESSAGE);

                    setVisible(false);
                } catch (BaseException e3) {
                    e3.printStackTrace();
                    JOptionPane.showMessageDialog(null, e3.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
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

    /*Update begin time and end time*/
    private void updateTime1() {
        String strBeginTime = jcbYear.getSelectedItem() + "-" + jcbMonth.getSelectedItem() + "-" + jcbDate.getSelectedItem();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newOrder.setOrder_begin_time(new java.sql.Date(format.parse(strBeginTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
        }
    }
    private  void updateTime2() {
        String strEndTime = jcbYear2.getSelectedItem() + "-" + jcbMonth2.getSelectedItem() + "-" + jcbDate2.getSelectedItem();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newOrder.setOrder_end_time(new java.sql.Date(format.parse(strEndTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
        }
    }
}
