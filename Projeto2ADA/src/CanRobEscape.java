

import UnionFind.*;

import java.util.ArrayList;
import java.util.List;


public class CanRobEscape {

    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Node> nodes;
    private UnionFind partition;

    public CanRobEscape(int length, int width, int diameter, int nLasers){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = new ArrayList<>();
        this.partition = new UnionFindInArray(nLasers);
    }

    public void addNode(Node node) {
        int id = 0;

        for(Node n : this.nodes) {
            if (distance(n, node) < diameter) {
                int n1 = partition.find(id);
                int n2 = partition.find(nodes.size());
                if (n1 != n2) {
                    partition.union(n1, n2);
                }
            }
            id++;
        }
        this.nodes.add(node);
    }

    public boolean solution(){
        List<Integer> partitionsRoots = new ArrayList<>();

        for(int i = 0; i < nLasers; i++) {
            int root = partition.find(i);
            if (!partitionsRoots.contains(root)) {
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
                    int top_distance = topDistance(node);
                    int bottom_distance = bottomDistance(node);

                    if(top_distance < top){
                        top = top_distance;
                    }
                    if(bottom_distance < bot){
                        bot = bottom_distance;
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
