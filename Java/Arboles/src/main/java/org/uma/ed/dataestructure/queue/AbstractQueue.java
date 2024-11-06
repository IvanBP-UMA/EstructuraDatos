package org.uma.ed.dataestructure.queue;

import org.uma.ed.dataestructure.stack.AbstractStack;

import java.util.Objects;
import java.util.Queue;
import java.util.StringJoiner;

/**
 * This class provides a skeletal implementation of equals, hashCode and
 * toString methods to minimize the effort
 * required to implement these methods in concrete subclasses of the
 * {@link Queue} interface.
 *
 * @param <T> Type of elements in queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractQueue<T> {
    /**
     * This abstract method should be implemented in concrete subclasses to provide
     * an iterable over the elements in the
     * queue.
     *
     * @return an iterable over the elements in the queue.
     */
    protected abstract Iterable<T> elements();

    /**
     * This abstract method should be implemented in concrete subclasses to provide
     * the number of elements in the queue.
     *
     * @return the number of elements in the queue.
     */
    public abstract int size();

    private boolean equals(Iterable<T> it1, Iterable<?> it2) {
        var iter1 = it1.iterator();
        var iter2 = it2.iterator();
        while (iter1.hasNext() && iter2.hasNext() && iter1.next().equals(iter2.next())){}
        return !(iter1.hasNext() || iter2.hasNext());
    }

    /**
     * Checks whether this Queue and another Queue have same elements in same order.
     *
     * @param obj another object to compare to.
     *
     * @return {@code true} if {@code obj} is a Queue and has same elements as
     *         {@code this} in same order.
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj ||
                obj instanceof AbstractQueue<?> queue2 && size() == queue2.size() &&
                        equals(this.elements(), queue2.elements());
    }

    /**
     * Computes a hash code for this Queue.
     *
     * @return hash code for this Queue.
     */
    @Override
    public int hashCode() { // Itera sobre los elementos
        int hc = 1;
        for (var el: this.elements()){
            hc += hc*7 + el.hashCode();
        }
        return hc;
    }

    /**
     * Returns representation of this Queue as a String.
     */
    @Override
    public String toString() {
        String nombreClase = this.getClass().getSimpleName();
        StringJoiner sj = new StringJoiner("| ", nombreClase+ " (", ")");
        for (var el: elements()){
            sj.add(el.toString());
        }
        return sj.toString();
    }
}