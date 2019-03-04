package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label drawGraphLabel;

    @FXML
    private Label drawGraphLabel1;

    @FXML
    private Canvas drawGraphCanvas;

    @FXML
    void initialize() {


        Graph graph = null;
        try {
            graph = new Graph("/Users/Pawel/Documents/Pawel/Studia/sem 3/AISDE/network2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LongestPath longestPath = new LongestPath(graph);
        drawGraph(graph, longestPath.getSolutionEdgesMST(), longestPath.edgeToDeleteID,false);
        fillInformation(longestPath);

    }


    void drawGraph( Graph graph, ArrayList<Edge> solutionEdges, int edgeToDeleteID, boolean directedGraph){

        ArrayList<Edge> edges = graph.getEdges();
        ArrayList<Node> nodes = graph.getNodes();


        GraphicsContext gc = drawGraphCanvas.getGraphicsContext2D();
        gc.setLineWidth(1.0);

        ArrayList<Double> graphNodeX = new ArrayList<Double>(nodes.size());
        ArrayList<Double> graphNodeY = new ArrayList<Double>(nodes.size());

        ArrayList<Double> graphNodeTextX = new ArrayList<Double>(nodes.size());
        ArrayList<Double> graphNodeTextY = new ArrayList<Double>(nodes.size());

        double graphScaleFactor = 0;
        int maximalCoordinate = 0;

        for (Node node: nodes){
            if (maximalCoordinate < node.getX())
                maximalCoordinate = node.getX();

            if (maximalCoordinate < node.getY())
                maximalCoordinate = node.getY();
        }

        graphScaleFactor = 0.8 * drawGraphCanvas.getWidth()/maximalCoordinate;


        for (int i = 0; i < nodes.size(); i++) {
            graphNodeX.add((double) nodes.get(i).getX() *graphScaleFactor);
            graphNodeY.add(drawGraphCanvas.getHeight() - (double) nodes.get(i).getY() *graphScaleFactor);

            graphNodeTextX.add( (double) nodes.get(i).getX() *graphScaleFactor - 20);

            if(nodes.get(i).getY() *graphScaleFactor > drawGraphCanvas.getHeight()/2)
                graphNodeTextY.add( drawGraphCanvas.getHeight() - (double) nodes.get(i).getY() *graphScaleFactor - 20);
            else
                graphNodeTextY.add( drawGraphCanvas.getHeight() - (double) nodes.get(i).getY() *graphScaleFactor + 20);
        }

        for (Edge edge: edges) {

            boolean isSolutionEdge = false;

            for (Edge solutionEdge: solutionEdges){

                if (edge == solutionEdge){
                    isSolutionEdge = true;
                }
            }

            if (isSolutionEdge ) {
                gc.setStroke(Color.GREEN);
                gc.setFill(Color.GREEN);
            }
            else{
                gc.setStroke(Color.GRAY);
                gc.setFill(Color.BLACK);
            }

            if (edge.getID() == edgeToDeleteID){
                gc.setStroke(Color.RED);
                gc.setFill(Color.RED);
            }


            drawArrow( gc, edge, directedGraph,graphNodeX.get(edge.getFirstNode().getID()-1) + 7.5,
                    graphNodeY.get(edge.getFirstNode().getID()-1) + 7.5,
                    graphNodeX.get(edge.getSecondNode().getID()-1)+ 7.5,
                    graphNodeY.get(edge.getSecondNode().getID()-1)+ 7.5 );

        }


        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);


        for (int i = 0; i < nodes.size(); i++) {

            gc.strokeOval(graphNodeX.get(i), graphNodeY.get(i), 15, 15);
            gc.fillOval(graphNodeX.get(i), graphNodeY.get(i), 15, 15);

            gc.fillText("N" + nodes.get(i).getID() +
                            "(" + nodes.get(i).getX() + ", " + nodes.get(i).getY() + ")",
                    graphNodeTextX.get(i), graphNodeTextY.get(i) + 15);

        }


    }





    void drawArrow(GraphicsContext gc, Edge edge, boolean directedEdge, double x1, double y1, double x2, double y2) {

        int ARR_SIZE = 10;

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        Transform defaultTransform = gc.getTransform();

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);

        if (directedEdge)
            gc.fillPolygon(new double[]{len*0.85, len*0.85 - ARR_SIZE, len*0.85 - ARR_SIZE, len*0.85}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0},
                    4);

        if (Math.toDegrees(angle) > 90 || Math.toDegrees(angle) < -90 ){

            transform = Transform.translate(x2, y2);
            transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle)+180, 0, 0));
            gc.setTransform(new Affine(transform));
        }

        gc.setFill(Color.GRAY);
        gc.fillText("E" + edge.getID() + " (" + String.format("%.2f", edge.getLength()) + ")",len/4, -5,80);

        gc.setTransform(new Affine(defaultTransform));
    }


    void fillInformation(LongestPath longestPath){

        drawGraphLabel.setText("Server attack\nMST Edges: ");

        for(Edge edge: longestPath.edgesMST){
            drawGraphLabel.setText(drawGraphLabel.getText() + "  E" + edge.getID());
        }

        drawGraphLabel.setText(drawGraphLabel.getText() + "\nTotal cost: " +
                String.format("%.2f", longestPath.getTotalCost()));

        drawGraphLabel.setText(drawGraphLabel.getText() + "\nEdge deleted: E" + longestPath.getEdgeToDeleteID());

    }


}

