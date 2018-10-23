package ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import control.ProductManager;
import control.TypeManagement;
import model.*;
import control.PetManager;
import util.BaseException;
import util.HibernateUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TypeManage extends JDialog {
    private JPanel buttonPane = new JPanel();

    private JButton jbtAdd = new JButton("Add");
    private JButton jbtDelete = new JButton("Delete");
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");

    private String[] typeColumn = {"Type ID", "Type Name", "Describe"};
    private Object[][] typeData;
    private DefaultTableModel defaultTableModel = new DefaultTableModel(typeData, typeColumn);
    private JTable dataTable = new JTable(defaultTableModel);

    public TypeManage(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        reloadType();

        dataTable.setGridColor(Color.GRAY);
        dataTable.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(dataTable));

        defaultTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int i = e.getFirstRow();
                int j = e.getColumn();
                if (i >= 0 && i < dataTable.getRowCount() && j >= 0 && j < dataTable.getColumnCount())
                    typeData[i][j] = dataTable.getValueAt(i, j);
            }
        });

        add(buttonPane, BorderLayout.SOUTH);
        buttonPane.add(jbtAdd);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogAddType dialogAddType = new DialogAddType(null, "Add Type", true);
                dialogAddType.setLocationRelativeTo(null);
                dialogAddType.setVisible(true);

                reloadType();
            }
        });
        buttonPane.add(jbtDelete);
        jbtDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select a type.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    if (JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            new TypeManagement().deleteType((int) typeData[i][0]);

                            reloadType();
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
                    JOptionPane.showMessageDialog(null, "Please select a type.", "Modify Type", JOptionPane.ERROR_MESSAGE);
                else {
                    bean_products_types t = HibernateUtil.getSession().get(bean_products_types.class, (int) typeData[i][0]);
                    t.setType_name(typeData[i][1].toString());
                    if (typeData[i][2] != null)
                        t.setType_describe(typeData[i][2].toString());
                    try {
                        new TypeManagement().modifyType(t);
                        JOptionPane.showMessageDialog(null, "Success", "Add type", JOptionPane.INFORMATION_MESSAGE);
                        reloadType();
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

        this.setSize(500, 400);
    }

    public void reloadType() {
        List<bean_products_types> list = new TypeManagement().reloadAllTypes();
        typeData = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i ++) {
            typeData[i][0] = list.get(i).getType_id();
            typeData[i][1] = list.get(i).getType_name();
            typeData[i][2] = list.get(i).getType_describe();
        }
        defaultTableModel.setDataVector(typeData, typeColumn);
        dataTable.validate();
        dataTable.repaint();
    }
}
