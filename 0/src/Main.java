import java.util.Scanner;

public class Main {
    static Plane plane;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        scan(scanner);
        //plane.findConvexHull();
        //print(plane.getLeftmost());
        //plane.findConvexHull2();
        //print(0);
        plane.findMaxDist();
    }
    private static void scan(Scanner scanner){    //initial n and points
        int n = scanner.nextInt();
        plane = new Plane(n);
        for (int i = 0; i < n; i++) {
            long x = scanner.nextLong();
            long y = scanner.nextLong();
            plane.setPoints(x, y, i);
        }
    }
    private static void print(int firstIndex){    //get convex hull and index of leftmost point in points array and print convex hull points
        Point leftmostPoint = plane.getPoints()[firstIndex];
        leftmostPoint.print();
        Point point = leftmostPoint.pointer.next;
        while (point != leftmostPoint){
            point.print();
            point = point.pointer.next;
        }
    }
}
