package org.uma.ed.dataestructure.heap;

import org.uma.ed.dataestructure.list.ArrayList;

import java.util.Comparator;

public class MaxiphobicHeap<T> implements Heap<T>{
  private final Comparator<T> comparator;
  private Node<T> root;

  private static final class Node<E> {
    E element;
    int weight; // number of elements in tree
    MaxiphobicHeap.Node<E> left, right;

    Node(E element, int weight, MaxiphobicHeap.Node<E> left, MaxiphobicHeap.Node<E> right) {
      this.element = element;
      this.weight = weight;
      this.left = left;
      this.right = right;
    }

    Node(E element) {
      this(element, 1, null, null);
    }
  }
  private MaxiphobicHeap(Comparator<T> comparator, Node<T> root){
    this.comparator = comparator;
    this.root = root;
  }

  public MaxiphobicHeap(Comparator<T> comparator){
    this(comparator, null);
  }

  public static <T> MaxiphobicHeap<T> empty(Comparator<T> comparator) {
    return new MaxiphobicHeap<>(comparator);
  }

  public static <T extends Comparable<? super T>> MaxiphobicHeap<T> empty() {
    Comparator<T> comparator = Comparator.naturalOrder();
    return empty(comparator);
  }

  @SafeVarargs
  public static <T> MaxiphobicHeap<T> of(Comparator<T> comparator, T... elements) {
    ArrayList<MaxiphobicHeap.Node<T>> nodes = ArrayList.withCapacity(elements.length);
    for (T element : elements) {
      nodes.append(new MaxiphobicHeap.Node<>(element));
    }
    return merge(comparator, nodes);
  }

  @SafeVarargs
  public static <T extends Comparable<? super T>> MaxiphobicHeap<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  public static <T> MaxiphobicHeap<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    ArrayList<MaxiphobicHeap.Node<T>> nodes = ArrayList.empty();
    for (T element : iterable) {
      nodes.append(new MaxiphobicHeap.Node<>(element));
    }
    return merge(comparator, nodes);
  }

  public static <T extends Comparable<? super T>> MaxiphobicHeap<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  private static <T> MaxiphobicHeap.Node<T> copyOf(MaxiphobicHeap.Node<T> node) {
    if (node == null) {
      return null;
    } else {
      return new Node<>(node.element, node.weight, copyOf(node.left), copyOf(node.right));
    }
  }

  public static <T> MaxiphobicHeap<T> copyOf(MaxiphobicHeap<T> that) {
    MaxiphobicHeap<T> res = empty(that.comparator());
    res.root = copyOf(that.root);
    return res;
  }

  private static <T> int weight(Node<T> node) {
    return node == null ? 0 : node.weight;
  }

  private static <T> MaxiphobicHeap<T> merge(Comparator<T> comparator, ArrayList<Node<T>> nodes){
    MaxiphobicHeap<T> res = empty(comparator);
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

  private Node<T> merge(Node<T> node1, Node<T> node2){
    Node<T> res;
    if (node1 == null){
      res = node2;
    }else if (node2 == null){
      res = node1;
    }else{
      Node<T> notRoot, max, aux;
      res = (comparator.compare(node1.element, node2.element) < 0)? node1 : node2;
      notRoot = (res == node1)? node2 : node1;
      max = maxSubHeap(res.left, res.right, notRoot);
      if (max == res.left){
        res.right = merge(res.right, notRoot);
      }else if(max == res.right){
        aux = res.left;
        res.left = max;
        res.right = merge(aux, notRoot);
      }else{
        aux = res.left;
        res.left = max;
        res.right = merge(aux, res.right);
      }
      res.weight = 1 + weight(res.left) + weight(res.right);
    }
    return res;
  }

  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

  @Override
  public boolean isEmpty() {
    return root==null;
  }

  @Override
  public int size() {
    return weight(root);
  }

  @Override
  public void clear() {
    root = null;
  }

  @Override
  public void insert(T element) {
    root = merge(root, new Node<>(element));
  }

  @Override
  public T minimum() {
    if (isEmpty()){throw new EmptyHeapException();}
    return root.element;
  }

  @Override
  public void deleteMinimum() {
    if (isEmpty()){throw new EmptyHeapException();}
    root = merge(root.left, root.right);
  }

  private Node<T> maxSubHeap(Node<T> subHeap1, Node<T> subHeap2, Node<T> subHeap3){
    Node<T> max = (weight(subHeap1) < weight(subHeap2))? subHeap2 : subHeap1;
    max = (weight(max) < weight(subHeap3))? subHeap3 : max;
    return max;
  }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringBuilder sb = new StringBuilder(className).append("(");
    toString(sb, root);
    sb.append(")");
    return sb.toString();
  }

  private static void toString(StringBuilder sb, MaxiphobicHeap.Node<?> node) {
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
