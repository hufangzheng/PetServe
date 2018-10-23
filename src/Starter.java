import ui.*;

import javax.swing.*;

public class Starter {
    public static void main(String[] args) {
        FrameMain frame = new FrameMain();
        frame.setTitle("Pets Server System");
        frame.setSize(600, 500);
//        this.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
