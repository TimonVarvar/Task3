package ru.vsu.cs.course1;

import java.util.*;

public class SimpleLinkedList<T> implements Deque<T>, Iterable<T> {

    protected class SimpleLinkedListItem<T> {
        public T value;
        public SimpleLinkedListItem<T> next;
        public SimpleLinkedListItem<T> prev;
        public SimpleLinkedListItem(T value, SimpleLinkedListItem<T> next, SimpleLinkedListItem<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public SimpleLinkedListItem(T value) {
            this(value, null, null);
        }
    }

    protected SimpleLinkedListItem<T> head = null;
    protected SimpleLinkedListItem<T> tail = null;
    protected int count = 0;

    protected void checkEmpty() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
    }

    // ========== Deque методы ==========

    @Override
    public void addFirst(T t) {
        SimpleLinkedListItem<T> newItem = new SimpleLinkedListItem<>(t, head, null);
        if (isEmpty()) {
            head = tail = newItem;
        } else {
            head.prev = newItem;
            head = newItem;
        }
        count++;
    }

    @Override
    public void addLast(T t) {
        SimpleLinkedListItem<T> newItem = new SimpleLinkedListItem<>(t, null, tail);
        if (isEmpty()) {
            head = tail = newItem;
        } else {
            tail.next = newItem;
            tail = newItem;
        }
        count++;
    }

    @Override
    public boolean offerFirst(T t) {
        try {
            addFirst(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean offerLast(T t) {
        try {
            addLast(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public T removeFirst() {
        checkEmpty();
        T value = head.value;
        if (count == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        count--;
        return value;
    }

    @Override
    public T removeLast() {
        checkEmpty();
        T value = tail.value;
        if (count == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        count--;
        return value;
    }

    @Override
    public T pollFirst() {
        if (isEmpty()) return null;
        return removeFirst();
    }

    @Override
    public T pollLast() {
        if (isEmpty()) return null;
        return removeLast();
    }

    @Override
    public T getFirst() {
        checkEmpty();
        return head.value;
    }

    @Override
    public T getLast() {
        checkEmpty();
        return tail.value;
    }

    @Override
    public T peekFirst() {
        if (isEmpty()) return null;
        return head.value;
    }

    @Override
    public T peekLast() {
        if (isEmpty()) return null;
        return tail.value;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        SimpleLinkedListItem<T> current = head;
        while (current != null) {
            if (Objects.equals(o, current.value)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        SimpleLinkedListItem<T> current = tail;
        while (current != null) {
            if (Objects.equals(o, current.value)) {
                removeNode(current);
                return true;
            }
            current = current.prev;
        }
        return false;
    }

    // Удаление конкретного узла (вспомогательный метод)
    protected void removeNode(SimpleLinkedListItem<T> node) {
        if (node == head) {
            removeFirst();
        } else if (node == tail) {
            removeLast();
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            count--;
        }
    }

    // ========== Queue методы ==========

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    // ========== Stack методы ==========

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    // ========== Collection методы ==========

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            while (remove(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        SimpleLinkedListItem<T> current = head;
        while (current != null) {
            SimpleLinkedListItem<T> next = current.next;
            if (!c.contains(current.value)) {
                removeNode(current);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public void clear() {
        head = tail = null;
        count = 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T element : this) {
            if (Objects.equals(o, element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            SimpleLinkedListItem<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
        for (T element : this) {
            array[i++] = element;
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size()) {
            a = (T1[]) Arrays.copyOf(a, size(), a.getClass());
        }
        int i = 0;
        for (T element : this) {
            a[i++] = (T1) element;
        }
        if (a.length > size()) {
            a[size()] = null;
        }
        return a;
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {
            SimpleLinkedListItem<T> current = tail;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T value = current.value;
                current = current.prev;
                return value;
            }
        };
    }

    // ========== Дополнительные методы для работы по индексу ==========

    // O(index)
    protected SimpleLinkedListItem<T> getItem(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Incorrect index [%d]", index));
        }

        // Оптимизация: идём с ближайшего конца
        SimpleLinkedListItem<T> current;
        if (index < size() / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size() - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    // O(index)
    public T get(int index) {
        return getItem(index).value;
    }

    // O(index)
    public void insert(int index, T value) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(String.format("Incorrect index [%d]", index));
        }

        if (index == 0) {
            addFirst(value);
        } else if (index == size()) {
            addLast(value);
        } else {
            SimpleLinkedListItem<T> current = getItem(index);
            SimpleLinkedListItem<T> newItem = new SimpleLinkedListItem<>(value, current, current.prev);
            current.prev.next = newItem;
            current.prev = newItem;
            count++;
        }
    }

    // O(index)
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Incorrect index [%d]", index));
        }

        if (index == 0) {
            return removeFirst();
        } else if (index == size() - 1) {
            return removeLast();
        } else {
            SimpleLinkedListItem<T> toRemove = getItem(index);
            T value = toRemove.value;
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
            count--;
            return value;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        SimpleLinkedListItem<T> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}