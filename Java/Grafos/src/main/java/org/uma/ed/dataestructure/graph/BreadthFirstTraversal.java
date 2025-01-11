package org.uma.ed.dataestructure.graph;

import java.util.*;


public class BreadthFirstTraversal<V> {
    private final Traversable<V> traversable; // The data structure to be traversed
    private final V source;                   // The starting point for the traversal

    public BreadthFirstTraversal(Traversable<V> traversable, V source) {
        this.traversable = traversable;
        this.source = source;
    }

    /**
     * Performs a Breadth-First Traversal starting from the given start element.
     * @return A List of elements visited during the traversal.
     */
    public List<V> traverse() {
        List<V> res = new ArrayList<>();
        Queue<V> aux = new LinkedList<>();

        aux.add(source);
        while (!aux.isEmpty()){
            V value = aux.remove();
            if (!res.contains(value)){
                res.add(value);
                for (var s : traversable.successors(value)){
                    if (!res.contains(s)) {
                        aux.add(s);
                    }
                }
            }
        }
        return res;
    }
}
