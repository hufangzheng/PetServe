package ui;

import control.TypeManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;
import util.BaseException;

public class DialogAddType extends JDialog {
    private JPanel buttonPane = new JPanel();
    private JPanel textPane = new JPanel();

    private JLabel jlbTypeName = new JLabel("Type Name:");
    private JLabel jlbDescribe = new JLabel("Describe:");
    private JTextField jtfTypeName = new JTextField(20);
    private JTextField jtfDescribe = new JTextField(20);
    private JButton jbtContinue = new JButton("Continue");
    private JButton jbtCancel = new JButton("Cancel");

    public DialogAddType(JDialog jDialog, String title, boolean modal) {
        super(jDialog, title, modal);

        add(textPane, BorderLayout.CENTER);
        textPane.setLayout(new GridLayout(2, 2));
        textPane.add(jlbTypeName);
        textPane.add(jtfTypeName);
        textPane.add(jlbDescribe);
        textPane.add(jtfDescribe);

        add(buttonPane, BorderLayout.SOUTH);
        buttonPane.add(jbtContinue);
        jbtContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bean_products_types newType = new bean_products_types();
                newType.setType_name(jtfTypeName.getText());
                newType.setType_describe(jtfDescribe.getText());

                try {
                    new TypeManagement().addType(newType);
                    JOptionPane.showMessageDialog(null, "Succeed", "Add Order", JOptionPane.INFORMATION_MESSAGE);
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
        this.setSize(300, 200);
    }
}
