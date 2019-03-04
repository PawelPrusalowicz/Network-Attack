package sample;

import sample.Edge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Kruskal {

    ArrayList<Edge> edges, edgesMST;
    ArrayList<Node> nodes;
    double totalCost;


    Kruskal(ArrayList<Edge> edges1, ArrayList<Node> nodes1) {

            edges = edges1;
            nodes = nodes1;

            // Krawędzie MST - Lista krawedzi, ktore naleza do rozwiazania (ich liczba = liczba wezlow -1)
            edgesMST = new ArrayList<Edge>(nodes.size()-1);

            // sortujemy wszystkie krawedzie od najkrotszej
            edges.sort((e1, e2) -> e1.getLength() >= (e2.getLength()) ? 0 : -1);


            // ustawiamy 'parent' dla kazdego wierzcholka, czyli samego siebie
            for ( Node node: nodes) {
                node.setPreviousNodeID(node.getID());
            }

            // sprawdzamy kazda krawedz, po kolei od najkrotszej
            for (Edge edge: edges) {

                // jesli maja roznych parentow, to trzeba je polaczyc, dodac krawedz do drzewka MST
                if (findParent(edge.getFirstNode()) != findParent(edge.getSecondNode())){

                    edgesMST.add(edge);
                    totalCost += edge.getLength();

                    // sprawiamy, ze oba wierzcholki (i cale ich drzewa) maja tego samego parenta - sa polaczone najlepsza droga z drzewem i nie rozpatrujemy ich juz oddzielnie
                    findParent(edge.getSecondNode()).setPreviousNodeID(findParent(edge.getFirstNode()).getID());
                }
            }


            edgesMST.sort((e1, e2) -> e1.getID() >= (e2.getID()) ? 0 : -1);


    }



    Node findParent (Node node) {
        if (node.getPreviousNodeID() != node.getID())
            return findParent(findNodeByID(nodes, node.getPreviousNodeID()));

        return node;
    }


    Node findNodeByID(ArrayList<Node> nodes, int ID){
        for (int i = 0; i<nodes.size(); i++){
            if (nodes.get(i).getID() == ID){
                return nodes.get(i);
            }
        }
        return null;
    }

    public ArrayList<Edge> getEdgesMST() {
        return edgesMST;
    }

    public double getTotalCost() {
        return totalCost;
    }

}
