import java.util.*;

import UnionFind.*;


public class CanRobEscape {

    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Node> nodes;
    private UnionFind partition;


    public CanRobEscape(int length, int width, int diameter, int nLasers, List<Node> nodes){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = nodes;
        partition = new UnionFindInArray(nLasers);
    }

    public void createPartitions() {

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

        for(int i = 0; i < nLasers; i++) {
            int root = partition.find(i);
            if(!partitionsRoots.contains(root)){
                partitionsRoots.add(root);
            }
        }

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
