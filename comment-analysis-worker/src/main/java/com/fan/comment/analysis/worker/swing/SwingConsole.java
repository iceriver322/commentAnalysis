package com.fan.comment.analysis.worker.swing;

import javax.swing.*;

public class SwingConsole {
    public static void run(final JFrame f, final int width, final int heigh) {
        SwingUtilities.invokeLater(() -> {
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.setSize(width, heigh);
                    f.setVisible(true);
                }
        );
    }
}
