import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private final HashMap<Node, List<Node>> graph;


    public Graph(int numLasers) {
        graph = new HashMap<Node, List<Node>>(numLasers);
    }

    public void addAdjacentNode(Node node1, Node adjacentNode) {
        graph.get(node1).add(adjacentNode);
    }

    public void addNode(Node node) {
        graph.put(node, new LinkedList<>());
    }

    public Iterable<Node> adjacentNodes( Node node ) {
        return graph.get(node);
    }

    public int numNodes() {
        return graph.size();
    }





}
