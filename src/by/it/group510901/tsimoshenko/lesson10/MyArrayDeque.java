package by.it.group510901.tsimoshenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class MyArrayDeque<E> implements Deque<E> {

    private static final int DEFAULT_CAPACITY = 16;

    private E[] data;
    private int head; // индекс первого элемента
    private int size;

    public MyArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayDeque(int capacity) {
        if (capacity < 1) {
            capacity = DEFAULT_CAPACITY;
        }
        data = (E[]) new Object[capacity];
    }

    // ------------------------------------------------------------------
    // Вспомогательные методы
    // ------------------------------------------------------------------

    private void ensureCapacity() {
        if (size < data.length) {
            return;
        }
        E[] bigger = (E[]) new Object[data.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = data[(head + i) % data.length];
        }
        data = bigger;
        head = 0;
    }

    private static void requireNonNull(Object o) {
        if (o == null) {
            throw new NullPointerException("null elements are not allowed");
        }
    }

    // ------------------------------------------------------------------
    // Обязательные методы
    // ------------------------------------------------------------------

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result += ", ";
            }
            result += data[(head + i) % data.length];
        }
        return result + "]";
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public void addFirst(E element) {
        requireNonNull(element);
        ensureCapacity();
        head = (head - 1 + data.length) % data.length;
        data[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        requireNonNull(element);
        ensureCapacity();
        data[(head + size) % data.length] = element;
        size++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[head];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[(head + size - 1) % data.length];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E result = data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        return result;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        int last = (head + size - 1) % data.length;
        E result = data[last];
        data[last] = null;
        size--;
        return result;
    }

    // ------------------------------------------------------------------
    // Простые дополнительные методы
    // ------------------------------------------------------------------

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        while (size > 0) {
            pollFirst();
        }
        head = 0;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return pollFirst();
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return pollLast();
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public E peekFirst() {
        return size == 0 ? null : data[head];
    }

    @Override
    public E peekLast() {
        return size == 0 ? null : data[(head + size - 1) % data.length];
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (o.equals(data[(head + i) % data.length])) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}

