package ru.otus;

import java.util.*;
import java.util.function.UnaryOperator;

public class DIYArrayList<E> implements List<E> {
    // Default size of array 10
    private int size = 0;

    private Object[] elements;

    public DIYArrayList(int size) {
        if(size > 0) {
            this.elements = new Object[size];
        } else if(size == 0) {
            this.elements = new Object[10];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + size);
        }
    }
    public DIYArrayList() {
        this.elements = new Object[10];
    }

    private void grow() {
        int s = this.elements.length * 2;
        Object[] newElements = new Object[s];
        System.arraycopy(this.elements, 0, newElements, 0,this.elements.length);
        this.elements = newElements;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator c) {

    }

    @Override
    public Spliterator spliterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(E e) {
        if((this.size + 1) >= this.elements.length) this.grow();
        this.elements[this.size] = e;
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if(index > -1 && index < this.size) {
            return (E) this.elements[index];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public E set(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
