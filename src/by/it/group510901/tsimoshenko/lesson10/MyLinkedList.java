package by.it.group510901.tsimoshenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size;

    // ------------------------------------------------------------------
    // Вспомогательные методы
    // ------------------------------------------------------------------

    private static void requireNonNull(Object o) {
        if (o == null) {
            throw new NullPointerException("null elements are not allowed");
        }
    }

    private E unlink(Node<E> node) {
        E item = node.item;
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return item;
    }

    private Node<E> nodeAt(int index) {
        if (index < size / 2) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    // ------------------------------------------------------------------
    // Обязательные методы
    // ------------------------------------------------------------------

    @Override
    public String toString() {
        String result = "[";
        for (Node<E> x = first; x != null; x = x.next) {
            result += x.item;
            if (x.next != null) {
                result += ", ";
            }
        }
        return result + "]";
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return unlink(nodeAt(index));
    }

    @Override
    public boolean remove(Object element) {
        return removeFirstOccurrence(element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(E element) {
        requireNonNull(element);
        Node<E> oldFirst = first;
        Node<E> node = new Node<>(null, element, oldFirst);
        first = node;
        if (oldFirst == null) {
            last = node;
        } else {
            oldFirst.prev = node;
        }
        size++;
    }

    @Override
    public void addLast(E element) {
        requireNonNull(element);
        Node<E> oldLast = last;
        Node<E> node = new Node<>(oldLast, element, null);
        last = node;
        if (oldLast == null) {
            first = node;
        } else {
            oldLast.next = node;
        }
        size++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.item;
    }

    @Override
    public E getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.item;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        return first == null ? null : unlink(first);
    }

    @Override
    public E pollLast() {
        return last == null ? null : unlink(last);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        while (first != null) {
            unlink(first);
        }
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
        if (first == null) {
            throw new NoSuchElementException();
        }
        return unlink(first);
    }

    @Override
    public E removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return unlink(last);
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
        return first == null ? null : first.item;
    }

    @Override
    public E peekLast() {
        return last == null ? null : last.item;
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
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            return false;
        }
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            return false;
        }
        for (Node<E> x = last; x != null; x = x.prev) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
        return false;
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