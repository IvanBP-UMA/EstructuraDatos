package org.uma.ed.dataestructure.graph;

import org.uma.ed.dataestructure.hashtable.HashTable;

import java.util.*;

/**
 * Class for undirected graphs implemented with a dictionary
 *
 * @param <V> Type for vertices in graph
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryGraph<V> implements Graph<V> {
    private final Set<V> vertices;                // set with all vertices in graph
    private final Map<V, Set<V>> diEdges;  // dictionary with sources as keys and Set of destinations as values

    /**
     * Creates an empty graph.
     */
    public DictionaryGraph() {
        vertices = new HashSet<>();
        diEdges = new HashMap<>();
    }

    /**
     * Creates an empty graph.
     *
     * @param <V> Type for vertices in graph.
     *
     * @return An empty DictionaryGraph.
     */
    public static <V> DictionaryGraph<V> empty() {
        return new DictionaryGraph<>();
    }

    /**
     * Creates a graph with given vertices and edges.
     *
     * @param vertices vertices to add to graph.
     * @param edges edges to add to graph.
     * @param <V> Type for vertices in graph.
     *
     * @return A DictionaryGraph with given vertices and edges.
     */
    public static <V> DictionaryGraph<V> of(Set<V> vertices, Set<Edge<V>> edges) {
        DictionaryGraph<V> res = empty();
        for (var v : vertices){
            res.addVertex(v);
        }
        for (var e : edges){
            res.addEdge(e.vertex1(), e.vertex2());
        }
        return res;
    }

    /**
     * Creates a graph with same vertices and edges as given graph.
     *
     * @param graph Graph to copy.
     * @param <V> Type for vertices in graph.
     *
     * @return A DictionaryGraph with same vertices and edges as given graph.
     */
    public static <V> DictionaryGraph<V> copyOf(Graph<V> graph) {
        return of(graph.vertices(), graph.edges());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVertex(V vertex) {
        vertices.add(vertex);
    }

    private void addDiEdge(V source, V destination) {
        if (!vertices.contains(source)) {
            throw new GraphException("vertex " + source + " is not in graph");
        }
        if (!vertices.contains(destination)) {
            throw new GraphException("vertex " + destination + " is not in graph");
        }

        Set<V> destinations = diEdges.getOrDefault(source,null);
        if (destinations == null) {
            destinations = new HashSet<>();
            diEdges.put(source, destinations);
        }
        destinations.add(destination);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(V vertex1, V vertex2) {
        addDiEdge(vertex1, vertex2);
        addDiEdge(vertex2, vertex1);
    }

    private void deleteDiEdge(V source, V destination) {
        Set<V> sucesores = successors(source);
        if (!sucesores.isEmpty()){
            sucesores.remove(destination);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEdge(V vertex1, V vertex2) {
        deleteDiEdge(vertex1, vertex2);
        deleteDiEdge(vertex2, vertex1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteVertex(V vertex) {
        vertices.remove(vertex);
        diEdges.remove(vertex);
        for (var v : vertices){
            successors(v).remove(vertex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> vertices() {
        return vertices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Edge<V>> edges() {
        Set<Edge<V>> res = new HashSet<>();
        for (var v : vertices){
            for (var s : successors(v)){
                res.add(Edge.of(v, s));
            }
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfVertices() {
        return vertices.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfEdges() {
        return edges().size();
    }

    /**
     * Returns the successors of a vertex in graph (i.e. vertices to which there is an edge from given vertex).
     *
     * @param vertex vertex for which we want to obtain its successors.
     *
     * @return Successors of a vertex.
     */
    @Override
    public Set<V> successors(V vertex) {
        return diEdges.getOrDefault(vertex,new HashSet<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int degree(V vertex) {
        return successors(vertex).size();
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();

        StringJoiner verticesSJ = new StringJoiner(", ", "vertices(", ")");
        for (V vertex : vertices()) {
            verticesSJ.add(vertex.toString());
        }

        StringJoiner edgesSJ = new StringJoiner(", ", "edges(", ")");
        for (Edge<V> edge : edges()) {
            edgesSJ.add(edge.toString());
        }

        StringJoiner sj = new StringJoiner(", ", className + "(", ")");
        sj.add(verticesSJ.toString());
        sj.add(edgesSJ.toString());
        return sj.toString();
    }
}