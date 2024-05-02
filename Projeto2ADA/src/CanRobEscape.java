import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CanRobEscape {


    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Node> nodes;
    private Graph graph;

    public CanRobEscape(int length, int width, int diameter, int nLasers, List<Node> nodes){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = nodes;
        Graph graph = new Graph(nLasers);

        nodes.sort(Comparator.comparing(Node::getX));

        for(int i = 0; i < nodes.size()-1; i++){
            Node node = nodes.get(i);
            graph.addNode(node);
            graph.addAdjacentNode(node, nodes.get(i+1));
        }

        graph.addNode(nodes.getLast());
    }


    boolean dfsExplore( Graph graph, Node root ) {


        Stack<Node> adjacent = new Stack<>();
        adjacent.push(root);
        do {

            Node node = adjacent.pop();
            for(Node adjacentNode: graph.adjacentNodes(node)) {

              


            }




        }
        while (!adjacent.empty());

        return true;
    }







    private boolean isHigher(Node node1, Node node2) {
        return node1.getY() > node2.getY();
    }

    private int distance(Node node1, Node node2){
        return Math.abs(node1.getX() - node2.getX()) + Math.abs(node1.getY() - node2.getY());
    }

    private int topDistance(Node node) {
        return width - node.getY();
    }

    private int bottomDistance(Node node) {
        return node.getY();
    }

}
