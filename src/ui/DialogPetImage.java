package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Blob;
import java.sql.SQLException;

import model.*;

public class DialogPetImage extends JDialog {
    private ImageIcon petImage = null;
    private JLabel jbl = new JLabel();

    public DialogPetImage(JDialog jDialog, String title, boolean modal, Blob image) {
        super(jDialog, title, modal);

        try {
            byte[] buff = image.getBytes(1, image.getBinaryStream().available());
            petImage = new ImageIcon(buff);
            jbl.setIcon(petImage);

            this.setSize(petImage.getIconWidth(), petImage.getIconHeight());
            this.getContentPane().add(jbl, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
