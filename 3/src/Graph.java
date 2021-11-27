import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    int n;
    Node[] nodes;
    private boolean[][] isConnected;
    private boolean find;
    private int[] father;
    private Queue<Integer> queue;
    public Graph(int n){
        this.n = n;
        nodes = new Node[n];
        for(int i = 0; i < n; i++){
            nodes[i] = new Node(i);
        }
        isConnected = new boolean[n][n];
        father = new int[n];
        queue = new LinkedList<>();
    }
    public void addEdge(int startNode, int endNode){
        isConnected[startNode][endNode] = true;
        if(startNode == 0 || endNode == n - 1){
            isConnected[endNode][startNode] = true;
        }
        nodes[startNode].adjacencyList.add(endNode);
        nodes[endNode].adjacencyList.add(startNode);
    }
    private void bfs(int number){
        find = false;
        boolean[] mark = new boolean[n];
        Node node;
        father[number] = -1;
        mark[number] = true;
        queue.add(number);
        while (!queue.isEmpty()){
            node = nodes[queue.poll()];
            for (int i : node.adjacencyList){
                if(!mark[i] && isConnected[node.number][i]){
                    mark[i] = true;
                    queue.add(i);
                    father[i] = node.number;
                    if(i == n - 1){
                        find = true;
                    }
                }
            }
        }
    }
    public int maxFlow(){
        int maxFlow = 0;
        bfs(0);
        while (find){
            maxFlow++;
            int endNode = father[n - 1];
            int startNode = father[endNode];
            while (startNode != 0){
                isConnected[startNode][endNode] = false;
                isConnected[endNode][startNode] = true;
                endNode = startNode;
                startNode = father[endNode];
            }
            bfs(0);
        }
        return maxFlow;
    }
}