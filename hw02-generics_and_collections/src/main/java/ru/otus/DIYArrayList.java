package ru.otus;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) this.elements, 0, size, c);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        if(this.size == this.elements.length) this.grow();
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
        throw new UnsupportedOperationException();
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
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        this.elements[index] = element;
        return element;
    }

    @Override
    public void add(int index, E element) {
        this.elements[index] = element;
    }

    @Override
    public E remove(int index) {
        Object[] newElements = new Object[this.elements.length -1];
        Object removedElement = this.elements[index];
        this.elements[index] = null;
        for(int i = 0, j = 0; i < this.elements.length; i++) {
            if(this.elements[i] != null) {
                newElements[j] = this.elements[i];
                j++;
            }
        }
        this.elements = newElements;
        return (E) removedElement;
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
        return new ListItr(0);
    }

    @Override
    public ListIterator listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        // prevent creating a synthetic constructor
        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYArrayList.this.elements;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                DIYArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
         throw new UnsupportedOperationException();
        }

    }


    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = elements;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                DIYArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            try {
                int i = cursor;
                DIYArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

}
