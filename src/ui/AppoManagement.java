package ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import control.ProductManager;
import model.*;
import control.AppointmentManager;
import util.BaseException;
import util.HibernateUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class AppoManagement extends JDialog {
    private JPanel buttonPane = new JPanel();
    private JPanel retrievePane = new JPanel();

    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtAdd = new JButton("Add");
    private JButton jbtDelete = new JButton("Delete");
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");
    private JTextField jtfRetrieve = new JTextField(20);
    private JLabel jlbRetrieve = new JLabel("user id:");

    private String[] columnName = {"Appointment ID", "Product Name", "User ID", "Pet ID", "Begin Time", "End Time", "Circumstance"};
    private Object[][] appoData;
    private DefaultTableModel defaultTableModel = new DefaultTableModel(appoData, columnName);
    private JTable dataTable = new JTable(defaultTableModel);

    public AppoManagement(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        this.setSize(800, 400);
        this.reloadAppointment(jtfRetrieve.getText());
        dataTable.setGridColor(Color.GRAY);
        dataTable.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(dataTable));

        defaultTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int i = e.getFirstRow();
                int j = e.getColumn();
                if (i >= 0 && i < dataTable.getRowCount() && j >= 0 && j < dataTable.getColumnCount())
                appoData[i][j] = dataTable.getValueAt(i, j);
            }
        });

        retrievePane.add(jlbRetrieve);
        retrievePane.add(jtfRetrieve);
        retrievePane.add(jbtRetrieve);
        this.getContentPane().add(retrievePane, BorderLayout.NORTH);
        jbtRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadAppointment(jtfRetrieve.getText());
            }
        });

        buttonPane.add(jbtAdd);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAppointment addAppointment = new AddAppointment(null, "Add Appointment", true);
                addAppointment.setLocationRelativeTo(null);
                addAppointment.setVisible(true);

                reloadAppointment(jtfRetrieve.getText());
            }
        });
        buttonPane.add(jbtDelete);
        jbtDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select a row.");
                else {
                    if (JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        int appoId = new Integer(appoData[i][0].toString()).intValue();
                        try {
                            new AppointmentManager().deleteAppointment(appoId);

                            reloadAppointment(jtfRetrieve.getText());
                        } catch (BaseException e1) {
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
                    JOptionPane.showMessageDialog(null, "Please select a appointment.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        bean_appointment appo = HibernateUtil.getSession().get(bean_appointment.class, (int) appoData[i][0]);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//                        appo.setProduct_id(new Integer(appoData[i][1].toString()).intValue());
                        appo.setUser_id(appoData[i][2].toString());
                        appo.setAppointment_begin_time(new java.sql.Date(format.parse(appoData[i][4].toString()).getTime()));
                        if (appoData[i][5] != null)
                             appo.setAppointment_end_time(new java.sql.Date(format.parse(appoData[i][5].toString()).getTime()));
                        appo.setCircumstance(appoData[i][6].toString());

                        new AppointmentManager().modifyAppointment(appo);
                        JOptionPane.showMessageDialog(null, "Succeed", "Modify Appointment", JOptionPane.INFORMATION_MESSAGE);
                        reloadAppointment(jtfRetrieve.getText());
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
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

    private void reloadAppointment(String userid) {
//        List<bean_appointment> list = new AppointmentManager().loadAllAppoin();
        List<bean_appointment> list = new AppointmentManager().retrieveAoopintment(userid);
        appoData = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i ++) {
            appoData[i][0] = list.get(i).getAppointment_id();
            appoData[i][1] = new ProductManager().productName(list.get(i).getProduct_id());
            appoData[i][2] = list.get(i).getUser_id();
            appoData[i][3] = list.get(i).getPet_id();
            appoData[i][4] = list.get(i).getAppointment_begin_time();
            appoData[i][5] = list.get(i).getAppointment_end_time();
            appoData[i][6] = list.get(i).getCircumstance();
        }
        defaultTableModel.setDataVector(appoData, columnName);
        this.dataTable.validate();
        this.dataTable.repaint();
    }
}
