import java.util.LinkedList;
import java.util.List;

public class Node {
    int number;
    private Node next;
    boolean isInternal;
    public List<Integer> adjacencyList;
    public Node(int number){
        this.number = number;
        isInternal = true;
        adjacencyList = new LinkedList<>();
    }
    public void setNext(Node next) {
        this.next = next;
    }
    public Node getNext() {
        return next;
    }
}
