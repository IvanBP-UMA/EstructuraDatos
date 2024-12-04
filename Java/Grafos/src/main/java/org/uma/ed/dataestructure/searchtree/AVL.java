package org.uma.ed.dataestructure.searchtree;

import org.uma.ed.dataestructure.list.ArrayList;
import org.uma.ed.dataestructure.list.List;

import java.util.Comparator;


/**
 * Search tree implemented using a balanced AVL tree. Nodes are sorted according to their keys and keys are sorted using
 * the provided comparator or their natural order if no comparator is provided.
 *
 * @param <K> Type of keys.
 *
 * @author Pepe Gallardo (modified by Joaquín Ballesteros). Data Structures, Grado en Informática. UMA.
 * 
 */
public class AVL<K> implements SearchTree<K> {

    //This class implements the Node and also the rotations.
    private static final class Node<K> {
        K key;
        int height;
        Node<K> left, right;

        Node(K key) {
            this.key = key;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        // Returns height of node
        static int height(Node<?> node) {
            return node == null ? 0 : node.height;
        }

        // Sets height of node
        void setHeight() {
            height = 1 + Math.max(height(left), height(right));
        }

        // Returns balance factor of node. Negative if node is right leaning, positive if node is left leaning
        static int balance(Node<?> node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }


        Node<K> rightRotated() { // Rotates receiving node to the right. Returns new root of rotated tree. Use set height!
            Node<K> aux = left;
            this.left = aux.right;
            this.setHeight();

            aux.right = this;
            aux.setHeight();

            return aux;
        }

        Node<K> leftRotated() { // Rotates receiving node to the left. Returns new root of rotated tree. Use set height!
            Node<K> aux = right;
            this.right = aux.left;
            this.setHeight();

            aux.left = this;
            aux.setHeight();

            return aux;
        }

        // Balances receiving node and sets new height. Returns node already balanced
        Node<K> balanced() {
            int balance = balance(this);
            Node<K> balanced;

            if (balance > 1) { // left leaning
                if (balance(left) < 0) { // left child is right leaning
                    left = left.leftRotated();
                }
                balanced = this.rightRotated();
            } else if (balance < -1) { // right leaning
                if (balance(right) > 0) { // right child is left leaning
                    right = right.rightRotated();
                }
                balanced = this.leftRotated();
            } else {
                balanced = this; // no rotation needed
                balanced.setHeight();
            }
            return balanced;
        }
    }

    private final Comparator<K> comparator;
    private Node<K> root;
    private int size;

    private AVL(Comparator<K> comparator, Node<K> root, int size) {
        this.comparator = comparator;
        this.root = root;
        this.size = size;
    }

    /**
     * Creates an empty balanced binary search tree. Keys are sorted according to provided comparator.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of keys in this search tree.
     */
    public AVL(Comparator<K> comparator) {
        this(comparator, null, 0);
    }

    /**
     * Creates an empty balanced binary search tree. Keys are sorted according to their natural order.
     * <p> Time complexity: O(1)
     */
    public static <K extends Comparable<? super K>> AVL<K> empty() {
        return new AVL<K>(Comparator.naturalOrder());
    }

    /**
     * Creates an empty balanced binary search tree. Keys are sorted according to provided comparator.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of keys in this search tree.
     */
    public static <K> AVL<K> empty(Comparator<K> comparator) {
        return new AVL<>(comparator);
    }

    /**
     * Returns a new balanced binary search tree with same elements and same structure as argument.
     * <p> Time complexity: O(n x log n)
     *
     * @param that binary search tree to be copied.
     *
     * @return a new AVL with same elements and structure as {@code that}.
     */
    public static <K> AVL<K> copyOf(SearchTree<K> that) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns a new balanced binary search tree with same elements and same structure as argument.
     * <p> Time complexity: O(n)
     *
     * @param that AVL binary search tree to be copied.
     *
     * @return a new AVL with same elements and structure as {@code that}.
     */
    public static <K> AVL<K> copyOf(AVL<K> that) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    //Recurvive method to return a copy of the node with the same height, left and right side.
    private static <K> Node<K> copyOf(Node<K> node) {
        if (node == null){
            return null;
        }else{
            Node<K> res = new Node<>(node.key);
            res.right = copyOf(res.right);
            res.left = copyOf(res.left);
            return res;
        }
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public Comparator<K> comparator() {
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
        return size;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(log n)
     */
    @Override
    public int height() {
        return Node.height(root);
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(log n)
     */
    @Override
    public void insert(K key) {
        root = insert(root, key);
    }

    private Node<K> insert(Node<K> node, K key){
        if (node == null){
            node = new Node<>(key);
            size++;
        }else{
            int cmp = comparator.compare(node.key, key);
            if (cmp < 0){
                node.right = insert(node.right, key);
                node = node.balanced();
            }else if (cmp > 0){
                node.left = insert(node.left, key);
                node = node.balanced();
            }else{
                node.key = key;
            }
        }
        return node;
    }


    // Sugerencia si lo quieres hacer recursivo.  private Node<K> insert(Node<K> node, K key)

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(log n)
     */
    @Override
    public K search(K key) {
        return search(root, key);
    }

    private K search(Node<K> node, K key){
        K res = null;
        if (node != null){
            int cmp = comparator.compare(node.key, key);
            if (cmp < 0){
                res = search(node.right, key);
            }else if (cmp > 0){
                res = search(node.left, key);
            }else{
                res = node.key;
            }
        }
        return res;
    }

    // Sugerencia si lo quieres hacer recursivo.  private K search(Node<K> node, K key)

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(log n)
     */
    @Override
    public boolean contains(K key) {
        return search(key) != null;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(log n)
     */
    @Override
    public void delete(K key) {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        if (comparator.compare(root.key, key) == 0){
            deleteRoot(root, null);
            root = root.balanced();
        }else {
            root = delete(root, key);
        }
    }

    private Node<K> delete(Node<K> node, K key){
        Node<K> res = null;
        if (node != null){
            int cmp = comparator.compare(node.key, key);
            Node<K> aux;
            if (cmp < 0){
                aux = delete(node.right, key);
                if (aux != null && comparator.compare(aux.key, key) == 0){
                    deleteNode(node.right, node, 1);
                    res = node.balanced();
                }else if (aux != null){
                    node.right = aux;
                    res = node.balanced();
                }else{
                    res = node;
                }
            }else if (cmp > 0){
                aux = delete(node.left, key);
                if (aux != null && comparator.compare(aux.key, key) == 0){
                    deleteNode(node.left, node, 0);
                    res = node.balanced();
                }else if (aux != null){
                    node.left = aux;
                    res = node;
                }else{
                    res = node;
                }
            }else{
                res = node;
            }
        }
        return res;
    }

    //Delete a node given itself, its parent and childNumber being 0 if left child or 1 if right child
    private void deleteNode(Node<K> node, Node<K> parent, int childNum){
        if (parent == null){
            deleteRoot(root, null);
        }else if (node.left == null && node.right == null){
            if (childNum == 0){
                parent.left = null;
            }else{
                parent.right = null;
            }
        }else if (node.left == null){
            if (childNum == 0){
                parent.left = node.right;
            }else{
                parent.right = node.right;
            }
        }else if (node.right == null){
            if (childNum == 0){
                parent.left = node.left;
            }else{
                parent.right = node.left;
            }
        }else{
            deleteRoot(node, parent);
        }
        size--;
    }

    //Delete root of the tree or of one of its subtrees
    private void deleteRoot(Node<K> root, Node<K> parent){
        AVL<K> aux = empty(comparator());
        if (parent == null) {
            aux.root = this.root.right;
        } else {
            aux.root = root.right;
        }
        root.key = aux.minimum();
        aux.deleteMinimum();
        root.right = aux.root;
    }

    // Sugerencia si lo quieres hacer recursivo. uno para recorrer y otro para eliminar.   private Node<K> delete(Node<K> node, K key)     private Node<K> delete(Node<K> node)

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n)
     */
    @Override
    public K minimum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        Node<K> aux = root;
        while (aux.left != null){
            aux = aux.left;
        }
        return aux.key;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n)
     */
    @Override
    public K maximum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        Node<K> aux = root;
        while (aux.right != null){
            aux = aux.right;
        }
        return aux.key;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n)
     */
    @Override
    public void deleteMinimum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        root = deleteMinimum(root);

        size--;
    }

    private static <K> Node<K> deleteMinimum(Node<K> node){
        if (node.left == null){
            if (node.height > 1){
                node = node.right;
            }else {
                node = null;
            }
        }else {
            Node<K> aux = deleteMinimum(node.left);
            if (aux == null){
                node.left = node.left.right;
                node = node.balanced();
            }else {
                node.left = aux;
            }
        }
        return node;
    }

    // Sugerencia si lo quieres hacer recursivo  private static <K> Node<K> deleteMinimum(Node<K> node) {

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n)
     */
    @Override
    public void deleteMaximum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        root = deleteMaximum(root);
        size--;
    }

    private static <K> Node<K> deleteMaximum(Node<K> node){
        if (node.right == null){
            if (node.height > 1){
                node = node.left;
            }else{
                node = null;
            }
        }else {
            Node<K> aux = deleteMaximum(node.right);
            if (aux == null){
                node.right = node.right.left;
                node = node.balanced();
            }else {
                node.right = aux;
            }
        }
        return node;
    }

    // Sugerencia si lo quieres hacer recursivo private static <K> Node<K> deleteMaximum(Node<K> node) {


    public Iterable<K> inOrder() {
        List<K> res = ArrayList.withCapacity(size);
        if (root != null){
            inOrder(res, root);
        }
        return res;
    }

    private void inOrder(List<K> nodes, Node<K> node){
        if (node != null){
            inOrder(nodes, node.left);
            nodes.append(node.key);
            inOrder(nodes, node.right);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<K> preOrder() {
        List<K> res = ArrayList.withCapacity(size);
        if (root != null){
            preOrder(res, root);
        }
        return res;
    }

    private void preOrder(List<K> nodes, Node<K> node){
        if (node != null){
            nodes.append(node.key);
            preOrder(nodes, node.left);
            preOrder(nodes, node.right);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<K> postOrder() {
        List<K> res = ArrayList.withCapacity(size);
        if (root != null){
            postOrder(res, root);
        }
        return res;
    }

    private void postOrder(List<K> nodes, Node<K> node){
        if (node != null){
            postOrder(nodes, node.left);
            postOrder(nodes, node.right);
            nodes.append(node.key);
        }
    }

    /**
     * Returns representation of this search tree as a String.
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
            sb.append(node.key);
            sb.append(", ");
            toString(sb, node.right);
            sb.append(")");
        }
    }
}