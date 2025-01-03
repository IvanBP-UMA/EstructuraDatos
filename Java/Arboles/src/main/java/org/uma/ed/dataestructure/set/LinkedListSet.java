package org.uma.ed.dataestructure.set;

import java.util.Iterator;
import org.uma.ed.dataestructure.list.LinkedList;
import org.uma.ed.dataestructure.list.List;

/**
 * A LinkedListSet is a set data structure that maintains its elements in a
 * linked list order.
 * It implements the Set interface.
 *
 * @param <T> Type of elements in set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LinkedListSet<T> extends AbstractSet<T> implements Set<T> {
  /**
   * List of elements in set.
   */
  private final List<T> elements;

  /*
   * INVARIANT:
   * - `elements` contains elements in set in insertion order.
   * - No element is repeated in `elements`.
   */

  /**
   * Creates an empty LinkedListSet.
   * <p>
   * Time complexity: O(1)
   */
  public LinkedListSet() {
    elements = LinkedList.empty();
  }

  /**
   * Creates an empty LinkedListSet.
   * <p>
   * Time complexity: O(1)
   *
   * @param <T> Type of elements in set.
   *
   * @return an empty LinkedListSet.
   */
  public static <T> LinkedListSet<T> empty() {
    return new LinkedListSet<>();
  }

  /**
   * Creates a LinkedListSet with given elements.
   * <p>
   * Time complexity: O(n²)
   *
   * @param elements elements to be added to set.
   * @param <T>      Type of elements in set.
   *
   * @return a LinkedListSet with given elements.
   */
  @SafeVarargs
  public static <T> LinkedListSet<T> of(T... elements) {
    LinkedListSet<T> newSet = new LinkedListSet<>();
    for (var el: elements){
      newSet.insert(el);
    }
    return newSet;
  }

  /**
   * Creates a LinkedListSet with elements in given iterable.
   * <p>
   * Time complexity: O(n²)
   *
   * @param iterable {@code Iterable} of elements to be added to set.
   * @param <T>      Type of elements in iterable.
   *
   * @return a LinkedListSet with elements in given iterable.
   */
  public static <T> LinkedListSet<T> from(Iterable<T> iterable) {
    LinkedListSet<T> newSet = new LinkedListSet<>();
    for (var el: iterable){
      newSet.insert(el);
    }
    return newSet;
  }

  /**
   * Returns a new LinkedListSet with same elements as argument.
   * <p>
   * Time complexity: O(n)
   *
   * @param set set to be copied.
   * @param <T> Type of elements in set.
   *
   * @return a new LinkedListSet with same elements as {@code set}.
   */
  public static <T> LinkedListSet<T> copyOf(Set<T> set) {
    LinkedListSet<T> newSet = new LinkedListSet<>();
      for (T t : set) {
          newSet.insert(t);
      }
    return newSet;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return elements.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Time complexity: O(1)
   */
  @Override
  public int size() {
    return elements.size();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Time complexity: O(n)
   */
  @Override
  public void insert(T element) {
    if(!this.contains(element)){
      elements.append(element);
    }
  }

  /**
   * {@inheritDoc}
   * <p>
   * Time complexity: O(n)
   */
  @Override
  public boolean contains(T element) {
    return elements.contains(element);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Time complexity: O(n)
   */
  @Override
  public void delete(T element) {
    if (this.contains(element)){
      Iterator<T> iter = this.iterator();
      int counter = 0;
      while (iter.hasNext() && !iter.next().equals(element)){
        counter++;
      }
      elements.delete(counter);
    }
  }

  /**
   * {@inheritDoc}
   * <p>
   * Time complexity: O(1)
   */
  @Override
  public void clear() {
    elements.clear();
  }

  /**
   * Iterator over elements in set. Notice that {@code remove} method is not
   * supported. Note also that set structure
   * should not be modified during iteration as iterator state may become
   * inconsistent.
   *
   * @see Iterable#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return elements.iterator();
  }
}
