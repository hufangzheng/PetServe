package ui;

import control.AppointmentManager;
import control.PetManager;
import control.UserManager;
import model.bean_pet_information;

import javax.management.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class DialogBuyAppointment extends JDialog {
    private JPanel buttonPane = new JPanel();

    private JLabel jLabel = new JLabel("Select a pet to make appointment.");
    private JButton jbtSelect = new JButton("Select");

    private String[] petColumnName = {"Pet ID", "Name", "Nick Name", "Picture", "Pet Age", "Health"};
    private Object[][] petData;
    private DefaultTableModel petModel = new DefaultTableModel(petData, petColumnName);
    private JTable petTable = new JTable(petModel);

    public DialogBuyAppointment(JDialog jDialog, String title, boolean modal, int productId) {
        super(jDialog, title, modal);

        petTable.setGridColor(Color.GRAY);
        petTable.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(jLabel, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(petTable), BorderLayout.CENTER);
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);

        reloadPetData(UserManager.currentUser.getUser_id());

        buttonPane.add(jbtSelect, BorderLayout.SOUTH);
        jbtSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = petTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select a pet.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    bean_appointment newAppointment = new bean_appointment();
                    newAppointment.setProduct_id(productId);
                    newAppointment.setAppointment_begin_time(new java.sql.Date(System.currentTimeMillis()));
                    newAppointment.setCircumstance("Unaccomplished");
                    newAppointment.setUser_id(UserManager.currentUser.getUser_id());
                    newAppointment.setPet_id((int) petData[i][0]);

                    try {
                        new AppointmentManager().addAppointment(newAppointment);
                        JOptionPane.showMessageDialog(null, "Success", "Hint", JOptionPane.INFORMATION_MESSAGE);

                        setVisible(false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        this.setSize(500, 400);
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
}
