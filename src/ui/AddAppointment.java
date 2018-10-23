package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.*;
import control.AppointmentManager;
import util.BaseException;

public class AddAppointment extends JDialog {
    private bean_appointment newAppo = new bean_appointment();

    private JPanel buttonPane = new JPanel();
    private JPanel textPane = new JPanel();
    private JPanel TimePane = new JPanel();

    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtCancel = new JButton("Cancel");
    private JComboBox jcbYear = new JComboBox(new Object[] {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"});
    private JComboBox jcbMonth = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
    private JComboBox jcbDate = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"});
    private JComboBox jcbYear2 = new JComboBox(new Object[] {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"});
    private JComboBox jcbMonth2 = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
    private JComboBox jcbDate2 = new JComboBox(new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"});
    private JComboBox jcbCircumstance = new JComboBox(new Object[] {"Unaccomplished", "Accomplish"});

    private JLabel jlbProductId = new JLabel("Product ID:");
    private JLabel jlbUserId = new JLabel("User ID:");
    private JLabel jlbPetId = new JLabel("Pet ID:");
    private JLabel jlbBeginTime = new JLabel("Begin Time:");
    private JLabel jlbEndTime = new JLabel("End Time:");
    private JLabel jlbCircumstance = new JLabel("Circumstance");
    private JTextField jtfProductId = new JTextField(20);
    private JTextField jtfUserId = new JTextField(20);
    private JTextField jtfPetId = new JTextField(20);
    private JTextField jtfCircumstance = new JTextField(20);

    public AddAppointment(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        TimePane.setLayout(new GridLayout(2, 4));
        TimePane.add(jlbBeginTime);
        jcbYear.setSelectedItem("2018");
        TimePane.add(jcbYear);
//        jcbYear.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                updateTime1();
//            }
//        });
        jcbMonth.setSelectedItem("1");
        TimePane.add(jcbMonth);
//        jcbMonth.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                updateTime1();
//            }
//        });
        jcbDate.setSelectedItem("1");
        TimePane.add(jcbDate);
//        jcbDate.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                updateTime1();
//            }
//        });
        TimePane.add(jlbEndTime);
        jcbYear2.setSelectedItem("2018");
        TimePane.add(jcbYear2);
//        jcbYear2.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                updateTime2();
//            }
//        });
        jcbMonth2.setSelectedItem("1");
        TimePane.add(jcbMonth2);
//        jcbMonth2.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                updateTime2();
//            }
//        });
        jcbDate2.setSelectedItem("1");
        TimePane.add(jcbDate2);
//        jcbDate2.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                updateTime2();
//            }
//        });

        textPane.setLayout(new GridLayout(6, 2));
//        textPane.add(jlbProductId);
//        textPane.add(jtfProductId);
        textPane.add(jlbUserId);
        textPane.add(jtfUserId);
        textPane.add(jlbPetId);
        textPane.add(jtfPetId);
        textPane.add(jlbCircumstance);
        textPane.add(jcbCircumstance);
//        textPane.add(jtfCircumstance);

        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime1();
                updateTime2();

//                newAppo.setProduct_id(new Integer(jtfProductId.getText()));
                newAppo.setUser_id(jtfUserId.getText());
                newAppo.setPet_id(new Integer(jtfPetId.getText()));
                newAppo.setCircumstance(jcbCircumstance.getSelectedItem().toString());
                try {
                    new AppointmentManager().addAppointment(newAppo);
                    JOptionPane.showMessageDialog(null, "Succeed", "Add Appointment", JOptionPane.INFORMATION_MESSAGE);
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
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
        this.setSize(500, 500);
        this.getContentPane().add(textPane, BorderLayout.CENTER);
        this.getContentPane().add(TimePane, BorderLayout.NORTH);
    }

    /*Update begin time and end time*/
    private void updateTime1() {
        String strBeginTime = jcbYear.getSelectedItem() + "-" + jcbMonth.getSelectedItem() + "-" + jcbDate.getSelectedItem();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newAppo.setAppointment_begin_time(new java.sql.Date(format.parse(strBeginTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
        }
    }
    private  void updateTime2() {
        String strEndTime = jcbYear2.getSelectedItem() + "-" + jcbMonth2.getSelectedItem() + "-" + jcbDate2.getSelectedItem();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newAppo.setAppointment_end_time(new java.sql.Date(format.parse(strEndTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
        }
    }
}
