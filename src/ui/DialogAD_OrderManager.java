package ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import control.ProductManager;
import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import control.OrderManager;
import util.BaseException;
import util.HibernateUtil;

public class DialogAD_OrderManager extends JDialog{
    private JPanel buttonPane = new JPanel();
    private JPanel retrievePane = new JPanel();

    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtAdd = new JButton("Add");
    private JButton jbtDelete = new JButton("Delete");
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");
    private JTextField jtfRetrieve = new JTextField(20);
    private JLabel jlbRetrieve = new JLabel("Retrieve by user id:");

    private String[] columnName = {"Order ID", "Product ID", "Product Name", "User ID", "Quantity", "Price", "Begin Time", "End Time", "Transaction State"};
    private Object[][] orderData;

    private DefaultTableModel defaultTableModel = new DefaultTableModel(orderData, columnName);
    private JTable dataTable = new JTable(defaultTableModel);

    public DialogAD_OrderManager(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        this.setSize(800, 400);
        this.reloadOrderList(jtfRetrieve.getText());
        dataTable.setGridColor(Color.GRAY);
        dataTable.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(dataTable));
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
        defaultTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int i = e.getFirstRow();
                int j = e.getColumn();
                if (i >= 0 && i < dataTable.getRowCount() && j >= 0 && j < dataTable.getColumnCount())
                    orderData[i][j] = dataTable.getValueAt(i, j);
            }
        });

        retrievePane.add(jlbRetrieve);
        retrievePane.add(jtfRetrieve);
        retrievePane.add(jbtRetrieve);
        this.getContentPane().add(retrievePane, BorderLayout.NORTH);
        jbtRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadOrderList(jtfRetrieve.getText());
            }
        });

        buttonPane.add(jbtAdd);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogOrderAdd dialogOrderAdd = new DialogOrderAdd(null, "Add Order", true);
                dialogOrderAdd.setLocationRelativeTo(null);
                dialogOrderAdd.setVisible(true);

                reloadOrderList(jtfRetrieve.getText());
            }
        });
        buttonPane.add(jbtDelete);
        jbtDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "Please select a row.", "Hint", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        int orderId = new Integer(orderData[i][0].toString()).intValue();
                        try {
                            new OrderManager().deleteOrder(orderId);

                            reloadOrderList(jtfRetrieve.getText());
                        } catch (BaseException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        buttonPane.add(jbtModify);
        jbtModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select an order.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        bean_order order = HibernateUtil.getSession().get(bean_order.class, (int) orderData[i][0]);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        order.setOrder_id((int) orderData[i][0]);
                        order.setProduct_id((int) orderData[i][1]);
                        order.setUser_id(orderData[i][3].toString());
                        order.setQuantity(new Integer(orderData[i][4].toString()).intValue());
                        order.setOrder_price(new Double(orderData[i][5].toString()));
                        order.setOrder_begin_time(new java.sql.Date(format.parse(orderData[i][6].toString()).getTime()));
                        order.setOrder_end_time(new java.sql.Date(format.parse(orderData[i][7].toString()).getTime()));
                        order.setTransfer_state(orderData[i][8].toString());

                        new OrderManager().modifyOrder(order);
                        JOptionPane.showMessageDialog(null, "Succeed", "Modify Order", JOptionPane.INFORMATION_MESSAGE);
                        reloadOrderList(jtfRetrieve.getText());
                    } catch (BaseException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        buttonPane.add(jbtQuit);
        jbtQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private void reloadOrderList(String userid) {
//        List<bean_order> list = new OrderManager().loadAllOrder();
        List<bean_order> list = new OrderManager().retrieveOrder(userid);
        orderData = new Object[list.size()][9];
        try {
            for (int i = 0; i < list.size(); i ++) {
                orderData[i][0] = list.get(i).getOrder_id();
                orderData[i][1] = list.get(i).getProduct_id();
                orderData[i][2] = new ProductManager().productName(list.get(i).getProduct_id());
                orderData[i][3] = list.get(i).getUser_id();
                orderData[i][4] = list.get(i).getQuantity();
                orderData[i][5] = list.get(i).getOrder_price();
                orderData[i][6] = list.get(i).getOrder_begin_time();
                orderData[i][7] = list.get(i).getOrder_end_time();
                orderData[i][8] = list.get(i).getTransfer_state();
            }
            defaultTableModel.setDataVector(orderData, columnName);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
