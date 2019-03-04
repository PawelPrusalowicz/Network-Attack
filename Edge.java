package sample;

import sample.Node;

public class Edge {

    private int ID;
    private Node firstNode;
    private Node secondNode;
    private double length;

    public Edge(int ID, Node firstNode, Node secondNode) {
        this.ID = ID;
        this.firstNode = firstNode;
        this.secondNode = secondNode;

        length = Math.sqrt((secondNode.getY() - firstNode.getY()) * (secondNode.getY() - firstNode.getY()) +
                (secondNode.getX() - firstNode.getX()) * (secondNode.getX() - firstNode.getX()));
    }

    public double getLength() {
        return length;
    }

    public int getID() {
        return ID;
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }
}
