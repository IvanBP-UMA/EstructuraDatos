package org.uma.ed.dataestructure.stack;

public class LinkedStack<T> extends AbstractStack<T> implements Stack<T> {

    private static final class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<T> top;
    private int size;

    public LinkedStack() {
        top = null;
        size = 0;
    }

    public static <T> LinkedStack<T> empty() {
        return new LinkedStack<>();
    }

    @SafeVarargs
    public static <T> LinkedStack<T> of(T... elements) {
        LinkedStack<T> stack = new LinkedStack<>();
        for (var el: elements){
            stack.push(el);
        }
        return stack;
    }


    public static <T> LinkedStack<T> from(Iterable<T> iterable) {
        LinkedStack<T> stack = new LinkedStack<>();
        for (var el: iterable){
            stack.push(el);
        }
        return stack;
    }


    public static <T> LinkedStack<T> copyOf(LinkedStack<T> that) {
        return from(from(that.elements()).elements());
    }


    public static <T> LinkedStack<T> copyOf(Stack<T> that) {
        LinkedStack<T> aux = new LinkedStack<>();
        LinkedStack<T> stack = new LinkedStack<>();

        while (!that.isEmpty()) {
            aux.push(that.top());
            that.pop();
        }

        while (!aux.isEmpty()){
            stack.push(aux.top());
            that.push(aux.top());
            aux.pop();
        }

        return stack;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T top() {
        if (this.top == null){throw new EmptyStackException();}
        return this.top.element;
    }

    @Override
    public void pop() {
        if (this.size == 0){throw new EmptyStackException();}
        this.top = this.top.next;
        size--;
    }

    @Override
    public void push(T element) {
        this.top = new Node<T>(element, this.top);
        this.size++;
    }

    @Override
    public void clear() {
        this.top = null;
        this.size = 0;
    }


    protected Iterable<T> elements() {
        return () -> new java.util.Iterator<>() {
            Node<T> actual = top;

            public boolean hasNext() {
                return actual != null;
            }

            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T element = actual.element;
                actual = actual.next;
                return element;
            }
        };
    }
}
