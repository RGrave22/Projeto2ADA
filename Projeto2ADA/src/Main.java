
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] wall = in.readLine().split(" ");
        int length = Integer.parseInt(wall[0]);
        int width = Integer.parseInt(wall[1]);

        int diameter = Integer.parseInt(in.readLine());

        int nLasers = Integer.parseInt(in.readLine());

        List<Node> nodes = new ArrayList<>(nLasers);

        for (int i = 0; i<nLasers; i++) {
            String[] node = in.readLine().split(" ");
            Node nodeObj = new Node(Integer.parseInt(node[0]), Integer.parseInt(node[1]));
            nodes.add(nodeObj);
        }

        CanRobEscape cre = new CanRobEscape(length,width,diameter,nLasers,nodes);
        cre.createPartitions();

        if(cre.solution()){
            System.out.println("Rob manages to escape!");
        }else{
            System.out.println("Impossible to escape");
        }

    }
}