package by.it.group510901.tsimoshenko.lesson10;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;


public class MyPriorityQueue<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 11;

    private E[] heap;
    private int size;
    private final Comparator<? super E> comparator;

    public MyPriorityQueue() {
        this(DEFAULT_CAPACITY, null);
    }

    public MyPriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }


    public MyPriorityQueue(int capacity, Comparator<? super E> comparator) {
        if (capacity < 1) {
            capacity = DEFAULT_CAPACITY;
        }
        this.heap = (E[]) new Object[capacity];
        this.comparator = comparator;
    }

    // ------------------------------------------------------------------
    // Вспомогательные методы кучи
    // ------------------------------------------------------------------

    private int compare(E a, E b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super E>) a).compareTo(b);
    }

    private void grow() {
        E[] bigger = (E[]) new Object[heap.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = heap[i];
        }
        heap = bigger;
    }

    private void siftUp(int k, E x) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            if (compare(x, heap[parent]) >= 0) {
                break;
            }
            heap[k] = heap[parent];
            k = parent;
        }
        heap[k] = x;
    }

    private void siftDown(int k, E x) {
        int half = size >>> 1;
        while (k < half) {
            int child = 2 * k + 1;
            int right = child + 1;
            if (right < size && compare(heap[child], heap[right]) > 0) {
                child = right;
            }
            if (compare(x, heap[child]) <= 0) {
                break;
            }
            heap[k] = heap[child];
            k = child;
        }
        heap[k] = x;
    }

    private void removeAt(int i) {
        int last = --size;
        E moved = heap[last];
        heap[last] = null;
        if (i == last) {
            return;
        }
        siftDown(i, moved);
        if (heap[i] == moved) {
            siftUp(i, moved);
        }
    }

    private void heapify() {
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            siftDown(i, heap[i]);
        }
    }


    private boolean filter(Collection<?> c, boolean retain) {
        if (c == null) {
            throw new NullPointerException();
        }
        int j = 0;
        for (int i = 0; i < size; i++) {
            E item = heap[i];
            if (c.contains(item) == retain) {
                heap[j++] = item;
            }
        }
        boolean changed = j != size;
        for (int i = j; i < size; i++) {
            heap[i] = null;
        }
        size = j;
        if (changed) {
            heapify();
        }
        return changed;
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
            result += heap[i];
        }
        return result + "]";
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public E remove() {
        E result = poll();
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    @Override
    public boolean contains(Object element) {
        if (element == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (element.equals(heap[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E element) {
        if (element == null) {
            throw new NullPointerException("null elements are not allowed");
        }
        if (comparator == null && !(element instanceof Comparable)) {
            throw new ClassCastException("Element is not Comparable and no Comparator given");
        }
        if (size == heap.length) {
            grow();
        }
        size++;
        siftUp(size - 1, element);
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E result = heap[0];
        int last = --size;
        E x = heap[last];
        heap[last] = null;
        if (size > 0) {
            siftDown(0, x);
        }
        return result;
    }

    @Override
    public E peek() {
        return size == 0 ? null : heap[0];
    }

    @Override
    public E element() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            throw new IllegalArgumentException("Cannot add a queue to itself");
        }
        boolean changed = false;
        for (E e : c) {
            if (offer(e)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == this) {
            boolean changed = size > 0;
            clear();
            return changed;
        }
        return filter(c, false);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == this) {
            return false;
        }
        return filter(c, true);
    }

    // ------------------------------------------------------------------
    // Остальные методы интерфейса Queue / Collection
    // ------------------------------------------------------------------

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (o.equals(heap[i])) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                return heap[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = heap[i];
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }



}