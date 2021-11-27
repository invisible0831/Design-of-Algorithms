public class Plane {
    private Point[] points;
    private int leftmost;   //index of leftmost point in points array
    public Plane(int n){
        points = new Point[n];
    }
    public void setPoints(long x, long y, int i){
        this.points[i] = new Point(x, y);
    }
    private void initLeftmost(){    //sequential search in points to find leftmost point and initial it
        int index = 0;
        for(int i = 1; i < points.length; i++){
            if(points[i].x < points[index].x){
                index = i;
            }
            else if((points[i].x == points[index].x) && (points[i].y > points[index].y)){
                index = i;
            }
        }
        leftmost = index;
    }
    public void findConvexHull(){    //if point p in the convexHull, p.pointer.next = index of next point of p in convexHull and p.pointer.prev = index of previous point of p in convexHull
        initLeftmost();
        Point point2 = points[leftmost];
        Point point1 = new Point(point2.x, point2.y - 1);
        Point point3 = point1;
        while(!point3.equals(points[leftmost])){    //index of leftmost point = low, because points array is sorted
            double maxAngle = 0;
            for(int i = 0; i < points.length; i++){
                double angle = findAngle(point1, point2, points[i]);  //angle of point1 and point2 and points[i]
                if((angle <= 180) && (maxAngle < angle)){
                    maxAngle = angle;
                    point3 = points[i];
                }
            }
            point2.pointer.next = point3;
            point3.pointer.prev = point2;
            point1 = point2;
            point2 = point3;
        }
    }
    private void findConvexHull(int low, int high){    //if point p in the convexHull, p.pointer.next = index of next point of p in convexHull and p.pointer.prev = index of previous point of p in convexHull
        Point point2 = points[low];
        Point point1 = new Point(point2.x, point2.y - 1);
        Point point3 = point1;
        while(!point3.equals(points[low])){    //index of leftmost point = low, because points array is sorted
            double maxAngle = 0;
            for(int i = low; i <= high; i++){
                double angle = findAngle(point1, point2, points[i]);  //angle of point1 and point2 and points[i]
                if((angle <= 180) && (maxAngle <= angle)){
                    maxAngle = angle;
                    point3 = points[i];
                }
            }
            point2.pointer.next = point3;
            point3.pointer.prev = point2;
            point1 = point2;
            point2 = point3;
        }
    }
    private static double findAngle(Point point1, Point point2, Point point3){   // origin = point2
        long x1, y1, x2, y2;
        x1 = point1.x - point2.x;
        y1 = point1.y - point2.y;
        x2 = point3.x - point2.x;
        y2 = point3.y - point2.y;
        double angle = Math.atan2(x1 * y2 - y1 * x2, x1 * x2 + y1 * y2);
        angle = (angle >= 0 ? angle : (2 * Math.PI + angle)) * 360 / (2 * Math.PI);
        return angle;
    }
    public Point[] getPoints() {
        return points;
    }
    public int getLeftmost() {
        return leftmost;
    }
    private void mergeSort(int low, int high){
        if(low == high){
            return;
        }
        int mid = (low + high) / 2;
        mergeSort(low, mid);
        mergeSort(mid + 1, high);
        merge(low, mid, high);
    }
    private void merge(int low, int mid, int high){    //merge two sorted arrays
        Point[] points1 = new Point[mid - low + 2];
        for(int i = low; i <= mid; i++){
            points1[i - low] = points[i];
        }
        points1[mid - low + 1] = new Point(Integer.MAX_VALUE, Integer.MIN_VALUE);
        Point[] points2 = new Point[high - mid + 1];
        for(int i = mid + 1; i <= high; i++){
            points2[i - mid - 1] = points[i];
        }
        points2[high - mid] = new Point(Integer.MAX_VALUE, Integer.MIN_VALUE);
        int pointer1 = 0;
        int pointer2 = 0;
        for(int i = low; i <= high; i++){
            if(points1[pointer1].x < points2[pointer2].x){
                points[i] = points1[pointer1];
                pointer1++;
            }
            else if(points1[pointer1].x > points2[pointer2].x){
                points[i] = points2[pointer2];
                pointer2++;
            }
            else {
                if(points1[pointer1].y < points2[pointer2].y){
                    points[i] = points2[pointer2];
                    pointer2++;
                }
                else if(points1[pointer1].y > points2[pointer2].y){
                    points[i] = points1[pointer1];
                    pointer1++;
                }
                else {
                    points[i] = points1[pointer1];
                    pointer1++;
                }
            }
        }
    }
    private void mergeConvexHull(int mid){
        Point up1 = points[mid];    //point of upper tangent in convex hull 1
        Point up2 = points[mid + 1];    //point of upper tangent in convex hull 2
        boolean check = false;    //if up1 and up2 are correct then check = true
        while(!check){
            check = true;
            if((!isTangent(up1, up2, up1.pointer.next)) || (!isTangent(up1, up2, up1.pointer.prev))){
                up1 = up1.pointer.prev;
                check = false;
            }
            else if((!isTangent(up1, up2, up2.pointer.next)) || (!isTangent(up1, up2, up2.pointer.prev))){
                up2 = up2.pointer.next;
                check = false;
            }
        }
        Point down1 = points[mid];    //point of lower tangent in convex hull 1
        Point down2 = points[mid + 1];    //point of lower tangent in convex hull 2
        check = false;    //if down1 and down2 are correct then check = true
        while(!check){
            check = true;
            if((!isTangent(down2, down1, down1.pointer.next)) || (!isTangent(down2, down1, down1.pointer.prev))){
                down1 = down1.pointer.next;
                check = false;
            }
            else if((!isTangent(down2, down1, down2.pointer.next)) || (!isTangent(down2, down1, down2.pointer.prev))){
                down2 = down2.pointer.prev;
                check = false;
            }
        }
        up1.pointer.next = up2;
        up2.pointer.prev = up1;
        down2.pointer.next = down1;
        down1.pointer.prev = down2;
    }
    private static boolean isTangent(Point point1, Point point2, Point point){
        if(point1.x == point2.x){
            if((point1.y > point2.y && point.x <= point1.x) || (point1.y < point2.y && point.x >= point1.x)){
                return true;
            }
            return false;
        }
        double a = (double)(point1.y - point2.y) / (point1.x - point2.x);   //y = ax + b
        double b = point1.y - a * point1.x;
        if(((point1.x <= point2.x) && (point.y <= a * point.x + b)) || ((point1.x >= point2.x) && (point.y >= a * point.x + b))){
            return true;
        }
        return false;
    }
    private void findConvexHull2(int low, int high){
        if(high - low < 9){
            findConvexHull(low, high);
            return;
        }
        int mid = (low + high) / 2;
        findConvexHull2(low, mid);
        findConvexHull2(mid + 1, high);
        mergeConvexHull(mid);
    }
    public void findConvexHull2(){
        mergeSort(0, points.length - 1);
        findConvexHull2(0, points.length - 1);
    }
    private static Double distTwoPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }
    private static Double distFromLine(Point point1, Point point2, Point target) {
        long p = point2.y - point1.y;
        long q = point1.x - point2.x;
        long r = point2.x * point1.y - point1.x * point2.y;
        return Math.abs(p * target.x + q * target.y + r) / Math.sqrt(Math.pow(p, 2) + Math.pow(q, 2));
    }
    private Point maxDistPoint(Point a, Point b){   //return the point that has maximum distance from ab
        Point c = b;
        boolean isMax = false;
        while (!isMax){
            c = c.pointer.next;
            isMax = (distFromLine(a, b, c) >= distFromLine(a, b, c.pointer.next)) && (distFromLine(a, b, c.pointer.prev) <= distFromLine(a, b, c));
        }
        return c;
    }
    private Point choosePoint(Point a, Point b, Point c){   //choose the point between a and b that has more distance to c
        if(distTwoPoints(a, c) > distTwoPoints(b, c)){
            return a;
        }
        return b;
    }
    public void findMaxDist(){
        findConvexHull2();
        double maxDist = 0;
        Point a = points[0];
        Point b = null;
        Point c;
        Point maxDistPoint1 = null;
        Point maxDistPoint2 = null;
        while ((!a.equals(points[0])) || (b == null)){
            b = a.pointer.next;
            c = maxDistPoint(a, b);
            if(distTwoPoints(choosePoint(a, b, c), c) > maxDist){
                maxDist = distTwoPoints(choosePoint(a, b, c), c);
                maxDistPoint1 = choosePoint(a, b, c);
                maxDistPoint2 = c;
            }
            a = b;
        }
        printMaxDistPoints(maxDistPoint1, maxDistPoint2);
    }
    private void printMaxDistPoints(Point maxDistPoint1, Point maxDistPoint2){
        if((maxDistPoint1.x > maxDistPoint2.x) || (maxDistPoint1.x == maxDistPoint2.x && maxDistPoint1.y > maxDistPoint2.y)){
            Point t = maxDistPoint1;
            maxDistPoint1 = maxDistPoint2;
            maxDistPoint2 = t;
        }
        maxDistPoint1.print();
        maxDistPoint2.print();
    }
}
