import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

public class Main {
    static int n, m, ns, nt;
    static Graph graph;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        initial(scanner);
        System.out.println(graph.maxFlow());
    }
    static void initial(Scanner scanner){
        n = scanner.nextInt();
        m = scanner.nextInt();
        ns = scanner.nextInt();
        nt = scanner.nextInt();
        graph = new Graph(2 * n + 2 - ns - nt);
        graph.nodes[0].isInternal = false;
        while(ns != 0){
            Node node = graph.nodes[scanner.nextInt()];
            node.setNext(node);
            node.isInternal = false;
            graph.addEdge(0, node.number);
            ns--;
        }
        while (nt != 0){
            Node node = graph.nodes[scanner.nextInt()];
            node.setNext(node);
            node.isInternal = false;
            graph.addEdge(node.number, graph.n - 1);
            nt--;
        }
        int t = n + 1;
        for(int i = 1; i <= n; i++){
            Node node = graph.nodes[i];
            if(node.isInternal){
                Node next = graph.nodes[t];
                node.setNext(next);
                graph.addEdge(i, t);
                t++;
            }
        }
        graph.nodes[t].isInternal = false;
        while(m != 0){
            Node node = graph.nodes[scanner.nextInt()].getNext();
            graph.addEdge(node.number, scanner.nextInt());
            m--;
        }
    }
}
