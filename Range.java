package ru.vsu.cs.course1;

import java.util.Iterator;

public class Range implements Iterable<Integer> {
    private int first;
    private int last;

    public Range(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int curr = first;

            @Override
            public boolean hasNext() {
                return curr < last;
            }

            @Override
            public Integer next() {
                curr++;
                return curr - 1;
            }
        };
    }

    public static Range range(int first, int last) {
        return new Range(first, last);
    }
}