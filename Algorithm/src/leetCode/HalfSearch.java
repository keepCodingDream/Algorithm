package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author tracy.
 * @create 2018-02-10 21:58
 **/
public class HalfSearch {

    //end is included
    static int doPrint(int[] line, int latest, int start, int end) {
        if (end > start) {
            int mid = (end - start) / 2;
            if (line[mid] == latest) {
                return mid;
            } else if (line[mid] < latest) {
                return doPrint(line, latest, mid + 1, end);
            } else {
                return doPrint(line, latest, start, mid - 1);
            }
        } else if (end == start) {
            return line[end] == latest ? end : -1;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            String[] a = line.split(" ");
            int aa[] = new int[a.length];
            int index = 0;
            for (String item : a) {
                aa[index++] = Integer.valueOf(item);
            }
            String latest = a[a.length - 1];
            System.out.println(doPrint(aa, Integer.valueOf(latest), 0, a.length - 2));
        }
    }
}
