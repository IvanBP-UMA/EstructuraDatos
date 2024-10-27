package org.uma.ed.datastructures.set;

import org.uma.ed.datastructures.utils.Equals;
import org.uma.ed.datastructures.utils.ToString;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedSet<T> extends AbstractSortedSet<T> implements SortedSet<T>{

  private static final class Node<E> {
    E element; // Elemento almacenado en el nodo
    Node<E> next; // Referencia al siguiente nodo
    Node(E element, Node<E> next) {
      this.element = element;
      this.next = next;
    }
  }

  private final class Finder {
    boolean found; // Indica si se encontró el elemento objetivo
    Node<T> previous, current; // Referencias a anterior y actual
    Finder(T element) { // El constructor toma el elemento objetivo
      previous = null;
      current = first;
      int cmp = 0;
      while (current != null && (cmp = comparator.compare(element, current.element)) > 0) {
        previous = current;
        current = current.next;
      }
      found = current != null && cmp == 0;
    }
  }

  private final class SortedLinkedSetIterator implements Iterator<T> {
    Node<T> current;
    SortedLinkedSetIterator() {
      current = first;
    }
    @Override
    public boolean hasNext() {
      return current != null;
    }
    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("next on empty iterator");
      }
      T element = current.element;
      current = current.next;
      return element;
    }
  }

  private static final class SortedLinkedSetBuilder<T> {
    Node<T> first, last;
    int size;
    Comparator<T> comparator;
    SortedLinkedSetBuilder(Comparator<T> comparator) {
      this.first = null;
      this.last = null;
      this.size = 0;
      this.comparator = comparator;
    }
    void append(T element) {
      assert first == null || comparator.compare(element, last.element)
              > 0;
      Node<T> node = new Node<>(element, null);
      if (first == null) { // el builder estaba vacío
        first = node;
      } else {
        last.next = node;
      }
      last = node;
      size++;
    }
    // convierte el builder en un SortedLinkedSet en tiempo O(1)
    SortedLinkedSet<T> toSortedLinkedSet() {
      return new SortedLinkedSet<>(this);
    }
  }

  private Node<T> first;
  private final Comparator<T> comparator;
  private int size;

  public SortedLinkedSet(Comparator<T> comparator){
    this.comparator = comparator;
    this.first = null;
    this.size = 0;
  }

  private SortedLinkedSet(SortedLinkedSetBuilder<T> builder) {
    this.first = builder.first;
    this.size = builder.size;
    this.comparator = builder.comparator;
  }

  public static <T extends Comparable<? super T>> SortedLinkedSet<T> empty(){
    return new SortedLinkedSet<T>(Comparator.naturalOrder());
  }

  public static <T> SortedLinkedSet<T> empty(Comparator<T> comparator){
    return new SortedLinkedSet<>(comparator);
  }

  public static <T> SortedLinkedSet<T> of(Comparator<T> comparator, T... elements){
    SortedLinkedSet<T> res = SortedLinkedSet.empty(comparator);
    for (var el: elements){
      res.insert(el);
    }
    return res;
  }

  public static <T extends Comparable<? super T>> SortedLinkedSet<T> of(T... elements){
    return of(Comparator.naturalOrder(), elements);
  }

  public static <T> SortedLinkedSet<T> copyOf(SortedSet<T> set){
    SortedLinkedSet<T> res = SortedLinkedSet.empty(set.comparator());
    SortedLinkedSet<T> aux = SortedLinkedSet.empty(set.comparator());
    while (!set.isEmpty()){
      aux.insert(set.minimum());
      set.delete(set.minimum());
    }
    while(!aux.isEmpty()){
      set.insert(aux.minimum());
      res.insert(aux.minimum());
      aux.delete(aux.minimum());
    }
    return res;
  }

  public static <T> SortedLinkedSet<T> from(Comparator<T> comparator, Iterable<T> iterable){
    SortedLinkedSet<T> res = SortedLinkedSet.empty(comparator);
    for (var el: iterable){
      res.insert(el);
    }
    return res;
  }

  public static <T extends Comparable<? super T>> SortedLinkedSet<T> from(Iterable<T> iterable){
    return from(Comparator.naturalOrder(), iterable);
  }

  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

  @Override
  public T minimum() {
    if (isEmpty()){throw new NoSuchElementException("minimum on empty set");}
    return first.element;
  }

  @Override
  public T maximum() {
    if (isEmpty()) {throw new NoSuchElementException("maximum on empty set");}
    Node<T> aux = first;
    while (aux.next != null) {
      aux = aux.next;
    }
    return aux.element;
  }

  @Override
  public boolean isEmpty() {
    return first == null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insert(T element) {
    Finder finder = new Finder(element);
    if (!finder.found){
      Node<T> newNode = new Node<>(element, finder.current);
      if (finder.previous == null){
        first = newNode;
      }else{
        finder.previous.next = newNode;
      }
      size++;
    }
  }

  @Override
  public boolean contains(T element) {
    return new Finder(element).found;
  }

  @Override
  public void delete(T element) {
    Finder finder = new Finder(element);
    if (finder.found){
      if (finder.previous == null){
        first = first.next;
      }else{
        finder.previous.next = finder.current.next;
      }
      size--;
    }
  }

  @Override
  public void clear() {
    size = 0;
    first = null;
  }

  @Override
  public Iterator<T> iterator() {
    return new SortedLinkedSetIterator();
  }

  public static <T> SortedLinkedSet<T> union(SortedSet<T> set1, SortedSet<T> set2){
    if (!set1.comparator().equals(set2.comparator())){throw new IllegalArgumentException("union: both sorted sets must use same comparator");}
    Comparator<T> comparator = set1.comparator();
    SortedLinkedSetBuilder<T> res = new SortedLinkedSetBuilder<>(comparator);
    Iterator<T> iter1 = set1.iterator();
    Iterator<T> iter2 = set2.iterator();
    T current1 = null;
    T current2 = null;
    if (iter1.hasNext()){current1 = iter1.next();}
    if (iter2.hasNext()){current2 = iter2.next();}

    while (iter1.hasNext() && iter2.hasNext()){
      if (comparator.compare(current1, current2) < 0){
        res.append(current1);
        current1 = iter1.next();
      }else if(comparator.compare(current1, current2) == 0) {
        res.append(current1);
        current1 = iter1.next();
        current2 = iter2.next();
      }else{
       res.append(current2);
       current2 = iter2.next();
      }
    }

    while (iter1.hasNext()){
      res.append(current1);
      current1 = iter1.next();
    }

    if(current1 != null){
      res.append(current1);
    }

    while (iter2.hasNext()){
      res.append(current2);
      current2 = iter2.next();
    }

    if(current2 != null){
      res.append(current2);
    }

    return res.toSortedLinkedSet();
  }

  public static <T> SortedLinkedSet<T> intersection(SortedSet<T> set1, SortedSet<T> set2){
    if (!set1.comparator().equals(set2.comparator())){throw new IllegalArgumentException("Comparadores deben ser iguales");}
    Comparator<T> comparator = set1.comparator();
    SortedLinkedSetBuilder<T> res = new SortedLinkedSetBuilder<>(comparator);
    Iterator<T> iter1 = set1.iterator();
    Iterator<T> iter2 = set2.iterator();
    T current1 = iter1.next();
    T current2 = iter2.next();

    while (iter1.hasNext() && iter2.hasNext()){
      if (comparator.compare(current1, current2) < 0){
        current1 = iter1.next();
      }else if(comparator.compare(current1, current2) == 0) {
        res.append(current1);
        current1 = iter1.next();
        current2 = iter2.next();
      }else{
        current2 = iter2.next();
      }
    }

    return res.toSortedLinkedSet();
  }

  public static <T> SortedLinkedSet<T> difference(SortedSet<T> set1, SortedSet<T> set2){
    if (!set1.comparator().equals(set2.comparator())){throw new IllegalArgumentException("Comparadores deben ser iguales");}
    Comparator<T> comparator = set1.comparator();
    SortedLinkedSetBuilder<T> res = new SortedLinkedSetBuilder<>(comparator);
    Iterator<T> iter1 = set1.iterator();
    Iterator<T> iter2 = set2.iterator();
    T current1 = iter1.next();
    T current2 = iter2.next();

    while (iter1.hasNext() && iter2.hasNext()){
      if (comparator.compare(current1, current2) < 0){
        res.append(current1);
        current1 = iter1.next();
      }else if(comparator.compare(current1, current2) == 0) {
        current1 = iter1.next();
        current2 = iter2.next();
      }else{
        current2 = iter2.next();
      }
    }

    while (iter1.hasNext()){
      res.append(iter1.next());
    }

    return res.toSortedLinkedSet();
  }
}
