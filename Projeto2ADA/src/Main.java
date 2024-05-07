
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] wall = in.readLine().split(" ");
        int length = Integer.parseInt(wall[0]);
        int width = Integer.parseInt(wall[1]);

        int diameter = Integer.parseInt(in.readLine());

        int nLasers = Integer.parseInt(in.readLine());

        CanRobEscape cre = new CanRobEscape(length,width,diameter,nLasers);

        for(int i = 0; i < nLasers; i++) {
            String[] line = in.readLine().split(" ");
            Node node = new Node(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            cre.addNode(node);
        }

        if(cre.solution()){
            System.out.println("Rob manages to escape!");
        }else{
            System.out.println("Impossible to escape");
        }

    }
}