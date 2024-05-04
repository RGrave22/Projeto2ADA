import java.util.*;

import UnionFind.*;


public class CanRobEscape {


    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Node> nodes;
    private Graph graph;

    private UnionFind partition;

    private HashMap<Node,Integer> graph1;




    public CanRobEscape(int length, int width, int diameter, int nLasers, List<Node> nodes){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = nodes;
        graph = new Graph(nLasers);
        graph1 = new HashMap<>(nodes.size());
        partition = new UnionFindInArray(nodes.size());

    }

//    public void createGraph() {
////        nodes.sort(Comparator.comparing(Node::getX));
//
//        for(int i = 0; i < nodes.size()-1; i++){
//            Node node = nodes.get(i);
//            graph.addNode(node);
//            graph.addAdjacentNode(node, nodes.get(i+1));
//        }
//
//        graph.addNode(nodes.get(nodes.size()-1));
//    }

    public void createGraph() {
//        nodes.sort(Comparator.comparing(Node::getX));

        for(int i = 0; i < nodes.size()-1; i++){
            for(int j = 0; j < nodes.size()-1; j++) {
                Node node1 = nodes.get(i);
                Node node2 = nodes.get(j);
                if(!areTheSame(node1,node2)){
                    graph.addNode(node1);
                    if(distance(node1,node2) >= diameter*2) {
                        graph.addAdjacentNode(node1, node2);
                    }
                }
            }
        }
    }

    public void initialPartition() {
        for (Node node: nodes) {
//            int id = graph1.get(node);
//            partition.find(id);
            int id = nodes.indexOf(node);
            graph1.put(node,id);
            // System.out.println(id);
        }
        // System.out.println(graph1);
    }


    public void createPartitions() {
        // System.out.println("=============================================================");
        for(Node node: nodes){
            // System.out.println("->" + nodes.indexOf(node));
            for(Node nodeToJoin: nodes) {
                // System.out.println(nodes.indexOf(nodeToJoin));
                if(!node.equals(nodeToJoin)){
                    // System.out.println("DISTANCE:"+distance(node,nodeToJoin));
                    if(distance(node,nodeToJoin) < diameter) {
                        int n1 = partition.find(graph1.get(node));
                        int n2 = partition.find(graph1.get(nodeToJoin));
                        if(n1 != n2) {
                            partition.union(n1, n2);
                            // System.out.println("UNION: " + n1 + " " + n2);
                        }
                    }
                }
            }
        }
    }




    public boolean solution(){

        List<Integer> partitionsRoots = new ArrayList<>();

        for(Node node: nodes) {
            int id = graph1.get(node);
            int root = partition.find(id);
            if(!partitionsRoots.contains(root)){
                partitionsRoots.add(root);
            }
        }


        for(int root: partitionsRoots ){
            int top = Integer.MAX_VALUE;
            int bot = Integer.MAX_VALUE;
            for(Node node: nodes) {
                int id = graph1.get(node);
                int rootId = partition.find(id);
                if(rootId == root){
                    if(topDistance(node) < top){
                        top = topDistance(node);
                    }
                    if(bottomDistance(node) < bot){
                        bot = bottomDistance(node);
                    }
                }
            }
            if(top < diameter && bot < diameter){
                return false;
            }

        }
        return true;

    }


    public boolean solve() {
        partition.print();
        return dfsExplore(graph, nodes.get(0));
    }



    //Se o próximo ponto esta por cima, se passar pelo meio TOP, se não ou passa por baixo do a baixo ou por cima do a cima
    //Se o próximo ponto esta por baixo, se passar pelo meio temos de verificar se o ponto a baixo tem espaço por cima,
    // se não passa pelo meio ou passa por cima ou por baixo ou não passa
    //
//     public boolean dfsExplore( Graph graph, Node root ) {
//
//         Stack<Node> adjacent = new Stack<>();
//         adjacent.push(root);
//
//         do {
//
//             Node node = adjacent.pop();
//             if(topDistance(node) < diameter && bottomDistance(node) < diameter && nodes.size()==1)
//                 return false;
//             else if(topDistance(node) >= diameter && bottomDistance(node) >= diameter && nodes.size()==1)
//                 return true;
//             for(Node adjacentNode: graph.adjacentNodes(node)) {
//                 if(isHigher(adjacentNode, node)) {
//                     if(distance(node,adjacentNode) < diameter){
//                         if(topDistance(adjacentNode) < diameter && bottomDistance(node) < diameter) {
//                             System.out.println("NODE: " + node.getX() + " " + node.getY());
//                             System.out.println("NODE ADJACENTE: "+ adjacentNode.getX()  + " " + adjacentNode.getY());
//                             System.out.println("Parou no de cima");
//                             return false;
//                         }
//                     }
//                 }else {
//                     if(topDistance(node) < diameter){
//                         if(topDistance(node) < diameter && bottomDistance(adjacentNode) < diameter) {
//                             System.out.println("NODE: " + node.getX() + " " + node.getY());
//                             System.out.println("NODE ADJACENTE: "+ adjacentNode.getX()  + " " + adjacentNode.getY());
//                             System.out.println("Parou no de baixo");
//                             return false;
//                         }
//                     }
//                 }
//              //   System.out.println(adjacentNode.getX()+ " " + adjacentNode.getY());
//                 adjacent.push(adjacentNode);
//             }
//
//     }
//         while (!adjacent.empty());
//
//         return true;
//     }


    //    Se o próximo ponto esta por cima, se passar pelo meio TOP, se não ou passa por baixo do a baixo ou por cima do a cima
//    Se o próximo ponto esta por baixo, se passar pelo meio temos de verificar se o ponto a baixo tem espaço por cima,
//     se não passa pelo meio ou passa por cima ou por baixo ou não passa
    public boolean dfsExplore( Graph graph, Node root ) {

        Stack<Node> adjacent = new Stack<>();
        int pos=-1;

        adjacent.push(root);


        do {

            Node node = adjacent.pop();
            if(topDistance(node) < diameter && bottomDistance(node) < diameter && nodes.size()==1)
                return false;
            else if(topDistance(node) >= diameter && bottomDistance(node) >= diameter && nodes.size()==1)
                return true;

            for(Node adjacentNode: graph.adjacentNodes(node)) {
                if(isHigher(adjacentNode, node)) {
                    if(distance(adjacentNode,node) >= diameter){
                        pos = 1;
                    }else if(topDistance(adjacentNode) >= diameter && bottomDistance(node) < diameter){
                        pos = 2;
                    }else {
                        return false;
                    }
                }else {
                    if(distance(node,adjacentNode) >= diameter){
                        if(topDistance(adjacentNode) < diameter && topDistance(node) < diameter && bottomDistance(adjacentNode) < diameter) {
                            return false;
                        }
                    }else{
                        if (topDistance(node) < diameter && bottomDistance(adjacentNode) < diameter) {
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
        if( node1.getX() == node2.getX() && node1.getY() == node2.getY()){
            return true;
        }else {
            return false;
        }
    }

    private boolean isHigher(Node node1, Node node2) {
        return node1.getY() > node2.getY();
    }

//    private int distance(Node node1, Node node2){
//        return Math.abs(node1.getX() - node2.getX()) + Math.abs(node1.getY() - node2.getY());
//    }

    private double distance(Node node1, Node node2) {
        int deltaX = node2.getX() - node1.getX();
        int deltaY = node2.getY() - node1.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private int topDistance(Node node) {
        return width - node.getY();
    }

    private int bottomDistance(Node node) {
        return node.getY();
    }


}
