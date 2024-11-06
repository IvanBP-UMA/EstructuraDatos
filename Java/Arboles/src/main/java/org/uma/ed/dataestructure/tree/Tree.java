package org.uma.ed.dataestructure.tree;

import org.uma.ed.dataestructure.list.ArrayList;
import org.uma.ed.dataestructure.list.List;
import org.uma.ed.dataestructure.queue.ArrayQueue;
import org.uma.ed.dataestructure.queue.LinkedQueue;
import org.uma.ed.dataestructure.queue.Queue;

import java.util.Comparator;
import java.util.NoSuchElementException;


/**
 * This class defines different methods to process general trees. A tree is represented by a root node. If the tree is
 * empty, this root node is null. Otherwise, the root node contains an element and a list of children nodes.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class Tree {
    /**
     * This class represents a node in a general tree. Each node contains an element and a list of children nodes.
     *
     * @param <E>
     */
    public static final class Node<E> {
        private final E element;
        private final List<Node<E>> children;

        /**
         * Creates a node with an element and no children.
         *
         * @param element Element in node.
         */
        public Node(E element) {
            this.element = element;
            this.children = ArrayList.empty();
        }

        /**
         * Creates a node with an element and a list of children.
         *
         * @param element The element in the node.
         * @param children The list of children nodes.
         * @param <T> The type of the element in the node.
         *
         * @return A new node with given element and children.
         */
        @SafeVarargs
        public static <T> Node<T> of(T element, Node<T>... children) {
            Node<T> node = new Node<>(element);
            for (Node<T> child : children) {
                node.children.append(child);
            }
            return node;
        }
    }

    /**
     * Returns the number of nodes in a tree.
     *
     * @param root The root node of the tree.
     *
     * @return The number of nodes in the tree.
     */
    public static int size(Node<?> root) {
        int size = 0;
        if (root != null){
            size++;
            if (!root.children.isEmpty()) {
                for (var child : root.children) {
                    size += size(child);
                }
            }
        }
        return size;
    }

    /**
     * Returns the height of a tree.
     *
     * @param root The root node of the tree.
     *
     * @return The height of the tree.
     */
    public static int height(Node<?> root) {
        int max = 0;
        if (root != null){
            int currentHeight;
            max++;
            if (!root.children.isEmpty()){
                for (var child : root.children){
                    currentHeight = height(child) + 1;
                    if (currentHeight > max){
                        max = currentHeight;
                    }
                }
            }
        }
        return max;
    }

    /**
     * Returns the sum of elements in a tree of integers.
     *
     * @param root The root node of the tree.
     *
     * @return The sum of elements in the tree.
     */
    public static int sum(Node<Integer> root) {
        int suma = 0;
        if (root != null){
            suma += root.element;
            if (!root.children.isEmpty()){
                for (var child : root.children){
                   suma += sum(child);
                }
            }
        }
        return suma;
    }

    /**
     * Returns the maximum element in a tree.
     *
     * @param root The root node of the tree.
     * @param comparator A comparator to compare elements in the tree.
     * @param <T> The type of elements in the tree.
     *
     * @return The maximum element in the tree according to the comparator.
     */
    public static <T> T maximum(Node<T> root, Comparator<T> comparator) {
        T max = null;
        if (root != null){
            T current;
            max = root.element;
            if (!root.children.isEmpty()){
                for (var child : root.children){
                    current = maximum(child, comparator);
                    if (comparator.compare(current, max) > 0){
                        max = current;
                    }
                }
            }
        }
        return max;
    }

    /**
     * Returns the number of occurrences of an element in a tree.
     *
     * @param root The root node of the tree.
     * @param element The element to count.
     * @param <T> The type of elements in the tree.
     *
     * @return The number of occurrences of the element in the tree.
     */
    public static <T> int count(Node<T> root, T element) {
        int oc = 0;
        if (root != null){
            if (!root.children.isEmpty()){
                for (var child : root.children){
                    oc += count(child, element);
                }
            }
            oc = root.element.equals(element)? oc+1 : oc;
        }
        return oc;
    }

    /**
     * Returns the leaves of a tree.
     *
     * @param root The root node of the tree.
     * @param <T> The type of elements in the tree.
     *
     * @return A list with the leaves of the tree.
     */
    public static <T> List<T> leaves(Node<T> root) {
        List<T> leaves = new ArrayList<>();
        if (root != null){
            leaves(root, leaves);
        }
        return leaves;
    }

    private static <T> void leaves(Node<T> root, List<T> hojas){
        if (root != null) {
            if (root.children.isEmpty()) {
                hojas.append(root.element);
            } else {
                for (var child : root.children) {
                    leaves(child, hojas);
                }
            }
        }
    }

    /**
     * Returns the preorder traversal of a tree.
     *
     * @param root The root node of the tree.
     * @param <T> The type of elements in the tree.
     *
     * @return A list with the preorder traversal of the tree.
     */
    public static <T> List<T> preorder(Node<T> root) {
        List<T> res = new ArrayList<>();
        if (root != null){
            preorder(root, res);
        }
        return res;
    }

    private static <T> void preorder(Node<T> root, List<T> valores){
        if (root != null){
            valores.append(root.element);
            for (var child : root.children){
                preorder(child, valores);
            }
        }
    }


    /**
     * Returns the postorder traversal of a tree.
     *
     * @param root The root node of the tree.
     * @param <T> The type of elements in the tree.
     *
     * @return A list with the postorder traversal of the tree.
     */
    public static <T> List<T> postorder(Node<T> root) {
        List<T> res = new ArrayList<>();
        if (root != null){
            postorder(root, res);
        }
        return res;
    }

    private static <T> void postorder(Node<T> root, List<T> valores){
        if (root != null){
            for (var child : root.children){
                postorder(child, valores);
            }
            valores.append(root.element);
        }
    }


    /**
     * Returns the breadth-first traversal of a tree.
     *
     * @param root The root node of the tree.
     * @param <T> The type of elements in the tree.
     *
     * @return A list with the breadth-first traversal of the tree.
     */
    public static <T> List<T> breadthFirst(Node<T> root) {
        List<T> res = new ArrayList<>();
        if (root != null){
            Queue<Node<T>> aux = new ArrayQueue<>();
            aux.enqueue(root);
            while (!aux.isEmpty()){
                Node<T> current = aux.first();
                res.append(current.element);
                for (var child : current.children){
                    aux.enqueue(child);
                }
                aux.dequeue();
            }
        }
        return res;
    }

}