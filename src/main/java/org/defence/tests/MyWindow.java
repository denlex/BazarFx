package org.defence.tests;

import javax.swing.*;
import java.awt.*;

/**
 * Created by root on 8/6/15.
 */
public class MyWindow extends JFrame {
    public MyWindow() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyWindow();
            }
        });
    }
}
