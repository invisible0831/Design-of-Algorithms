import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        double M, E;
        double[] equation1, equation2;
        n = scanner.nextInt();
        M = scanner.nextDouble();
        E = scanner.nextDouble();
        Simplex s = new Simplex(n, 2);
        equation1 = new double[n];
        equation2 = new double[n];
        double[] objective = new double[n];
        for(int i = 0; i < n; i++){
            objective[i] = -1;
            equation1[i] = -1 * scanner.nextDouble();
            equation2[i] = -1 * scanner.nextDouble();
        }
        s.addUpperBound(equation1, -1 * M);
        s.addUpperBound(equation2, -1 * E);
        s.setObjective(objective);
        double result = s.getMaximum() * -1;
        System.out.printf("%.2f", result);
    }
}
