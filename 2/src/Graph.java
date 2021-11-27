import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private int n, m, maximumDegree, edgeCount, t = 0;
    private Node[] nodes;   //inputs = Nodes[0:n - 1], outputs = Nodes[n:n + m - 1]
    private List<Integer> freeNodes;    //nodes with maximum degree
    private boolean find, isMaximumNode;
    private boolean[][] isMatchingEdge;
    private Queue<Node> queue;
    private int breakDist;
    private int[][] edgesCount;
    int matchingCount;

    public Graph(int n, int m, int k, Node[] nodes, int[][] edgesCount) {
        this.n = n;
        this.m = m;
        this.edgeCount = k;
        this.nodes = nodes;
        this.edgesCount = edgesCount;
        isMatchingEdge = new boolean[n + m][n + m];
    }

    private void findMaximumDegree() {
        maximumDegree = Integer.MIN_VALUE;
        for (Node node : nodes) {
            maximumDegree = Math.max(maximumDegree, node.getDegree());
        }
    }

    private void startBFS() {
        find = false;
        queue = new LinkedList<>();
        for (Node node : nodes) {
            node.isSelected = false;
            node.mark = false;
        }
        for(int numNode : freeNodes){
            Node node = nodes[numNode];
            if (!node.isMatchingPoint) {
                node.mark = true;
                node.dist = 0;
                node.father = null;
                node.grandFather = node;
                queue.add(node);
            }
        }
        BFS1();
    }

    private void BFS1(){
        while (!queue.isEmpty()) {
            Node start = queue.remove();
            for (int endNumber : start.adjacencyList) {
                Node end = nodes[endNumber];
                if (!end.mark && edgesCount[start.getNumber()][end.getNumber()] > 0) {
                    if (!end.isMatchingPoint || (isMaximumNode && isMatchingEdge[start.getNumber()][end.getNumber()] && (end.getDegree() < maximumDegree))) {
                        if(!end.isMatchingPoint){
                            matchingCount++;
                        }
                        end.isMatchingPoint = !end.isMatchingPoint;
                        find = true;
                        flipEdge(start, end);
                        isMatchingEdge[start.getNumber()][end.getNumber()] = !isMatchingEdge[start.getNumber()][end.getNumber()];
                        isMatchingEdge[end.getNumber()][start.getNumber()] = !isMatchingEdge[end.getNumber()][start.getNumber()];
                        Node end1 = start;
                        while (end1.dist > 0) {
                            Node start1 = end1.father;
                            flipEdge(start1, end1);
                            isMatchingEdge[start1.getNumber()][end1.getNumber()] = !isMatchingEdge[start1.getNumber()][end1.getNumber()];
                            isMatchingEdge[end1.getNumber()][start1.getNumber()] = !isMatchingEdge[end1.getNumber()][start1.getNumber()];
                            end1 = start1;
                        }
                        end1.isMatchingPoint = true;
                        return;
                    }
                    end.mark = true;
                    end.dist = start.dist + 1;
                    end.father = start;
                    queue.add(end);
                }
            }
        }
    }

    private void BFS() {
        outer:
        while (!queue.isEmpty()) {
            Node start = queue.remove();
            if (start.dist != breakDist && find) {
                break outer;
            }
            inner:
            for (int endNumber : start.adjacencyList) {
                Node end = nodes[endNumber];
                if (!end.mark && edgesCount[start.getNumber()][end.getNumber()] > 0) {
                    if ((!end.isMatchingPoint || (isMaximumNode && isMatchingEdge[start.getNumber()][end.getNumber()] && (end.getDegree() < maximumDegree))) && !start.grandFather.isSelected) {
                        if(!end.isMatchingPoint){
                            matchingCount++;
                        }
                        breakDist = start.dist;
                        start.grandFather.isSelected = true;
                        end.isMatchingPoint = !end.isMatchingPoint;
                        find = true;
                        flipEdge(start, end);
                        isMatchingEdge[start.getNumber()][end.getNumber()] = !isMatchingEdge[start.getNumber()][end.getNumber()];
                        isMatchingEdge[end.getNumber()][start.getNumber()] = !isMatchingEdge[end.getNumber()][start.getNumber()];
                        Node end1 = start;
                        while (end1.dist > 0) {
                            Node start1 = end1.father;
                            flipEdge(start1, end1);
                            isMatchingEdge[start1.getNumber()][end1.getNumber()] = !isMatchingEdge[start1.getNumber()][end1.getNumber()];
                            isMatchingEdge[end1.getNumber()][start1.getNumber()] = !isMatchingEdge[end1.getNumber()][start1.getNumber()];
                            end1 = start1;
                        }
                        end1.isMatchingPoint = true;
                        break inner;
                    }
                    end.mark = true;
                    end.dist = start.dist + 1;
                    end.father = start;
                    end.grandFather = start.grandFather;
                    queue.add(end);
                }
            }
        }
    }

    private void findMatching() {
        matchingCount = 0;
        isMaximumNode = false;
        freeNodes = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (nodes[i].getDegree() == maximumDegree) {
                freeNodes.add(i);
            }
        }
        find = true;
        while (find && !freeNodes.isEmpty()) {
            startBFS();
        }
        flipAllEdges();
        isMaximumNode = true;
        freeNodes = new LinkedList<>();
        find = true;
        for (int i = n; i < m + n; i++) {
            if (nodes[i].getDegree() == maximumDegree && !nodes[i].isMatchingPoint) {
                freeNodes.add(i);
            }
        }
        while (find && !freeNodes.isEmpty()) {
            startBFS();
        }
        freeNodes = new LinkedList<>();
        for (int i = n; i < m + n; i++) {
            if (nodes[i].getDegree() > 0 && nodes[i].getDegree() < maximumDegree && !nodes[i].isMatchingPoint) {
                freeNodes.add(i);
            }
        }
        find = true;
        isMaximumNode = false;
        while (find && !freeNodes.isEmpty()) {
            startBFS();
        }
    }

    private void decreaseMatchingEdges() {
        for (Node node1 : nodes) {
            int numNode1 = node1.getNumber();
            for (int numNode2 : node1.adjacencyList) {
                Node node2 = nodes[numNode2];
                if (edgesCount[numNode1][numNode2] > 0) {
                    if (isMatchingEdge[numNode1][numNode2]) {
                        edgeCount--;
                        edgesCount[numNode1][numNode2]--;
                        isMatchingEdge[numNode1][numNode2] = false;
                        isMatchingEdge[numNode2][numNode1] = false;
                        node1.isMatchingPoint = false;
                        node2.isMatchingPoint = false;
                        node1.decrease();
                        node2.decrease();
                        print(numNode1, numNode2);
                    }
                    else{
                        flipEdge(node1, node2);
                    }
                }
            }
        }
    }

    public void find() {
        findMaximumDegree();
        t = maximumDegree;
        System.out.println(t);
        while (edgeCount != 0) {
            findMatching();
            System.out.println(matchingCount);
            decreaseMatchingEdges();
            findMaximumDegree();
        }
    }

    private void flipEdge(Node node1, Node node2) {
        int num1 = node1.getNumber();
        int num2 = node2.getNumber();
        int t = edgesCount[num1][num2];
        edgesCount[num1][num2] = edgesCount[num2][num1];
        edgesCount[num2][num1] = t;
    }

    private void flipAllEdges() {
        for (int numNode1 = 0; numNode1 < n; numNode1++) {
            Node node1 = nodes[numNode1];
            for (int numNode2 : node1.adjacencyList) {
                Node node2 = nodes[numNode2];
                flipEdge(node1, node2);
            }
        }
    }

    private void print(){
        for (int numNode1 = 0; numNode1 < n + m; numNode1++) {
            Node node1 = nodes[numNode1];
            for (int numNode2 : node1.adjacencyList) {
                Node node2 = nodes[numNode2];
                if(isMatchingEdge[numNode1][numNode2])
                    System.out.println(edgesCount[numNode1][numNode2] + " " + numNode1 + " " + numNode2 + " " + isMatchingEdge[numNode1][numNode2]);
            }
        }
    }

    private void print(int numNode1, int numNode2){
        System.out.println((numNode1 + 1) + " " + (numNode2 + 1 - n));
    }

}