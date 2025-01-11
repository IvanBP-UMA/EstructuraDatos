package org.uma.ed.demos.graph;

import static org.uma.ed.datastructures.graph.Dijkstra.dijkstra;
import static org.uma.ed.datastructures.graph.Dijkstra.dijkstraPaths;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.graph.DictionaryWeightedGraph;
import org.uma.ed.datastructures.graph.WeightedGraph;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.tuple.Tuple2;

/**
 * Simple class to test Dijkstra's shortest path algorithm.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DijkstraDemo {
  public static WeightedGraph<Character, Integer> exampleGraph() {
    WeightedGraph<Character, Integer> weightedGraph = DictionaryWeightedGraph.empty();
    weightedGraph.addVertex('a');
    weightedGraph.addVertex('b');
    weightedGraph.addVertex('c');
    weightedGraph.addVertex('d');
    weightedGraph.addVertex('e');

    weightedGraph.addEdge('a', 'b', 3);
    weightedGraph.addEdge('a', 'd', 7);
    weightedGraph.addEdge('b', 'd', 2);
    weightedGraph.addEdge('b', 'c', 4);
    weightedGraph.addEdge('c', 'e', 6);
    weightedGraph.addEdge('d', 'c', 5);
    weightedGraph.addEdge('d', 'e', 4);

    return weightedGraph;
  }

  public static void main(String[] args) {
    WeightedGraph<Character, Integer> weightedGraph = exampleGraph();
    System.out.println("Graph is: " + weightedGraph);
    System.out.println();

    Character source = 'a';
    Dictionary<Character, Integer> dictionary1 = dijkstra(weightedGraph, source);
    System.out.printf("Costs of shortest paths from vertex %s are:\n(Current)  %s\n", source, dictionary1);
    System.out.println("(Expected) JDKHashDictionary(Entry(a, 0), Entry(e, 9), Entry(d, 5), Entry(c, 7), Entry(b, 3))");
    System.out.println();

    Dictionary<Character, Tuple2<Integer, List<Character>>> dictionary2 = dijkstraPaths(weightedGraph, source);
    System.out.printf("Shortest paths from vertex %s are:\n(Current)  %s\n", source, dictionary2);
    System.out.println("(Expected) JDKHashDictionary(Entry(b, Tuple2(3, JDKArrayList(a, b))), Entry(a, Tuple2(0, JDKArrayList(a))), Entry(e, Tuple2(9, JDKArrayList(a, b, d, e))), Entry(d, Tuple2(5, JDKArrayList(a, b, d))), Entry(c, Tuple2(7, JDKArrayList(a, b, c))))\n");
  }
}
