package org.uma.ed.dataestructure.graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class DepthFirstTraversal<V> {
    private final Traversable<V> traversable; // The data structure to be traversed
    private final V source;

    public DepthFirstTraversal(Traversable<V> traversable, V source) {
        this.traversable = traversable;
        this.source = source;
    }

    /**
     * Performs a Depth-First Traversal starting from the given start element.
     * @return A set of elements visited during the traversal.
     */
    public List<V> traverse() {
        List<V> res = new ArrayList<>();
        Stack<V> aux = new Stack<>();
        aux.add(source);

        while (!aux.isEmpty()){
            V value = aux.pop();
            if (!res.contains(value)){
                res.add(value);
                for (var s : traversable.successors(value)){
                    if (!res.contains(s)) {
                        aux.push(s);
                    }
                }
            }
        }

        return res;
    }

    public List<Edge<V>> traverse() {
        List<Edge<V>> res = new ArrayList<>();
        Stack<Edge<V>> aux = new Stack<>();
        aux.add(source);

        while (!aux.isEmpty()){
            V value = aux.pop();
            if (!res.contains(value)){
                res.add(value);
                for (var s : traversable.successors(value)){
                    if (!res.contains(s)) {
                        aux.push(s);
                    }
                }
            }
        }

        return res;
    }
}
