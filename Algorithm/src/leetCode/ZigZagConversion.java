package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
 * <p>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * @author tracy.
 * @create 2017-10-31 17:19
 **/
class SolutionZig {
    public String convert(String s, int numRows) {
        int length = s.length();
        if (length <= numRows || numRows == 1) {
            return s;
        }
        int cursor;
        char[] result = new char[length];
        int k = 0;
        for (int i = 0; i < numRows; i++) {
            if (k == length) {
                break;
            }
            int index = i;
            if (i % 2 == 0) {
                cursor = 4;
            } else {
                cursor = 2;
            }
            while (index < length) {
                result[k++] = s.charAt(index);
                index += cursor;
            }
        }
        return String.valueOf(result);
    }
}

public class ZigZagConversion {
    public static String stringToString(String input) {
        assert input.length() >= 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < input.length() - 1; i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '\\') {
                char nextChar = input.charAt(i + 1);
                switch (nextChar) {
                    case '\"':
                        sb.append('\"');
                        break;
                    case '/':
                        sb.append('/');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    default:
                        break;
                }
                i++;
            } else {
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            String s = stringToString(line);
            line = in.readLine();
            int numRows = Integer.parseInt(line);

            String ret = new SolutionZig().convert(s, numRows);

            String out = (ret);

            System.out.print(out);
        }
    }
}
