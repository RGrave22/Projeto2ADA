import java.util.*;

import UnionFind.*;


public class CanRobEscape {

    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Node> nodes;
    private UnionFind partition;
    private HashMap<Node,Integer> partitionMap;

    public CanRobEscape(int length, int width, int diameter, int nLasers, List<Node> nodes){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = nodes;
        partitionMap = new HashMap<>(nLasers);
        partition = new UnionFindInArray(nLasers);
    }

    public void createPartitions() {
//        for (Node node: nodes) {
//            int id = nodes.indexOf(node);
//            partitionMap.put(node,id);
//        }
//
//        // System.out.println("=============================================================");
//        for(Node node: nodes) {
//            // System.out.println("->" + nodes.indexOf(node));
//            for (Node nodeToJoin : nodes) {
//                // System.out.println(nodes.indexOf(nodeToJoin));
//                if (!node.equals(nodeToJoin)) {
//                    // System.out.println("DISTANCE:"+distance(node,nodeToJoin));
//                    if (distance(node, nodeToJoin) < diameter) {
//                        int n1 = partition.find(partitionMap.get(node));
//                        int n2 = partition.find(partitionMap.get(nodeToJoin));
//                        if (n1 != n2) {
//                            partition.union(n1, n2);
//                            // System.out.println("UNION: " + n1 + " " + n2);
//                        }
//                    }
//                }
//            }
//        }

        for(int i = 0; i < nLasers; i++) {
            // System.out.println("->" + nodes.indexOf(node));
            for (int j = 0; j < nLasers; j++) {
                // System.out.println(nodes.indexOf(nodeToJoin));
                if (i != j) {
                    // System.out.println("DISTANCE:"+distance(node,nodeToJoin));
                    if (distance(nodes.get(i), nodes.get(j)) < diameter) {
                        int n1 = partition.find(i);
                        int n2 = partition.find(j);
                        if (n1 != n2) {
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

//        for(Node node: nodes) {
////            int id = graph1.get(node);
//            int id = partitionMap.get(node);
//            int root = partition.find(id);
//            if(!partitionsRoots.contains(root)){
//                partitionsRoots.add(root);
//            }
//        }

        for(int i = 0; i < nLasers; i++) {
            int root = partition.find(i);
            if(!partitionsRoots.contains(root)){
                partitionsRoots.add(root);
            }
        }

//        for(int root: partitionsRoots ){
//            int top = Integer.MAX_VALUE;
//            int bot = Integer.MAX_VALUE;
//            for(Node node: nodes) {
//                int id = partitionMap.get(node);
//                int rootId = partition.find(id);
//                if(rootId == root){
//                    if(topDistance(node) < top){
//                        top = topDistance(node);
//                    }
//                    if(bottomDistance(node) < bot){
//                        bot = bottomDistance(node);
//                    }
//                }
//            }
//            if(top < diameter && bot < diameter){
//                return false;
//            }
//        }

        for(int root: partitionsRoots ){
            int top = Integer.MAX_VALUE;
            int bot = Integer.MAX_VALUE;
            for(int i = 0; i < nLasers; i++) {
                int rootId = partition.find(i);
                if(rootId == root){
                    Node node = nodes.get(i);
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
