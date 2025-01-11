package org.uma.ed.dataestructure.graph;
import java.util.*;


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

    public List<Edge<V>> traversePath() {
        List<Edge<V>> res = new ArrayList<>();
        Stack<Edge<V>> aux = new Stack<>();
        Set<V> visited = new HashSet<>();
        aux.add(Edge.of(source, source));

        while (!aux.isEmpty()){
            Edge<V> value = aux.pop();
            if (!res.contains(value)){
                V destination = value.vertex2();
                res.add(value);
                visited.add(destination);
                for (var s : traversable.successors(destination)){
                    if (!visited.contains(s)) {
                        aux.push(Edge.of(destination, s));
                    }
                }
            }
        }

        return res;
    }

    public void reconstruirCamino(V destination){
        List<Edge<V>> dft = traversePath();
        dft.removeFirst();
        V currentDestination = destination;
        List<Edge<V>> res = new LinkedList<>();
        boolean finished = false;

        while (!finished){
            Edge<V> current = findDestinationEdge(dft, currentDestination);
            dft.remove(current);
            if (current == null){
                finished = true;
            }else{
                res.addFirst(current);
                currentDestination = current.vertex1();
                finished = currentDestination.equals(source);
            }
        }

        if (res.isEmpty() || !res.getFirst().vertex1().equals(source)){
            System.out.println("Couldnt find path from source to destination");
        }else {
            System.out.println(res);
        }
    }

    private Edge<V> findDestinationEdge(List<Edge<V>> dft, V destination){
        boolean found = false;
        int i = 0;
        Edge<V> res = null;

        while (!found && i<dft.size()){
            if (dft.get(i).vertex2().equals(destination)){
                found = true;
                res = dft.get(i);
            }
            i++;
        }

        return res;
    }
}
