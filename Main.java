package ru.vsu.cs.course1;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueueWindow window = new QueueWindow();
            window.setVisible(true);
        });
    }
}
