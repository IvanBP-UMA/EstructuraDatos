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
            stack.size = that.size();
        }
        return stack;
    }

    public static <T> ArrayStack<T> copyOf(Stack<T> that) {
        ArrayStack<T> newStack = new ArrayStack<>(that.size());
        ArrayStack<T> aux = new ArrayStack<>(that.size());

        while (!that.isEmpty()){
            aux.push(that.top());
            that.pop();
        }

        while (!aux.isEmpty()){
            that.push(aux.top());
            newStack.push(aux.top());
            aux.pop();
        }

        return newStack;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public void push(T element) {
        if (size == elements.length){
            System.arraycopy(this.elements, 0, this.elements, 0, this.elements.length * 2);
        }
        elements[size] = element;
        size++;
    }

    @Override
    public T top() {
        if (this.isEmpty()){throw new EmptyStackException();}
        return elements[size - 1];
    }
    
    @Override
    public void pop() {
        if (this.isEmpty()){throw new EmptyStackException();}
       size--;
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
