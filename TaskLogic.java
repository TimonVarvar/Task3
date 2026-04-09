package ru.vsu.cs.course1;

import java.util.Deque;


public class TaskLogic {

    private final Deque<Integer> s1; // входной стек
    private final Deque<Integer> s2; // выходной стек

    public TaskLogic(Deque<Integer> s1, Deque<Integer> s2) {
        this.s1 = s1;
        this.s2 = s2;
    }


    public void enqueue(int value) {
        s1.push(value);
    }


    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Очередь пуста");
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }


    public int peek() {
        if (isEmpty()) throw new RuntimeException("Очередь пуста");
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        return s2.peek();
    }

    public boolean isEmpty() {
        return s1.isEmpty() && s2.isEmpty();
    }

    public int size() {
        return s1.size() + s2.size();
    }

}
