package ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.*;
import control.UserManager;
import util.BaseException;
import util.HibernateUtil;

public class DialogUserList extends JDialog{
    private JPanel buttonPane = new JPanel();
    private JPanel retrievePane = new JPanel();

    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtRetrieve2 = new JButton("Retrieve");
    private JButton jbtDetail = new JButton("Detail");
    private JButton jbtAdd = new JButton("Add");
    private JButton jbtDelete = new JButton("Delete");
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");

    private JTextField jtfRetrieve = new JTextField(20);
    private JTextField jtfRetrieve2 = new JTextField(20);
    private JLabel jlbRetrieve = new JLabel("Retrieve by user ID :");
    private JLabel jlbRetrieve2 = new JLabel("Retrieve by user name :");

    private String[] columnName =
            {"User Id", "Phone Number", "User Name", "Email", "Other Contact", "Authority"};
    private Object[][] userData;
    private DefaultTableModel defaultTableModel = new DefaultTableModel(userData, columnName);
    private JTable dataTable = new JTable(defaultTableModel);

    public DialogUserList(Dialog dialog, String title, boolean modal){
        super(dialog, title, modal);

        this.setSize(800, 400);
        this.reloadUserList();
        dataTable.setGridColor(Color.GRAY);
        dataTable.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(dataTable));

        defaultTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int i = e.getFirstRow();
                int j = e.getColumn();
                if (i >= 0 && i < dataTable.getRowCount() && j >= 0 && j < dataTable.getColumnCount())
                    userData[i][j] = dataTable.getValueAt(i, j);
            }
        });

        retrievePane.setLayout(new GridLayout(2, 3, 2, 2));
        retrievePane.add(jlbRetrieve);
        retrievePane.add(jtfRetrieve);
        retrievePane.add(jbtRetrieve);
        jbtRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadUserByID();
            }
        });
        retrievePane.add(jlbRetrieve2);
        retrievePane.add(jtfRetrieve2);
        retrievePane.add(jbtRetrieve2);
        jbtRetrieve2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadUserByName();
            }
        });
        add(retrievePane, BorderLayout.NORTH);

        buttonPane.add(jbtDetail);
        jbtDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select an user.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    UserRetrieve userRetrieve = new UserRetrieve(null, "~~ " + (String) userData[i][0] + " ~~", true, (String) userData[i][0]);
                    userRetrieve.setLocationRelativeTo(null);
                    userRetrieve.setVisible(true);
                }
            }
        });
        buttonPane.add(jbtAdd);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogRegister dialogRegister = new DialogRegister(null, "Add User", true);
                dialogRegister.setLocationRelativeTo(null);
                dialogRegister.setVisible(true);
                reloadUserList();
            }
        });
        buttonPane.add(jbtDelete);
        jbtDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "Please select an user", "Hint", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
                    {
                        String userid = userData[i][0].toString();
                        try {
                            new UserManager().deleteUser(userid);
                            reloadUserList();
                        } catch (BaseException e1) {
                            JOptionPane.showConfirmDialog(null, e1.getMessage(), "Fail", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Please select an user.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        bean_user_information user = HibernateUtil.getSession().get(bean_user_information.class, userData[i][0].toString());

                        user.setPhone_number(userData[i][1].toString());
//                        user.setPassword(userData[i][2].toString());
                        user.setUser_name(userData[i][2].toString());
                        user.setEmail(userData[i][3].toString());
                        user.setOther_contact(userData[i][4].toString());
                        user.setAuthority(userData[i][5].toString());

                        new UserManager().modifyUser(user);
                        JOptionPane.showMessageDialog(null, "Succeed", "Modify User", JOptionPane.INFORMATION_MESSAGE);
                        reloadUserList();
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

    private void reloadUserList() {
        List<bean_user_information> list = new UserManager().loadAllUser();
        userData = new Object[list.size()][6];
        try {
            for (int i = 0; i < list.size(); i ++) {
                userData[i][0] = list.get(i).getUser_id();
                userData[i][1] = list.get(i).getPhone_number();
                userData[i][2] = list.get(i).getUser_name();
                userData[i][3] = list.get(i).getEmail();
                userData[i][4] = list.get(i).getOther_contact();
                userData[i][5] = list.get(i).getAuthority();
            }
            defaultTableModel.setDataVector(userData, columnName);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadUserByID() {
        try {
            List<bean_user_information> list = new UserManager().retrieveUserById(jtfRetrieve.getText());

            userData = new Object[list.size()][6];
            for (int i = 0; i < list.size(); i ++) {
                userData[i][0] = list.get(i).getUser_id();
                userData[i][1] = list.get(i).getPhone_number();
                userData[i][2] = list.get(i).getUser_name();
                userData[i][3] = list.get(i).getEmail();
                userData[i][4] = list.get(i).getOther_contact();
                userData[i][5] = list.get(i).getAuthority();
            }
            defaultTableModel.setDataVector(userData, columnName);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    private  void reloadUserByName() {
        try {
            List<bean_user_information> list = new UserManager().retrieveUserById(jtfRetrieve2.getText());

            userData = new Object[list.size()][6];
            for (int i = 0; i < list.size(); i ++) {
                userData[i][0] = list.get(i).getUser_id();
                userData[i][1] = list.get(i).getPhone_number();
                userData[i][2] = list.get(i).getUser_name();
                userData[i][3] = list.get(i).getEmail();
                userData[i][4] = list.get(i).getOther_contact();
                userData[i][5] = list.get(i).getAuthority();
            }
            defaultTableModel.setDataVector(userData, columnName);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }
}
