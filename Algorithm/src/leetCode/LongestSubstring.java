package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * kmp算法。
 * 使用两个坐标去平行移动。
 * @author tracy.
 * @create 2017-10-30 15:21
 **/
class Solution2 {
    int lengthOfLongestSubstring(String s) {
        int max = 1;
        if ("".equals(s) || s == null) {
            return 0;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(s.charAt(0));
        int endIndex = 1;
        while (endIndex < s.length()) {
            int isRepeat = checkRepeatable(builder.toString(), s.charAt(endIndex));
            if (isRepeat == -1) {
                builder.append(s.charAt(endIndex));
                if (builder.length() > max) {
                    max = builder.length();
                }
            } else {
                //发现重复，就将重复的之前全部抹去，保留重复后面的
                builder.replace(0, isRepeat + 1, "");
                builder.append(s.charAt(endIndex));
            }
            endIndex++;
        }
        return max;
    }

    /**
     * return repeat index.
     * -1 if not find
     *
     * @param s target
     * @param a char
     */
    private int checkRepeatable(String s, char a) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == a) {
                return i;
            }
        }
        return -1;
    }
}

public class LongestSubstring {
    private static String stringToString(String input) {
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

            int ret = new Solution2().lengthOfLongestSubstring(s);

            String out = String.valueOf(ret);

            System.out.print(out);
        }
    }
}