import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    static Graph graph;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        initial(scanner);
        graph.find();
    }

    static void initial(Scanner scanner){
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        Node[] nodes = new Node[n + m];
        for(int i = 0; i < n + m; i++){
            nodes[i] = new Node(i);
        }
        int[][] edgesCount = new int[n + m][n + m];
        graph = new Graph(n, m, k, nodes, edgesCount);
        while(k != 0){
            k--;
            int i = scanner.nextInt() - 1;
            int j = scanner.nextInt() + n - 1;
            if(edgesCount[i][j] == 0) {
                nodes[i].addAdjacencyList(j);
                nodes[j].addAdjacencyList(i);
            }
            nodes[i].increase();
            nodes[j].increase();
            edgesCount[i][j]++;
        }
    }

}

