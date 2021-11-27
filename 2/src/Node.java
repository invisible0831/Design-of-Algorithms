import java.util.LinkedList;
import java.util.List;

public class Node {
    boolean isMatchingPoint = false;
    boolean mark = false;
    int dist;
    Node father, grandFather;
    boolean isSelected;
    List<Integer> adjacencyList;
    private int number, degree = 0;

    public Node(int number) {
        this.number = number;
        adjacencyList = new LinkedList<>();
    }

    public int getDegree() {
        return degree;
    }

    public int getNumber() {
        return number;
    }

    public void addAdjacencyList(int number) {
        adjacencyList.add(number);
    }

    public void increase(){
        degree++;
    }

    public void decrease() {
        degree--;
    }
}