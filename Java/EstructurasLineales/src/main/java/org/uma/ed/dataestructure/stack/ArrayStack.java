package org.uma.ed.dataestructure.stack;

import java.util.Arrays;

public class ArrayStack<T> extends AbstractStack<T> implements Stack<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private T[] elements;
    private int size;


    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initial capacity must be greater than 0");
        }
        elements = (T[]) new Object[initialCapacity];
        size = 0;
    }

    public ArrayStack() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public static <T> ArrayStack<T> empty() {
        return new ArrayStack<>();
    }

    public static <T> ArrayStack<T> withCapacity(int initialCapacity) {
        return new ArrayStack<>(initialCapacity);
    }


    @SafeVarargs
    public static <T> ArrayStack<T> of(T... elements) {
        ArrayStack<T> stack = ArrayStack.withCapacity(elements.length);
        for (var el: elements){
            stack.push(el);
        }
       return stack;
    }

    public static <T> ArrayStack<T> from(Iterable<T> iterable) {
        ArrayStack<T> stack = ArrayStack.empty();
        for (var el: iterable){
            stack.push(el);
        }
        return stack;
    }


    public static <T> ArrayStack<T> copyOf(ArrayStack<T> that) {
        ArrayStack<T> stack = new ArrayStack<>(that.size());
        if (that.size() > 1){
            System.arraycopy(that.elements, 0, stack.elements, 0, that.size);
        }
        return stack;
    }

    public static <T> ArrayStack<T> copyOf(Stack<T> that) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public int size() {
        return -1;
    }


    @Override
    public void push(T element) {
        //
    }

    @Override
    public T top() {
        //
        return null;
    }
    
    @Override
    public void pop() {
       //
    }


    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            elements[i] = null;
        }
        size = 0;
    }


    protected Iterable<T> elements() {
        return () -> new java.util.Iterator<>() {
            int actual = size - 1;

            public boolean hasNext() {
                return actual >= 0;
            }

            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T element = elements[actual];
                actual--;
                return element;
            }
        };
    }
}