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

public class ProductManagement extends JDialog {
    private JPanel buttonPane = new JPanel();
    private JPanel retrievePane = new JPanel();

    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtAdd = new JButton("Add");
    private JButton jbtDelete = new JButton("Delete");
    private JButton jbtModify = new JButton("Modify");
    private JButton jbtQuit = new JButton("Quit");
    private JLabel jlbRetrieve = new JLabel("Retrieve by product name :");
    private JTextField jtfRetrieve = new JTextField(20);
    private JComboBox jcbType = new JComboBox(new Object[] {"", "Pet", "Pet's Food", "Other Products", "Appointment"});

    private String[] columnName = {"Product ID", "Type ID", "Type Name", "Product Name", "Brand", "Price", "Code"};
    private Object[][] productData;

    private DefaultTableModel defaultTableModel = new DefaultTableModel(productData, columnName);
    private JTable dataTable = new JTable(defaultTableModel);

    public ProductManagement(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        this.setSize(800, 400);
        this.reloadProductList(jcbType.getSelectedItem().toString());
        dataTable.setGridColor(Color.GRAY);
        dataTable.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(dataTable));

        defaultTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int i = e.getFirstRow();
                int j = e.getColumn();
                if (i >= 0 && i < dataTable.getRowCount() && j >= 0 && j < dataTable.getColumnCount())
                    productData[i][j] = dataTable.getValueAt(i, j);
            }
        });

        retrievePane.add(jlbRetrieve);
        retrievePane.add(jtfRetrieve);
        retrievePane.add(jcbType);
        retrievePane.add(jbtRetrieve);
        this.getContentPane().add(retrievePane, BorderLayout.NORTH);
        jbtRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadProductList(jcbType.getSelectedItem().toString());
            }
        });
        buttonPane.add(jbtAdd);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogProductAdd dialogProductAdd = new DialogProductAdd(null, "Add Product", true);
                dialogProductAdd.setLocationRelativeTo(null);
                dialogProductAdd.setVisible(true);

                reloadProductList(jcbType.getSelectedItem().toString());
            }
        });
        buttonPane.add(jbtDelete);
        jbtDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = dataTable.getSelectedRow();
                if (i < 0)
                    JOptionPane.showMessageDialog(null, "Please select a row.", "Hint", JOptionPane.ERROR_MESSAGE);
                else {
                    if (JOptionPane.showConfirmDialog(null, "Delete ?", "Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        int proId = new Integer(productData[i][0].toString());
                        try {
                            new ProductManager().deleteProduct(proId);

                            reloadProductList(jcbType.getSelectedItem().toString());
                        } catch (BaseException e2) {
                            JOptionPane.showMessageDialog(null, e2.getMessage(), "Hint", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Please select a product.", "Modify Product", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        bean_products_information product = HibernateUtil.getSession().get(bean_products_information.class, (int) productData[i][0]);

                        product.setTypes(new Integer(productData[i][1].toString()).intValue());
                        product.setProduct_name(productData[i][3].toString());
                        if (productData[i][4] != null)
                            product.setBrand(productData[i][4].toString());
                        product.setPrice(new Double(productData[i][5].toString()).doubleValue());
                        product.setProduct_code(productData[i][6].toString());

                        new ProductManager().modifyProduct(product);
                        JOptionPane.showMessageDialog(null, "Succeed", "Modify Product", JOptionPane.INFORMATION_MESSAGE);
                        reloadProductList(jcbType.getSelectedItem().toString());
                    }  catch (BaseException e1) {
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

    public void reloadProductList(String typeName) {
//        List<bean_products_information> list = new ProductManager().loadAllProducts();
        List<bean_products_information> list = new ProductManager().retrieveProduct(jtfRetrieve.getText(), typeName);
        productData = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i ++) {
            productData[i][0] = list.get(i).getProduct_id();
            productData[i][1] = list.get(i).getTypes();
            productData[i][2] = new TypeManagement().typeName(list.get(i).getTypes());
            productData[i][3] = list.get(i).getProduct_name();
            productData[i][4] = list.get(i).getBrand();
            productData[i][5] = list.get(i).getPrice();
            productData[i][6] = list.get(i).getProduct_code();
        }
        defaultTableModel.setDataVector(productData, columnName);
        this.dataTable.validate();
        this.dataTable.repaint();
    }
}
