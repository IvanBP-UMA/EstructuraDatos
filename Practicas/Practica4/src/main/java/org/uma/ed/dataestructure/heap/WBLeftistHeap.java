package org.uma.ed.dataestructure.heap;

import java.util.Comparator;
import org.uma.ed.dataestructure.list.ArrayList;

/**
 * Heap implemented using weight biased leftist heap-ordered binary trees.
 *
 * @param <T> Type of elements in heap.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class WBLeftistHeap<T> implements Heap<T> {
  private static final class Node<E> {
    E element;
    int weight; // number of elements in tree
    Node<E> left, right;

    Node(E element, int weight, Node<E> left, Node<E> right) {
      this.element = element;
      this.weight = weight;
      this.left = left;
      this.right = right;
    }

    Node(E element) {
      this(element, 1, null, null);
    }
  }

  /*
   * INVARIANT:
   * - All nodes adhere to the Heap Order Property (HOP): a node's element is ≤ its children's elements.
   * - The tree is structured as a weight biased leftist heap:
   *   - Each node's left child has a weight (total elements) ≥ the right child's weight.
   *   - This ensures the right spine's length is logarithmic relative to the heap's size.
   */

  /**
   * Comparator used to order elements in heap.
   */
  private final Comparator<T> comparator;

  /**
   * Reference to root of this heap.
   */
  private Node<T> root;

  private WBLeftistHeap(Comparator<T> comparator, Node<T> root) {
    this.comparator = comparator;
    this.root = root;
  }

  /**
   * Creates an empty Weight Biased Leftist Heap.
   * <p> Time complexity: O(1)
   */
  public WBLeftistHeap(Comparator<T> comparator) {
    this(comparator, null);
  }

  public static <T> WBLeftistHeap<T> empty(Comparator<T> comparator) {
    return new WBLeftistHeap<>(comparator);
  }

  public static <T extends Comparable<? super T>> WBLeftistHeap<T> empty() {
    return new WBLeftistHeap<T>(Comparator.naturalOrder());
  }

  /**
   * Constructs a Weight Biased Leftist Heap from a list of singleton nodes in O(n) time.
   * @param comparator comparator to use
   * @param nodes list of singleton nodes (Node<T> is a heap with a single node at the begining).
   * @param <T> type of elements
   *
   * @return skew heap with elements in nodes
   */
  private static <T> WBLeftistHeap<T> merge(Comparator<T> comparator, ArrayList<Node<T>> nodes) {
    WBLeftistHeap<T> res = empty(comparator);
    int jump = 2;
    int elementsToPair = nodes.size();
    int auxi = 0;
    while (jump < nodes.size()){
      for (int i = 0; i<= nodes.size()-jump; i+=jump){
        res.root = res.merge(nodes.get(i), nodes.get(i+(jump/2)));
        nodes.set(i, res.root);
        res.clear();
        auxi += jump;
      }
      if (elementsToPair % 2 != 0){
        res.root = res.merge(nodes.get(auxi-jump), nodes.get(auxi));
        nodes.set(auxi-jump, res.root);
        res.clear();
      }
      jump *= 2;
      elementsToPair /= 2;
      auxi = 0;
    }
    res.root = nodes.get(0);
    return res;
  }

  /**
   * Constructs a Weight Biased Leftist Heap from a sequence of elements and a comparator.
   * <p>
   * Time complexity: O(n)
   * @param comparator comparator to use
   * @param elements elements to insert in heap
   * @param <T> type of elements
   *
   * @return a Weight Biased Leftist Heap with elements in sequence
   */
  @SafeVarargs
  public static <T> WBLeftistHeap<T> of(Comparator<T> comparator, T... elements) {
    ArrayList<Node<T>> nodes = ArrayList.withCapacity(elements.length);
    for (T element : elements) {
      nodes.append(new Node<>(element));
    }
    return merge(comparator, nodes);
  }

  /**
   * Constructs a Weight Biased Leftist Heap from a sequence of elements and natural order comparator.
   * <p>
   * Time complexity: O(n)
   * @param elements elements to insert in heap
   * @param <T> type of elements
   *
   * @return a Weight Biased Leftist Heap with elements in sequence
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> WBLeftistHeap<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Constructs a Weight Biased Leftist Heap from an iterable collection and a comparator.
   * <p>
   * Time complexity: O(n)
   * @param comparator comparator to use
   * @param iterable collection of elements to insert in heap
   * @param <T> type of elements
   *
   * @return a Weight Biased Leftist Heap with elements in collection
   */
  public static <T> WBLeftistHeap<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    ArrayList<Node<T>> nodes = ArrayList.empty();
    for (T element : iterable) {
      nodes.append(new Node<>(element));
    }
    return merge(comparator, nodes);
  }

  /**
   * Constructs a Weight Biased Leftist Heap from an iterable collection of elements and natural order comparator.
   * <p>
   * Time complexity: O(n)
   * @param iterable collection of elements to insert in heap
   * @param <T> type of elements
   *
   * @return a Weight Biased Leftist Heap with elements in collection
   */
  public static <T extends Comparable<? super T>> WBLeftistHeap<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }


  // Copia de forma recursiva, ojo que nunca copiamos el elemento, solo su referncia a él.
  private static <T> Node<T> copyOf(Node<T> node) {
    if (node == null) {
      return null;
    } else {
      return new Node<>(node.element, node.weight, copyOf(node.left), copyOf(node.right));
    }
  }

  /**
   * <p> Time complexity: O(n)
   */
  public static <T> WBLeftistHeap<T> copyOf(WBLeftistHeap<T> that) {
    WBLeftistHeap<T> res = empty(that.comparator());
    res.root = copyOf(that.root);
    return res;
  }



  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return weight(root);
  }

  private static <T> int weight(Node<T> node) {
    return node == null ? 0 : node.weight;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() {
    root = null;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(T element) {
    root = merge(root, new Node<>(element));
  }

  /* Merges two heap trees along their right spines.
   * Returns merged heap. Reuses nodes during merge
   */
  private Node<T> merge(Node<T> node1, Node<T> node2) {
    Node<T> res;
    if (node1 == null){
      res = node2;
    }else if(node2 == null){
      res =  node1;
    }else{
      Node<T> aux;
      if (comparator.compare(node1.element, node2.element) < 0){
        res = node1;
        aux = node2;
      }else{
        res = node2;
        aux = node1;
      }
      res.right = merge(res.right, aux);
      if (weight(res.left) < weight(res.right)){
        aux = res.left;
        res.left = res.right;
        res.right = aux;
      }
      res.weight = 1 + weight(res.right)+weight(res.left);
    }
    return res;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   *
   * @throws <code>EmptyHeapException</code> if heap stores no element.
   */
  @Override
  public T minimum() {
    if (root == null){throw new EmptyHeapException();}
    return root.element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   *
   * @throws <code>EmptyHeapException</code> if heap stores no element.
   */
  @Override
  public void deleteMinimum() {
    if (root == null){throw new EmptyHeapException();}
    root = merge(root.left, root.right);
  }

  /**
   * Returns representation of this heap as a String.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringBuilder sb = new StringBuilder(className).append("(");
    toString(sb, root);
    sb.append(")");
    return sb.toString();
  }

  private static void toString(StringBuilder sb, Node<?> node) {
    if (node == null) {
      sb.append("null");
    } else {
      String className = node.getClass().getSimpleName();
      sb.append(className).append("(");
      toString(sb, node.left);
      sb.append(", ");
      sb.append(node.element);
      sb.append(", ");
      toString(sb, node.right);
      sb.append(")");
    }
  }
}
