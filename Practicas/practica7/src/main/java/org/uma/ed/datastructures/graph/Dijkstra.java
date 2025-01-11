package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.*;
import org.uma.ed.datastructures.graph.*;
import org.uma.ed.datastructures.list.*;
import org.uma.ed.datastructures.priorityqueue.*;
import org.uma.ed.datastructures.set.*;
import org.uma.ed.datastructures.tuple.Tuple2;


public class Dijkstra {
  public static <V> Dictionary<V, Integer> dijkstra(
          WeightedGraph<V, Integer> weightedGraph,
          V source){
    record Extension <V>(V source, V destination, Integer totalCost) implements Comparable<Extension<V>>{
      @Override
      public int compareTo(Extension<V> o) {
        return Integer.compare(this.totalCost, o.totalCost);
      }
      static <V> Extension<V> of(V source, V destination, Integer totalCost){
        return new Extension<>(source, destination, totalCost);
      }
    }

    Dictionary<V, Integer> costOpt = JDKHashDictionary.empty();
    costOpt.insert(source, 0);
    Set<V> vertices = weightedGraph.vertices();
    Set<V> verticesOpt = new JDKHashSet<>();
    verticesOpt.insert(source);
    vertices.delete(source);
    JDKPriorityQueue<Extension<V>> priorityQueue = JDKPriorityQueue.empty();

    while (!vertices.isEmpty()){
      for (var v : verticesOpt){
        for (var child : weightedGraph.successors(v)){
          if (vertices.contains(child.vertex())){
            priorityQueue.enqueue(Extension.of(v, child.vertex(), costOpt.valueOf(v) + child.weight()));
          }
        }
      }

      Extension<V> next = priorityQueue.first();
      verticesOpt.insert(next.destination);
      vertices.delete(next.destination);
      costOpt.insert(next.destination, next.totalCost);
      priorityQueue.clear();
    }
    return costOpt;
  }

  public static <V> Dictionary<V, Tuple2<Integer, List<V>>> dijkstraPaths(
          WeightedGraph<V, Integer> weightedGraph,
          V source){
    record Extension<V>(V source, V destination, Integer totalCost, List<V>
            path) implements Comparable<Extension<V>> {
      @Override
      public int compareTo(Extension<V> o) {
        return Integer.compare(this.totalCost, o.totalCost);
      }
      static <V> Extension<V> of(V source, V destination, Integer totalCost,
              List<V> path) {
        return new Extension<>(source, destination, totalCost, path);
      }
    }

    Dictionary<V, Tuple2<Integer, List<V>>> costOpt = JDKHashDictionary.empty();
    costOpt.insert(source, Tuple2.of(0, JDKArrayList.of(source)));
    Set<V> vertices = weightedGraph.vertices();
    Set<V> verticesOpt = new JDKHashSet<>();
    verticesOpt.insert(source);
    vertices.delete(source);
    JDKPriorityQueue<Extension<V>> priorityQueue = JDKPriorityQueue.empty();

    while (!vertices.isEmpty()){
      for (var v : verticesOpt){
        for (var child : weightedGraph.successors(v)){
          if (vertices.contains(child.vertex())){
            List<V> path = JDKArrayList.from(costOpt.valueOf(v)._2());
            path.append(child.vertex());
            priorityQueue.enqueue(Extension.of(v, child.vertex(),
                    costOpt.valueOf(v)._1() + child.weight(),
                    path));
          }
        }
      }

      Extension<V> next = priorityQueue.first();
      verticesOpt.insert(next.destination);
      vertices.delete(next.destination);
      costOpt.insert(next.destination, Tuple2.of(next.totalCost, next.path));
      priorityQueue.clear();
    }
    return costOpt;
}
}
