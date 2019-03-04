package sample;

import sample.Edge;
import sample.Node;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    Graph (String filePath) throws IOException {


        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = null;

            while ((line = reader.readLine()) != null) {

                    if (line.charAt(0) != '#') {

                        StringTokenizer result = new StringTokenizer(line, " ");

                        String temp = result.nextToken();

                        if (temp.equals("WEZLY")) {

                            result.nextToken(); // to skip "="
                            int numberOfNodes = Integer.parseInt(result.nextToken());
                            nodes = new ArrayList<>(numberOfNodes);

                            for (int i = 0; i < numberOfNodes; i++) {

                                line = reader.readLine();

                                if (line.charAt(0) == '#'){
                                    i--;
                                }
                                else {

                                    StringTokenizer result1 = new StringTokenizer(line, " ");

                                    Node temp1 = new Node(  Integer.parseInt(result1.nextToken()),
                                                            Integer.parseInt(result1.nextToken()),
                                                            Integer.parseInt(result1.nextToken()));

                                    nodes.add(temp1);
                                }
                            }
                        }

                        else if (temp.equals("LACZA")) {

                            result.nextToken();
                            int numberOfEdges = Integer.parseInt(result.nextToken());
                            edges = new ArrayList<>(numberOfEdges);

                            for (int i = 0; i < numberOfEdges; i++) {

                                line = reader.readLine();

                                if (line.charAt(0) == '#'){
                                    i--;
                                }
                                else {

                                    StringTokenizer result1 = new StringTokenizer(line, " ");
                                    int ID = Integer.parseInt(result1.nextToken());
                                    int firstNodeID = Integer.parseInt(result1.nextToken());
                                    int secondNodeID = Integer.parseInt(result1.nextToken());
                                    Node firstNode = null;
                                    Node secondNode = null ;

                                    for(Node node: nodes){
                                        if (node.getID() == firstNodeID)
                                            firstNode = node;

                                        else if (node.getID() == secondNodeID)
                                            secondNode = node;

                                    }

                                    Edge temp2 = new Edge(  ID, firstNode, secondNode);

                                    edges.add(temp2);

                                }
                            }

                        }
                    }

                }
            } catch (FileNotFoundException e) {
                System.out.println("Couldn't find file!");
            } catch (IOException e) {
                System.out.println("Couldn't read file path!");
            } catch (NumberFormatException e) {
                System.out.println("Couldn't read file path!");
            }
        }


    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}


