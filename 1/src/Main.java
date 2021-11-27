import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static int[][] score, score1, score2;
    static char[][] previousMove, previousMove1, previousMove2;
    static String string1, string2;
    static int sigma, epsilon;
    static int[][] scoreMatrix;
    static Map<Character, Integer> characterMap = new HashMap<>();
    static void initialMatrix(){
        String[][] score = new String[20][];
        score[0] = "4  0 -2 -1 -2  0 -2 -1 -1 -1 -1 -2 -1 -1 -1  1  0  0 -3 -2".split("\\s+");
        score[1] = "0  9 -3 -4 -2 -3 -3 -1 -3 -1 -1 -3 -3 -3 -3 -1 -1 -1 -2 -2".split("\\s+");
        score[2] = "-2 -3  6  2 -3 -1 -1 -3 -1 -4 -3  1 -1  0 -2  0 -1 -3 -4 -3".split("\\s+");
        score[3] = "-1 -4  2  5 -3 -2  0 -3  1 -3 -2  0 -1  2  0  0 -1 -2 -3 -2".split("\\s+");
        score[4] = "-2 -2 -3 -3  6 -3 -1  0 -3  0  0 -3 -4 -3 -3 -2 -2 -1  1  3".split("\\s+");
        score[5] = "0 -3 -1 -2 -3  6 -2 -4 -2 -4 -3  0 -2 -2 -2  0 -2 -3 -2 -3".split("\\s+");
        score[6] = "-2 -3 -1  0 -1 -2  8 -3 -1 -3 -2  1 -2  0  0 -1 -2 -3 -2  2".split("\\s+");
        score[7] = "-1 -1 -3 -3  0 -4 -3  4 -3  2  1 -3 -3 -3 -3 -2 -1  3 -3 -1".split("\\s+");
        score[8] = "-1 -3 -1  1 -3 -2 -1 -3  5 -2 -1  0 -1  1  2  0 -1 -2 -3 -2".split("\\s+");
        score[9] = "-1 -1 -4 -3  0 -4 -3  2 -2  4  2 -3 -3 -2 -2 -2 -1  1 -2 -1".split("\\s+");
        score[10] = "-1 -1 -3 -2  0 -3 -2  1 -1  2  5 -2 -2  0 -1 -1 -1  1 -1 -1".split("\\s+");
        score[11] = "-2 -3  1  0 -3  0  1 -3  0 -3 -2  6 -2  0  0  1  0 -3 -4 -2".split("\\s+");
        score[12] = "-1 -3 -1 -1 -4 -2 -2 -3 -1 -3 -2 -2  7 -1 -2 -1 -1 -2 -4 -3".split("\\s+");
        score[13] = "-1 -3  0  2 -3 -2  0 -3  1 -2  0  0 -1  5  1  0 -1 -2 -2 -1".split("\\s+");
        score[14] = "-1 -3 -2  0 -3 -2  0 -3  2 -2 -1  0 -2  1  5 -1 -1 -3 -3 -2".split("\\s+");
        score[15] = "1 -1  0  0 -2  0 -1 -2  0 -2 -1  1 -1  0 -1  4  1 -2 -3 -2".split("\\s+");
        score[16] = "0 -1 -1 -1 -2 -2 -2 -1 -1 -1 -1  0 -1 -1 -1  1  5  0 -2 -2".split("\\s+");
        score[17] = "0 -1 -3 -2 -1 -3 -3  3 -2  1  1 -3 -2 -2 -3 -2  0  4 -3 -1".split("\\s+");
        score[18] = "-3 -2 -4 -3  1 -2 -2 -3 -3 -2 -1 -4 -4 -2 -3 -3 -2 -3 11  2".split("\\s+");
        score[19] = "-2 -2 -3 -2  3 -3  2 -1 -2 -1 -1 -2 -3 -1 -2 -2 -2 -1  2  7".split("\\s+");
        scoreMatrix = new int[20][20];
        for (int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                scoreMatrix[i][j] = Integer.parseInt(score[i][j]);
            }
        }
    }
    static void initialMap(){
        String[] characters = "A  C  D  E  F  G  H  I  K  L  M  N  P  Q  R  S  T  V  W  Y".split("\\s+");
        for (int i = 0; i < 20; i++) {
            characterMap.put(characters[i].charAt(0), i);
        }
    }
    static int getScore(char c1, char c2){
        return scoreMatrix[characterMap.get(c1)][characterMap.get(c2)];
    }
    static void scan(Scanner scanner){
        string1 = scanner.nextLine();
        string2 = scanner.nextLine();
        sigma = scanner.nextInt();
        epsilon = scanner.nextInt();
    }
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        scan(scanner);
        initialMatrix();
        initialMap();
        System.out.println(findScore());
        printMatchedStrings();
    }
    static int findScore(){
        int m = string1.length();
        int n = string2.length();
        score = new int[m + 1][n + 1];
        score1 = new int[m + 1][n + 1];
        score2 = new int[m + 1][n + 1];
        previousMove = new char[m + 1][n + 1];
        previousMove1 = new char[m + 1][n + 1];
        previousMove2 = new char[m + 1][n + 1];
        for (int i = 1; i <= m; i++){
            score[i][0] = -sigma - (i - 1) * epsilon;
            score1[i][0] = -sigma - (i - 1) * epsilon;
            score2[i][0] = -sigma - (i - 1) * epsilon;
            previousMove[i][0] = 'D';
            previousMove1[i][0] = 'D';
        }
        previousMove1[1][0] = 'C';
        for (int j = 1; j <= n; j++){
            score[0][j] = -sigma - (j - 1) * epsilon;
            score1[0][j] = -sigma - (j - 1) * epsilon;
            score2[0][j] = -sigma - (j - 1) * epsilon;
            previousMove[0][j] = 'R';
            previousMove2[0][j] = 'R';
        }
        previousMove2[0][1] = 'C';
        for (int i = 1; i <= m; i++){
            for (int j = 1; j <= n; j++){
                int t = score[i - 1][j - 1] + getScore(string1.charAt(i - 1), string2.charAt(j - 1));
                if(score1[i - 1][j] - epsilon >= score[i - 1][j] - sigma){
                    score1[i][j] = score1[i - 1][j] - epsilon;  //امتیاز i حرف اول string1 با j حرف اول string2 که حرف i عه String1 با اسپیس مچ شده باشد :score1[i][j]
                    previousMove1[i][j] = 'D';  //مشخص می‌کند که حرف i-1 نیز با خط فاصله مچ شده
                }
                else {
                    score1[i][j] = score[i - 1][j] - sigma;
                    previousMove1[i][j] = 'C';
                }
                if(score2[i][j - 1] - epsilon >= score[i][j - 1] - sigma){
                    score2[i][j] = score2[i][j - 1] - epsilon;
                    previousMove2[i][j] = 'R';  // مشخص می‌کند که حرف j-1 نیز با خط فاصله مچ شده
                }
                else {
                    score2[i][j] = score[i][j - 1] - sigma; //امتیاز i حرف اول string1 با j حرف اول string2 که حرف j عه String2 با اسپیس مچ شده باشد :score2[i][j]
                    previousMove2[i][j] = 'C';
                }
                if(t >= score1[i][j] && t >= score2[i][j]){
                    score[i][j] = t;
                    previousMove[i][j] = 'C';
                }
                else if(score1[i][j] >= t && score1[i][j] >= score2[i][j]){
                    score[i][j] = score1[i][j];
                    previousMove[i][j] = 'D';
                }
                else if(score2[i][j] >= t && score2[i][j] >= score1[i][j]){
                    score[i][j] = score2[i][j];
                    previousMove[i][j] = 'R';
                }
            }
        }
        return score[m][n];
    }
    static void printMatchedStrings(){
        int i = string1.length();
        int j = string2.length();
        String matchedString1 = "";
        String matchedString2 = "";
        while(i > 0 || j > 0){
            if(previousMove[i][j] == 'D'){
                matchedString1 = string1.charAt(i - 1) + matchedString1;
                matchedString2 = "-" + matchedString2;
                while (previousMove1[i][j] == 'D' && i > 1){
                    matchedString1 = string1.charAt(i - 1) + matchedString1;
                    matchedString2 = "-" + matchedString2;
                    i--;
                }
                i--;
            }
            else if(previousMove[i][j] == 'R'){
                matchedString1 = "-" + matchedString1;
                matchedString2 = string2.charAt(j - 1) + matchedString2;
                while (previousMove2[i][j] == 'R' && j > 1){
                    matchedString1 = "-" + matchedString1;
                    matchedString2 = string2.charAt(j - 1) + matchedString2;
                    j--;
                }
                j--;
            }
            else {
                matchedString1 = string1.charAt(i - 1) + matchedString1;
                matchedString2 = string2.charAt(j - 1) + matchedString2;
                i--;
                j--;
            }
        }
        System.out.println(matchedString1);
        System.out.println(matchedString2);

    }
}
