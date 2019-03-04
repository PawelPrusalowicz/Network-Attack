package sample;

import java.io.IOException;
import java.util.ArrayList;

// zadanie  - atak na siec
// Oslabic siec w taki sposob, zeby na wyznaczonym dla niej drzewie MST odleglosc miedzy krancowymi punktami byla jak najwieksza.

public class LongestPath {

    ArrayList<Edge> edges, edgesMST, solutionEdgesMST;
    ArrayList<Node> nodes;
    double totalCost = 0;
    int edgeToDeleteID;


    LongestPath(Graph graph) {

            edges = graph.getEdges();
            nodes = graph.getNodes();

            int numberOfEdges = edges.size();


            // szukamy krawedzi do usuniecia, ktora spelni zalozenia zadania
            for(int i = 0; i < numberOfEdges; i++) {
                // sortujemy wszystkie krawedzie od najkrotszej
                edges.sort((e1, e2) -> e1.getLength() >= (e2.getLength()) ? 0 : -1);

                Edge removedEdge = edges.get(i);
                edges.remove(i);

                edgesMST = new Kruskal(edges,nodes).getEdgesMST();

                Dijkstra dijkstra = new Dijkstra(graph, edgesMST, nodes, 1, 5);


                if (dijkstra.getFinalPathLength() > totalCost){
                    edgeToDeleteID = removedEdge.getID();
                    totalCost = dijkstra.getFinalPathLength();
                    solutionEdgesMST = edgesMST;
                }

                edges.add(i,removedEdge);

            }

    }


    public double getTotalCost() {
        return totalCost;
    }

    public int getEdgeToDeleteID() {
        return edgeToDeleteID;
    }

    public ArrayList<Edge> getSolutionEdgesMST() {
        return solutionEdgesMST;
    }
}

