import UnionFind.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CanRobEscape {

    private int length;
    private int width;
    private int diameter;
    private int nLasers;
    private List<Point> nodes;
    private UnionFind partition;

    public CanRobEscape(int length, int width, int diameter, int nLasers){
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        this.nLasers = nLasers;
        this.nodes = new ArrayList<>();
        this.partition = new UnionFindInArray(nLasers);
    }

    public void addNode(Point node) {
        int id = 0;

        for(Point n : this.nodes) {
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
        List<Integer> partitionRoots = new ArrayList<>();

        for(int i = 0; i < nLasers; i++) {
            int root = partition.find(i);
            if (!partitionRoots.contains(root)) {
                partitionRoots.add(root);
            }
        }

        for(int root: partitionRoots ){
            double top = Integer.MAX_VALUE;
            double bot = Integer.MAX_VALUE;
            for(int i = 0; i < nLasers; i++) {
                int rootId = partition.find(i);

                if(rootId == root){
                    Point node = nodes.get(i);
                    double top_distance = topDistance(node);
                    double bottom_distance = bottomDistance(node);

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

    private double distance(Point node1, Point node2) {
        double deltaX = node2.getX() - node1.getX();
        double deltaY = node2.getY() - node1.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private double topDistance(Point node) {
        return width - node.getY();
    }

    private double bottomDistance(Point node) {
        return node.getY();
    }
}
