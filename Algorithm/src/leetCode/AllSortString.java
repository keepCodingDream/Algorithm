package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 字符串的全排列
 *
 * @author tracy.
 * @create 2018-02-10 21:23
 **/
public class AllSortString {
    //实现ABC---> ABC、ACB、CBA、CAB、BAC、BCA

    public static void doPrint(char[] values, int currentIndex) {
        if (currentIndex == values.length - 1) {
            System.out.println(String.valueOf(values));
        } else {
            for (int i = currentIndex; i < values.length; i++) {
                swap(values, i, currentIndex);
                doPrint(values, currentIndex + 1);
                swap(values, i, currentIndex);
            }
        }
    }

    public static void swap(char values[], int a, int b) {
        char pp = values[a];
        values[a] = values[b];
        values[b] = pp;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            doPrint(line.toCharArray(), 0);
        }
    }
}
