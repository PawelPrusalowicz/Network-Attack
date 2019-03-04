package sample;

public class Node {

    private int ID;
    private int x;
    private int y;
    private int previousNodeID;

    // for Dijkstra
    private double shortestDistanceToSource;


    // for Prim
    private Edge edgePrim;


    public Node(int ID, int x, int y) {
        this.ID = ID;
        this.x = x;
        this.y = y;


        shortestDistanceToSource = Double.MAX_VALUE;
        previousNodeID = ID;
        edgePrim = null;

    }

    public Node(Node node) {
        this.ID = node.getID();
        this.x = node.getX();
        this.y = node.getY();
        this.shortestDistanceToSource = node.getShortestDistanceToSource();
        this.previousNodeID = node.getPreviousNodeID();

    }

    public int getID() {
        return ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public double getShortestDistanceToSource() {
        return shortestDistanceToSource;
    }

    public void setShortestDistanceToSource(double shortestDistanceToSource) {
        this.shortestDistanceToSource = shortestDistanceToSource;
    }

    public void setPreviousNodeID(int previousNodeID) {
        this.previousNodeID = previousNodeID;
    }

    public int getPreviousNodeID() {
        return previousNodeID;
    }

    public Edge getEdgePrim() {
        return edgePrim;
    }

    public void setEdgePrim(Edge edgePrim) {
        this.edgePrim = edgePrim;
    }
}
