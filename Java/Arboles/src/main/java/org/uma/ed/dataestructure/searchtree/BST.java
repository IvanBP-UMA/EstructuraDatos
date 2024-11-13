package org.uma.ed.dataestructure.searchtree;


import org.uma.ed.dataestructure.heap.EmptyHeapException;
import org.uma.ed.dataestructure.list.ArrayList;
import org.uma.ed.dataestructure.list.List;

import java.util.*;

/**
 * Search tree implemented using an unbalanced binary search tree. Nodes are sorted according to their keys and keys are
 * sorted using the provided comparator or their natural order if no comparator is provided.
 *
 * @param <K> Type of keys.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BST<K> implements SearchTree<K> {
    private static final class Node<K> {
        K key;
        Node<K> left, right;

        Node(K key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }

  /*
   INVARIANT:
   - Keys in left child are smaller than key in node.
   - Keys in right child are greater than key in node.
   - There are no duplicate keys in tree.
   - size is number of nodes in tree.
  */

    private final Comparator<K> comparator;
    private Node<K> root;
    private int size;

    /**
     * Creates an empty unbalanced binary search tree. Keys are sorted according to provided comparator.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of keys in this search tree.
     */
    public BST(Comparator<K> comparator) {
        this(comparator, null, 0);
    }

    private BST(Comparator<K> comparator, Node<K> root, int size) {
        this.comparator = comparator;
        this.root = root;
        this.size = size;
    }

    /**
     * Creates an empty unbalanced binary search tree. Keys are sorted according to their natural order.
     * <p> Time complexity: O(1)
     */
    public static <K extends Comparable<? super K>> BST<K> empty() {
        return new BST<K>(Comparator.naturalOrder());
    }

    /**
     * Creates an empty unbalanced binary search tree. Keys are sorted according to provided comparator.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of keys in this search tree.
     */
    public static <K> BST<K> empty(Comparator<K> comparator) {
        return new BST<>(comparator);
    }

    /**
     * Returns a new binary search tree with same elements and same structure as argument (preorder).
     * <p> Time complexity: O(n²)
     *
     * @param that binary search tree to be copied.
     *
     * @return a new BST with same elements and structure as {@code that}.
     */
    public static <K> BST<K> copyOf(SearchTree<K> that) {
        if (that instanceof BST<K> bst) {
            // use specialized version for BST trees
            return copyOf(bst);
        }
        BST<K> copy = new BST<>(that.comparator());
        for (K key : that.preOrder()) {
            copy.insert(key);
        }
        return copy;
    }

    /**
     * Returns a new unbalanced binary search tree with same elements and same structure as argument.
     * Puedes usar un método estático auxliar que te permita copiar dado un Node<K>  devolver una copia de él de forma recursiva.
     * <p> Time complexity: O(n)
     *
     * @param that binary search tree to be copied.
     *
     * @return a new BST with same elements and structure as {@code that}.
     */
    public static <K> BST<K> copyOf(BST<K> that) {
        return new BST<>(that.comparator, copyOf(that.root), that.size);
    }

    private static <K> Node<K> copyOf(Node<K> node) {
        if (node == null) {
            return null;
        } else {
            Node<K> copy = new Node<>(node.key);
            copy.left = copyOf(node.left);
            copy.right = copyOf(node.right);
            return copy;
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
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public int height() {
        return height(root);
    }

    private static int height(Node<?> node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    private boolean isLeaf(Node<K> node){
        return node.left == null && node.right == null;
    }


    //Aquí empieza tu trabajo joven padawan.

    /** Prueba a hacerlo iterativo :). Lleva un anterior (parent) y un actual (node), y recorre izquierda.
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void deleteMinimum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        Node<K> parent = null;
        Node<K> current = root;
        while (current != null && !isLeaf(current)){
            parent = current;
            current = current.left;
        }

        if (parent == null){
            root = null;
        }else if (current == null){
            root = root.right;
        }else {
            parent.left = null;
        }
        size--;
    }

    /** Prueba a hacerlo Iterativo.
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void deleteMaximum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }
        Node<K> parent = null;
        Node<K> current = root;
        while (current != null && !isLeaf(current)){
            parent = current;
            current = current.right;
        }

        if (parent == null){
            root = null;
        }
        else if (current == null){
            root = root.left;
        }else {
            parent.right = null;
        }
        size--;
    }



    /** Se recomienda tener un método privado que dada una clave y un Node<T>, lo intente insertar en él, devolviendo el árbol modificado.
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void insert(K key) {
        insert(key, root, null);
    }

    private void insert(K key, Node<K> node, Node<K> parent){
        if (node == null){
            if (parent == null){
                this.root = new Node<>(key);
            }else{
                if (comparator.compare(parent.key, key) < 0){
                    parent.right = new Node<>(key);
                }else{
                    parent.left = new Node<>(key);
                }
            }
            size++;
        }else {
            if (comparator.compare(node.key, key) < 0) {
                insert(key, node.right, node);
            } else if (comparator.compare(node.key, key) > 0) {
                insert(key, node.left, node);
            } else {
                if (comparator.compare(parent.key, key) < 0){
                    parent.right = new Node<>(key);
                }else{
                    parent.left = new Node<>(key);
                }
            }
        }

    }

    /** Si no lo encuentra, devuelve null. Puedes usar un método privado
     *  que dada una clave y un Node<K> busque si está o no.
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public K search(K key) {
        Node<K> current = root;
        boolean found = false;
        while (current != null && !found){
            if (comparator.compare(current.key, key) < 0){
                current = current.right;
            }else if (comparator.compare(current.key, key) > 0){
                current = current.left;
            }else{
                found = true;
            }
        }

        return (found)? current.key : null;
    }

    /** Puedes usar el search :)
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public boolean contains(K key) {
        return search(key) != null;
    }

    /** Si no está, no hace nada. Sale más rentable buscar para borrar, que mirar primero si está y luego buscarlo.
     * Puedes usar un método privado que dada una clave y un Node<K> localize el nodo. Cuando encuentra el nodo a borrar,
     * puedes tener OTRO método privado, que dado el nodo a borrar, te devuelve el árbol con el nodo borrado (busca los tres casos para borrar).
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void delete(K key) {
        if (!isEmpty()){
            int lastMove = 0; //0 if left, 1 if right
            boolean found = false;
            Node<K> parentNode = null;
            Node<K> currentNode = root;
            while (currentNode != null && !found){
                if (comparator.compare(key, currentNode.key) == 0){
                    found = true;
                }else if (comparator.compare(key, currentNode.key) < 0){
                    parentNode = currentNode;
                    currentNode = currentNode.left;
                    lastMove = 0;
                }else{
                    parentNode = currentNode;
                    currentNode = currentNode.right;
                    lastMove = 1;
                }
            }

            if (found){
                delete(currentNode, parentNode, lastMove);
            }
        }
    }

    private void delete(Node<K> node, Node<K> parent, int childNum){
        if (parent == null){
            deleteBSTRoot(root, null, childNum);
        }else if (isLeaf(node)){
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
            deleteBSTRoot(node, parent, childNum);
        }
        size--;
    }

    private void deleteBSTRoot(Node<K> root, Node<K> parent, int childNum){
        //El metodo delete nos pide eliminar la raiz de un arbol degenerado con raiz minima
        if (parent == null && comparator.compare(root.key, minimum()) == 0){
            this.root = this.root.right;
        }else {//Casos normales
            BST<K> aux = empty(comparator());
            if (parent == null) {
                aux.root = this.root.right;
            } else {
                aux.root = root.right;
            }
            root.key = aux.minimum();
            aux.deleteMinimum();
        }
    }

    private Node<K> findParent(K inSearch){
        Node<K> n = root;
        Node<K> parent = null;
        boolean found = false;
        while (!found && n != null){
            if (comparator.compare(n.key, inSearch) == 0){
                found = true;
            }else if (comparator.compare(n.key, inSearch) < 0){
                parent = n;
                n = n.right;
            }else{
                parent = n;
                n = n.left;
            }
        }
        return parent;
    }

    /** Si está vacía lanza la excepción EmptySearchTreeException.
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public K minimum(){
        if (root == null){
            throw new EmptySearchTreeException();
        }

        Node<K> current = root;
        while (current.left != null){
            current = current.left;
        }
        return current.key;
    }

    /**
     * Si no, busca de forma iterativa y revuelve la clave más grande.
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public K maximum() {
        if (root == null){
            throw new EmptySearchTreeException();
        }

        Node<K> current = root;
        while (current.right != null){
            current = current.right;
        }
        return current.key;
    }


    /**
     * Un iterador sobre las claves del árbol
     * Prueba a crear un metodo privado recursivo para poder recorrer el árbol y lo agregas a una lista.
     * Luego solo tienes que usar el iterator() de esa lista para poder crear tu propio iterator.
     * Iterable es una interfaz funcional (sólo tiene un método).
     * Puedes crear una clase anónima para crear el objeto iterable:
      return new Iterable<K>() {
                @Override
                public Iterator<K> iterator() {
                  ...
                 }
     * O puedes usar una lambda, si usas una lista ll para almacenar los nodos inOrder, sería:
      return () -> elements.iterator();
     * De esta forma cada vez que se llame al único método que tiene el objeto Iterable<k> (iterator) se está llamando a ll.iterator();

     * {@inheritDoc}
     */
    @Override
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
        StringJoiner sj = new StringJoiner(", ",className+" {","}");
        Iterator<K> iter = inOrder().iterator();
        while(iter.hasNext()){
            sj.add(iter.next().toString());
        }
        return sj.toString();
    }
}