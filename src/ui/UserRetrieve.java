package ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import control.*;
import model.*;

public class UserRetrieve extends JDialog {
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");
    private JPanel buttonPane = new JPanel();

    private String[] petColumnName = {"Pet ID", "Name", "Nick Name", "Picture", "Pet Age", "Health"};
    private String[] orderColumnName = {"Order ID", "Order Name", "Quantity", "Price", "Begin Time", "End Time", "Transaction State"};
    private String[] appointmentColumnName = {"Appointment ID", "Appointment Name", "Pet Nick Name", "Begin Time", "End Time", "Circumstance"};

    private Object[][] petData;
    private Object[][] orderData;
    private Object[][] appoimtmentData;

    private DefaultTableModel petModel = new DefaultTableModel(petData, petColumnName);
    private DefaultTableModel orderModel = new DefaultTableModel(orderData, orderColumnName);
    private DefaultTableModel appoModel = new DefaultTableModel(appoimtmentData, appointmentColumnName);

    private JTable petTable = new JTable(petModel);
    private JTable orderTable = new JTable(orderModel);
    private JTable appoTable = new JTable(appoModel);

    public UserRetrieve(JDialog jDialog, String title, boolean modal, String userid) {
        super(jDialog, title, modal);

        this.setSize(700, 700);
        petTable.setGridColor(Color.GRAY);
        petTable.setBackground(Color.LIGHT_GRAY);
        orderTable.setGridColor(Color.GRAY);
        orderTable.setBackground(Color.LIGHT_GRAY);
        appoTable.setGridColor(Color.GRAY);
        appoTable.setBackground(Color.LIGHT_GRAY);

//        petModel.addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                int i = e.getFirstRow();
//                int j = e.getColumn();
//                if (i >= 0 && i < petTable.getRowCount() && j >= 0 && j < petTable.getColumnCount())
//                    petData[i][j] = petTable.getValueAt(i, j);
//            }
//        });
//        orderModel.addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                int i = e.getFirstRow();
//                int j = e.getColumn();
//                if (i >= 0 && i < orderTable.getRowCount() && j >= 0 && j < orderTable.getColumnCount())
//                    orderData[i][j] = orderTable.getValueAt(i, j);
//            }
//        });
//        appoModel.addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                int i = e.getFirstRow();
//                int j = e.getColumn();
//                if (i >= 0 && i < appoTable.getRowCount() && j >= 0 && j < appoTable.getColumnCount())
//                    appoimtmentData[i][j] = appoTable.getValueAt(i, j);
//            }
//        });

        add(buttonPane, BorderLayout.SOUTH);
        buttonPane.add(jbtQuit);
        jbtQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        JScrollPane jScrollPane1 = new JScrollPane(appoTable);
        jScrollPane1.setSize(new Dimension(690, 200));
        JScrollPane jScrollPane2 = new JScrollPane(petTable);
        jScrollPane2.setSize(new Dimension(690, 200));
        JScrollPane jScrollPane3 = new JScrollPane(orderTable);
        jScrollPane3.setSize(new Dimension(690, 200));

        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane1, buttonPane);
        jSplitPane1.setDividerLocation(180);
        JSplitPane jSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane2, jScrollPane3);
        jSplitPane2.setDividerLocation(220);
        JSplitPane jSplitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPane2, jSplitPane1);
        jSplitPane3.setDividerLocation(440);
        this.getContentPane().add(jSplitPane3, BorderLayout.CENTER);

        reloadPetData(userid);
        reloadOrderData(userid);
        reloadAppoData(userid);
    }

    public void reloadPetData(String userid) {
        List<bean_pet_information> list = new PetManager().retrievePetByUserId(userid);
        petData = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i ++) {
            petData[i][0] = list.get(i).getPet_id();
//            petData[i][1] = new ProductManager().productName(list.get(i).getProduct_id());
            petData[i][1] = list.get(i).getPet_name();
            petData[i][2] = list.get(i).getNick_name();
//            petData[i][3] = list.get(i).getPet_owner();
            petData[i][3] = list.get(i).getPicture_path();
            petData[i][4] = list.get(i).getPet_age();
            petData[i][5] = list.get(i).getHealthy();
        }
        petModel.setDataVector(petData, petColumnName);
        this.petTable.validate();
        this.petTable.repaint();
    }

    public void reloadOrderData(String userid) {
        List<bean_order> list = new OrderManager().retrieveOrderByUserIdAccurate(userid);
        orderData = new Object[list.size()][7];
        try {
            for (int i = 0; i < list.size(); i ++) {
                orderData[i][0] = list.get(i).getOrder_id();
                orderData[i][1] = new ProductManager().productName(list.get(i).getProduct_id());
//                orderData[i][2] = list.get(i).getUser_id();
                orderData[i][2] = list.get(i).getQuantity();
                orderData[i][3] = list.get(i).getOrder_price();
                orderData[i][4] = list.get(i).getOrder_begin_time();
                orderData[i][5] = list.get(i).getOrder_end_time();
                orderData[i][6] = list.get(i).getTransfer_state();
            }
            orderModel.setDataVector(orderData, orderColumnName);
            orderTable.validate();
            orderTable.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadAppoData(String userid) {
        List<bean_appointment> list = new AppointmentManager().retrieveAoopintment(userid);
        appoimtmentData = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i ++) {
            appoimtmentData[i][0] = list.get(i).getAppointment_id();
//            appoimtmentData[i][1] = list.get(i).getUser_id();
            appoimtmentData[i][1] = new ProductManager().productName(list.get(i).getProduct_id());
            appoimtmentData[i][2] = new PetManager().petNickName(list.get(i).getPet_id());
            appoimtmentData[i][3] = list.get(i).getAppointment_begin_time();
            appoimtmentData[i][4] = list.get(i).getAppointment_end_time();
            appoimtmentData[i][5] = list.get(i).getCircumstance();
        }
        appoModel.setDataVector(appoimtmentData, appointmentColumnName);
        appoTable.validate();
        appoTable.repaint();
    }
}
