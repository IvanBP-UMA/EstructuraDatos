
package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.priorityqueue.EmptyPriorityQueueException;
import org.uma.ed.datastructures.priorityqueue.JDKPriorityQueue;
import org.uma.ed.datastructures.priorityqueue.PriorityQueue;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;
import org.uma.ed.datastructures.tuple.Tuple2;
import org.uma.ed.datastructures.utils.data.Location;

import java.util.Comparator;
import java.util.Dictionary;

/**
 * Class for computing shortest paths in a weighted graph using A* algorithm.
 *
 * @author Joaquín Ballesteros, Data Structures, Grado en Informática. UMA.
 */
public class Astar {


  /**
   * Computes costs of shortest paths from a source vertex to a goal vertex in a weighted graph.
   *
   * @param weightedGraph The weighted graph.
   * @param source The source vertex.
   * @param <V> The type of the vertices in the graph.
   *
   * @return a dictionary where keys are vertices and values are the minimum cost to reach them from the source.
   */
  public static <V extends Location> Tuple2<Double, List<V>> findPath(
      WeightedGraph<V, Double> weightedGraph,  V source, V dest) {

    record Extension<V>(V source, V destination, Double totalCost, Double realCost, List<V> path) implements Comparable<Extension<V>> {
      @Override
      // Best extension is the one with the smallest total cost.
      // Will be used later by the priority queue.
      public int compareTo(Extension that) {
        return this.totalCost.compareTo(that.totalCost);
      }

      static <V> Extension<V> of(V source, V destination, Double totalCost, Double realCost, List<V> path) {
        return new Extension<>(source, destination, totalCost,realCost, path);
      }
    }

    JDKPriorityQueue<Extension<V>> heap = JDKPriorityQueue.empty();
    JDKArrayList<V> aux = new JDKArrayList<>();
    aux.append(source);
    heap.enqueue(new Extension<>(source, source, source.distanceTo(dest), 0.0, aux));
    Double menorDistancia = Double.MAX_VALUE;
    Tuple2<Double, List<V>> mejor = null;


    while(!heap.isEmpty()){
      Extension<V> current = heap.first();
      heap.dequeue();
      if (current.destination.equals(dest)){
        if (current.realCost < menorDistancia){
          menorDistancia = current.realCost;
          mejor = new Tuple2<>(current.realCost, JDKArrayList.from(current.path));
        }
      }else{
        List<V> currentPath;
        for (var child : weightedGraph.successors(current.destination)){
          currentPath = JDKArrayList.from(current.path);
          if (!currentPath.contains(child.vertex())){
            Double cota = current.realCost + child.weight() + current.destination.distanceTo(dest);
            currentPath.append(child.vertex());
            if (cota < menorDistancia) {
              heap.enqueue(new Extension<>(current.destination, child.vertex(), cota
                      , current.realCost + child.weight(), currentPath));
            }
          }
        }
      }
    }
    return mejor;
  }
}
