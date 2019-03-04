package sample;

import sample.Graph;
import sample.Node;

import java.io.IOException;
import java.util.*;

public class Dijkstra {

    Graph graph;
    ArrayList<Integer> dijkstraNodes;
    ArrayList<Edge> dijkstraEdges;
    int sourceNodeID;
    int finalNodeID;
    double finalPathLength;

    //dla grafow o krawedziach skierowanych
    Dijkstra(Graph graph1, ArrayList<Edge> edges1, ArrayList<Node> nodes1, int sourceNodeID, int finalNodeID){


            this.sourceNodeID = sourceNodeID;
            this.finalNodeID = finalNodeID;


            //graph = new Graph(filepath);
            Graph graph = graph1;
            ArrayList<Edge> edges = edges1;
            ArrayList<Node> nodes = nodes1;


            dijkstraNodes = new ArrayList<Integer>();
            dijkstraEdges = new ArrayList<Edge>();


            PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {

                    if(o1.getShortestDistanceToSource() > o2.getShortestDistanceToSource())
                        return 1;

                    else
                        return 0;
                }
            });


            for( Node node: nodes){
                if(node.getID() == sourceNodeID){
                    node.setShortestDistanceToSource(0);
                }
                else{
                    node.setShortestDistanceToSource(Double.MAX_VALUE);
                }

                queue.add(node);

            }


            while ( !queue.isEmpty()){


                Node currentNode = queue.poll();

                for (Edge edge : edges){

                    Node neighbourNode = null;

                    if (edge.getFirstNode().getID() == currentNode.getID() ) {
                        neighbourNode = edge.getSecondNode();


                        double distanceThroughCurrentNode = currentNode.getShortestDistanceToSource() + edge.getLength();



                        if (distanceThroughCurrentNode < neighbourNode.getShortestDistanceToSource()) {

                            neighbourNode.setShortestDistanceToSource(distanceThroughCurrentNode);


                            neighbourNode.setPreviousNodeID(currentNode.getID());
                            queue.add(neighbourNode);
                        }

                    }

                    else if (edge.getSecondNode().getID() == currentNode.getID() ){
                        neighbourNode = edge.getFirstNode();


                        double distanceThroughCurrentNode = currentNode.getShortestDistanceToSource() + edge.getLength();

                        if (distanceThroughCurrentNode < neighbourNode.getShortestDistanceToSource()) {

                            neighbourNode.setShortestDistanceToSource(distanceThroughCurrentNode);
                            neighbourNode.setPreviousNodeID(currentNode.getID());
                            queue.add(neighbourNode);
                        }
                    }

                }
            }



            Node temp = new Node(findNodeByID (nodes, finalNodeID) );

            dijkstraNodes.add(temp.getID());
            dijkstraNodes.add (temp.getPreviousNodeID());

            while ( temp.getPreviousNodeID() != sourceNodeID){

                temp = new Node( findNodeByID(nodes, temp.getPreviousNodeID()) );
                dijkstraNodes.add(temp.getPreviousNodeID());

            }

            Collections.reverse(dijkstraNodes);

            for (int i = 0; i< dijkstraNodes.size()-1; i++){
                for (Edge edge: edges){
                    if (    edge.getFirstNode().getID() == dijkstraNodes.get(i) &&
                            edge.getSecondNode().getID() == dijkstraNodes.get(i+1) )
                    {
                        dijkstraEdges.add(edge);
                    }
                }
            }


            for (int i = 0; i< dijkstraNodes.size()-1; i++){
                for (Edge edge: edges){
                    if (    edge.getSecondNode().getID() == dijkstraNodes.get(i) &&
                            edge.getFirstNode().getID() == dijkstraNodes.get(i+1) )
                    {
                        dijkstraEdges.add(edge);
                    }
                }
            }

        for (Edge edge: dijkstraEdges){
            finalPathLength += edge.getLength();
        }

    }





    Node findNodeByID(ArrayList<Node> nodes, int ID){
        for (int i = 0; i<nodes.size(); i++){
            if (nodes.get(i).getID() == ID){
                return nodes.get(i);
            }
        }
        return null;
    }

    public ArrayList<Integer> getDijkstraNodes() {
        return dijkstraNodes;
    }

    public Graph getGraph() {
        return graph;
    }

    public int getSourceNodeID() {
        return sourceNodeID;
    }

    public int getFinalNodeID() {
        return finalNodeID;
    }

    public double getFinalPathLength() {
        return finalPathLength;
    }
}


