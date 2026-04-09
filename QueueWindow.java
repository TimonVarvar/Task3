package ru.vsu.cs.course1;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class QueueWindow extends JFrame {

    private Deque<Integer> s1;
    private Deque<Integer> s2;
    private TaskLogic queue;

    private JTextField inputField  = new JTextField(10);
    private JTextArea  outputArea  = new JTextArea(10, 30);

    public QueueWindow() {
        setTitle("Очередь через два стека");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // переключатель реализации
        JRadioButton stdBtn    = new JRadioButton("Стандартная (ArrayDeque)", true);
        JRadioButton customBtn = new JRadioButton("Самописная (SimpleLinkedList)");
        ButtonGroup bg = new ButtonGroup();
        bg.add(stdBtn); bg.add(customBtn);

        JPanel implPanel = new JPanel();
        implPanel.setBorder(BorderFactory.createTitledBorder("Реализация"));
        implPanel.add(stdBtn);
        implPanel.add(customBtn);

        stdBtn.addActionListener(e    -> resetQueue(false));
        customBtn.addActionListener(e -> resetQueue(true));

        // поле ввода + кнопки
        JPanel top = new JPanel();
        top.add(new JLabel("Значение:"));
        top.add(inputField);

        JButton enqBtn  = new JButton("Добавить");
        JButton deqBtn  = new JButton("Извлечь");
        JButton peekBtn = new JButton("Peek");
        JButton fileBtn = new JButton("Из файла");

        top.add(enqBtn);
        top.add(deqBtn);
        top.add(peekBtn);
        top.add(fileBtn);

        JPanel north = new JPanel(new BorderLayout());
        north.add(implPanel, BorderLayout.NORTH);
        north.add(top, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        // область вывода
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // инициализация
        resetQueue(false);

        // действия
        enqBtn.addActionListener(e -> {
            try {
                int val = Integer.parseInt(inputField.getText().trim());
                queue.enqueue(val);
                log("enqueue(" + val + ")");
                inputField.setText("");
            } catch (NumberFormatException ex) {
                log("Ошибка: введите число");
            }
        });

        deqBtn.addActionListener(e -> {
            try {
                log("dequeue() → " + queue.dequeue());
            } catch (Exception ex) {
                log("Очередь пуста!");
            }
        });

        peekBtn.addActionListener(e -> {
            try {
                log("peek() → " + queue.peek());
            } catch (Exception ex) {
                log("Очередь пуста!");
            }
        });

        fileBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser(".");
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try (Scanner sc = new Scanner(f)) {
                    int count = 0;
                    while (sc.hasNextInt()) {
                        queue.enqueue(sc.nextInt());
                        count++;
                    }
                    log("Загружено " + count + " элементов из " + f.getName());
                } catch (Exception ex) {
                    log("Ошибка чтения файла");
                }
            }
        });

        setSize(520, 380);
        setLocationRelativeTo(null);
    }

    private void resetQueue(boolean useCustom) {
        if (useCustom) {
            // Используем SimpleLinkedList вместо SimpleDeque
            s1 = new SimpleLinkedList<>();
            s2 = new SimpleLinkedList<>();
        } else {
            s1 = new ArrayDeque<>();
            s2 = new ArrayDeque<>();
        }
        queue = new TaskLogic(s1, s2);
        log("--- переключено: " + (useCustom ? "SimpleLinkedList" : "ArrayDeque") + " ---");
    }

    private void log(String msg) {
        outputArea.append(msg + "\n");
    }
}
