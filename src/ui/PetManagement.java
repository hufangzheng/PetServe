package ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import control.ProductManager;
import model.*;
import control.PetManager;
import util.BaseException;
import util.HibernateUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.util.*;
import java.util.List;

public class PetManagement extends JDialog {
    private JPanel buttonPane = new JPanel();
    private JPanel retrievePane = new JPanel();

    private JLabel jlbRetrieve = new JLabel("Retrieve by pet nick name:");
    private JButton jbtShow = new JButton("< Click here to view the picture >");
    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtAdd = new JButton("Add");
    private JButton jbtDelete = new JButton("Delete");
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");
    private JTextField jtfRetrieve = new JTextField(20);

    private String[] columnName = {"Pet ID", "Name", "Nick Name", "Owner", "Picture", "Pet Age", "Health"};
    private Object[][] petData;
    private DefaultTableModel defaultTableModel = new DefaultTableModel(petData, columnName);
    private JTable dataTable = new JTable(defaultTableModel);

    public PetManagement(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        this.setSize(800, 400);
        this.reloadPetList(jtfRetrieve.getText());
        dataTable.setGridColor(Color.GRAY);
        dataTable.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(dataTable));

        defaultTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int i = e.getFirstRow();
                int j = e.getColumn();
                if (i >= 0 && i < dataTable.getRowCount() && j >= 0 && j < dataTable.getColumnCount())
                    petData[i][j] = dataTable.getValueAt(i, j);
            }
        });

        this.getContentPane().add(retrievePane, BorderLayout.NORTH);
        retrievePane.add(jlbRetrieve);
        retrievePane.add(jtfRetrieve);
        retrievePane.add(jbtRetrieve);
        jbtRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadPetList(jtfRetrieve.getText());
            }
        });
        buttonPane.add(jbtShow);
        jbtShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select a pet.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    if (petData[i][4] == null)
                        JOptionPane.showMessageDialog(null, "This pet has no image.", "Hint", JOptionPane.INFORMATION_MESSAGE);
                    else {
                        DialogPetImage dialogPetImage = new DialogPetImage(null, "Image", true, (Blob) petData[i][4]);
                        dialogPetImage.setLocationRelativeTo(null);
                        dialogPetImage.setVisible(true);
                    }
                }
            }
        });
        buttonPane.add(jbtAdd);
        jbtAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DialogPetAdd dialogPetAdd = new DialogPetAdd(null, "Add Pet", true);
                dialogPetAdd.setLocationRelativeTo(null);
                dialogPetAdd.setVisible(true);

                reloadPetList(jtfRetrieve.getText());
            }
        });
        buttonPane.add(jbtDelete);
        jbtDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "Please select a row.", "Hint", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        int petId = new Integer(petData[i][0].toString()).intValue();
                        try {
                            new PetManager().deletePetInf(petId);

                            reloadPetList(jtfRetrieve.getText());
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
                    JOptionPane.showMessageDialog(null, "Please select a pet.", "Hint", JOptionPane.INFORMATION_MESSAGE);
                else {
//                    DialogPetModify dialogPetModify = new DialogPetModify(null, "Modify Pet", true, (int) petData[i][0]);
//                    dialogPetModify.setLocationRelativeTo(null);
//                    dialogPetModify.setVisible(true);
                    try {
                        bean_pet_information pet = HibernateUtil.getSession().get(bean_pet_information.class, (int) petData[i][0]);

//                        pet.setProduct_id((int) petData[i][1]);
                        pet.setPet_name(petData[i][1].toString());
                        pet.setNick_name(petData[i][2].toString());
                        pet.setPet_owner(petData[i][3].toString());
                        pet.setPet_age(new Integer(petData[i][5].toString()));
                        pet.setHealthy(petData[i][6].toString());

                        new PetManager().modifyPet(pet);
                        JOptionPane.showMessageDialog(null, "Succeed", "Modify Pet Information", JOptionPane.INFORMATION_MESSAGE);
                        reloadPetList(jtfRetrieve.getText());
                    } catch (BaseException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
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

    public void reloadPetList(String nickName) {
//        List<bean_pet_information> list = new PetManager().loadAllPet();
        List<bean_pet_information> list = new PetManager().retrievePet(nickName);
        petData = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i ++) {
            petData[i][0] = list.get(i).getPet_id();
            petData[i][1] = list.get(i).getPet_name();
            petData[i][2] = list.get(i).getNick_name();
            petData[i][3] = list.get(i).getPet_owner();
            petData[i][4] = list.get(i).getPicture_path();
            petData[i][5] = list.get(i).getPet_age();
            petData[i][6] = list.get(i).getHealthy();
        }
        defaultTableModel.setDataVector(petData, columnName);
        this.dataTable.validate();
        this.dataTable.repaint();
    }
}
