package org.uma.ed.demos.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.graph.Astar;
import org.uma.ed.datastructures.graph.DictionaryWeightedGraph;
import org.uma.ed.datastructures.graph.WeightedGraph;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.tuple.Tuple2;
import org.uma.ed.datastructures.utils.data.Location;


import static org.uma.ed.datastructures.graph.Dijkstra.dijkstraPaths;

/**
 * Simple class to test A* shortest path algorithm.
 *
 * @author Joaquín Ballesteros, Data Structures, Grado en Informática. UMA.
 */
public class AStarDemo {
  public static WeightedGraph<Location, Double> exampleGraph() {
    WeightedGraph<Location, Double> weightedGraph = DictionaryWeightedGraph.empty();

    Location almeria = new Location(36.834, -2.463);
    Location cadiz = new Location(36.527, -6.292);
    Location cordoba = new Location(37.888, -4.779);
    Location granada = new Location(37.177, -3.598);
    Location huelva = new Location(37.261, -6.949);
    Location jaen = new Location(37.779, -3.783);
    Location malaga = new Location(36.720, -4.420);
    Location sevilla = new Location(37.388, -5.982);

// Añadir los vértices al grafo
    weightedGraph.addVertex(almeria);
    weightedGraph.addVertex(cadiz);
    weightedGraph.addVertex(cordoba);
    weightedGraph.addVertex(granada);
    weightedGraph.addVertex(huelva);
    weightedGraph.addVertex(jaen);
    weightedGraph.addVertex(malaga);
    weightedGraph.addVertex(sevilla);



// Conexiones de Huelva
    weightedGraph.addEdge(huelva, sevilla, 95.0); // Huelva - Sevilla
    weightedGraph.addEdge(huelva, cadiz, 115.0); // Huelva - Cádiz

// Conexiones de Cádiz
    weightedGraph.addEdge(cadiz, sevilla, 120.0); // Cádiz - Sevilla
    weightedGraph.addEdge(cadiz, huelva, 115.0); // Cádiz - Huelva
    weightedGraph.addEdge(cadiz, malaga, 185.0); // Cádiz - Málaga

// Conexiones de Sevilla
    weightedGraph.addEdge(sevilla, huelva, 95.0); // Sevilla - Huelva
    weightedGraph.addEdge(sevilla, cadiz, 120.0); // Sevilla - Cádiz
    weightedGraph.addEdge(sevilla, malaga, 205.0); // Sevilla - Málaga
    weightedGraph.addEdge(sevilla, cordoba, 140.0); // Sevilla - Córdoba

// Conexiones de Málaga
    weightedGraph.addEdge(malaga, cordoba, 160.0); // Málaga - Córdoba
    weightedGraph.addEdge(malaga, granada, 125.0); // Málaga - Granada
    weightedGraph.addEdge(malaga, sevilla, 205.0); // Málaga - Sevilla
    weightedGraph.addEdge(malaga, cadiz, 195.0); // Málaga - Cádiz

// Conexiones de Córdoba
    weightedGraph.addEdge(cordoba, sevilla, 140.0); // Córdoba - Sevilla
    weightedGraph.addEdge(cordoba, malaga, 160.0); // Córdoba - Málaga
    weightedGraph.addEdge(cordoba, granada, 200.0); // Córdoba - Granada
    weightedGraph.addEdge(cordoba, jaen, 95.0); // Córdoba - Jaén

// Conexiones de Jaén
    weightedGraph.addEdge(jaen, granada, 90.0); // Jaén - Granada
    weightedGraph.addEdge(jaen, cordoba, 95.0); // Jaén - Córdoba

// Conexiones de Granada
    weightedGraph.addEdge(granada, jaen, 90.0); // Granada - Jaén
    weightedGraph.addEdge(granada, cordoba, 200.0); // Granada - Córdoba
    weightedGraph.addEdge(granada, malaga, 125.0); // Granada - Málaga

// Conexión de Almería
    weightedGraph.addEdge(almeria, granada, 170.0); // Almería - Granada


    return weightedGraph;
  }

  public static void main(String[] args) {
    WeightedGraph<Location, Double> weightedGraph = exampleGraph();
    System.out.println("Graph is: " + weightedGraph);
    System.out.println();

    Location source = new Location(36.720, -4.420); //malaga
    Location goal = new Location(37.388, -5.982); //sevilla
    Tuple2<Double, List<Location>> dictionary1 = Astar.findPath(weightedGraph, source,goal);
    System.out.printf("Costs of shortest paths from vertex %s to vertex %s are %s:\n", source, goal, dictionary1._1());
    for (var point: dictionary1._2()){
      System.out.print(point + " ");
    }
    System.out.println();

    source = new Location(36.834, -2.463);; //almeria
    goal = new Location(37.261, -6.949);; //huelva
    dictionary1 = Astar.findPath(weightedGraph, source,goal);
    System.out.printf("Costs of shortest paths from vertex %s to vertex %s are %s:\n", source, goal, dictionary1._1());
    for (var point: dictionary1._2()){
      System.out.print(point + " ");
    }

    //Vamos a joder, no existe el final
    source = new Location(36.834, -2.463);; //almeria
    goal = new Location(37.261, -6.77749);; //no existe en el grafo
    dictionary1 = Astar.findPath(weightedGraph, source,goal);
    System.out.println("\n" + dictionary1); //Debería ser null
  }
}
