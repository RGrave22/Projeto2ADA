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
        graph = new Graph(nLasers);

    }

    public void createGraph() {
        nodes.sort(Comparator.comparing(Node::getX));

        for(int i = 0; i < nodes.size()-1; i++){
            Node node = nodes.get(i);
            graph.addNode(node);
            graph.addAdjacentNode(node, nodes.get(i+1));
        }

        graph.addNode(nodes.getLast());
    }

    public boolean solve() {
        return dfsExplore(graph, nodes.get(0));
    }


    //Se o próximo ponto esta por cima, se passar pelo meio TOP, se não ou passa por baixo do a baixo ou por cima do a cima
    //Se o próximo ponto esta por baixo, se passar pelo meio temos de verificar se o ponto a baixo tem espaço por cima,
    // se não passa pelo meio ou passa por cima ou por baixo ou não passa
    //
    public boolean dfsExplore( Graph graph, Node root ) {

        Stack<Node> adjacent = new Stack<>();
        adjacent.push(root);
        boolean isTop = false;
        do {

            Node node = adjacent.pop();
            for(Node adjacentNode: graph.adjacentNodes(node)) {
                if(isHigher(adjacentNode, node)) {
                    if(distance(node,adjacentNode) < diameter){
                        if(topDistance(adjacentNode) < diameter && bottomDistance(node) < diameter)
                            return false;
                    }
                }else {
                    if(topDistance(adjacentNode) < diameter){
                        if(topDistance(node) < diameter && bottomDistance(adjacentNode) < diameter) {
                            return false;
                        }
                    }
                }
              


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
