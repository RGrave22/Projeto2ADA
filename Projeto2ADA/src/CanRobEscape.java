import java.util.*;


public class CanRobEscape {


    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Node> nodes;
    private Graph graph;

//    private UnionFind partition;

    private HashMap<Node,Integer> graph1;




    public CanRobEscape(int length, int width, int diameter, int nLasers, List<Node> nodes){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = nodes;
        graph = new Graph(nLasers);
//        partition = new UnionFindInArray(nodes.size());

    }

    public void createGraph() {
//        nodes.sort(Comparator.comparing(Node::getX));

        for(int i = 0; i < nodes.size()-1; i++){
            Node node = nodes.get(i);
            graph.addNode(node);
            graph.addAdjacentNode(node, nodes.get(i+1));
        }

        graph.addNode(nodes.get(nodes.size()-1));
    }

//    public void initialPartition() {
//        for (Node node: nodes) {
//            int id = graph1.get(node);
//            partition.find(id);
//            graph1.put(node,id);
//        }
//    }

//    public void createPartitions() {
//        for(Node node: nodes){
//            for(Node nodeToJoin: nodes) {
//                if(!areTheSame(node,nodeToJoin)){
//                    if(distance(node,nodeToJoin) < diameter) {
//        int n1 = partition.find(graph1.get(node));
//        int n2 = partition.find(graph1.get(nodeToJoin));
//        partition.union(n1,n2);
//    }
//    }
//}
//        }
//                }

//    public boolean solution(){
//
//    }





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

        do {

            Node node = adjacent.pop();
            if(topDistance(node) < diameter && bottomDistance(node) < diameter && nodes.size()==1)
                return false;

            for(Node adjacentNode: graph.adjacentNodes(node)) {
                if(isHigher(adjacentNode, node)) {
                    if(distance(node,adjacentNode) < diameter){
                        if(topDistance(adjacentNode) < diameter && bottomDistance(node) < diameter) {
                            return false;
                        }
                    }
                }else {
                    if(topDistance(node) < diameter){
                        if(topDistance(node) < diameter && bottomDistance(adjacentNode) < diameter) {
                            return false;
                        }
                    }
                }
                //   System.out.println(adjacentNode.getX()+ " " + adjacentNode.getY());
                adjacent.push(adjacentNode);
            }

        }
        while (!adjacent.empty());

        return true;
    }

    private boolean areTheSame(Node node1, Node node2) {
        return node1.getX() == node2.getX() && node1.getY() == node2.getY();
    }

    private boolean isHigher(Node node1, Node node2) {
        return node1.getY() >= node2.getY();
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
