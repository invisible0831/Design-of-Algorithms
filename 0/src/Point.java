public class Point {
    long x, y;
    Pointer pointer;
    public Point(long x, long y){
        this.x = x;
        this.y = y;
        this.pointer = new Pointer();
    }
    public boolean equals(Point point) {
        if(this.x == point.x && this.y == point.y){
            return true;
        }
        return false;
    }
    public void print(){
        System.out.println(x + " " + y);
    }
}