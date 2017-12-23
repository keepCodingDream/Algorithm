package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * <p>
 * Example:
 * <p>
 * Input: "babad"
 * <p>
 * Output: "bab"
 * <p>
 * Note: "aba" is also a valid answer.
 * Example:
 * <p>
 * Input: "cbbd"
 * <p>
 * Output: "bb"
 *
 * @author tracy.
 * @create 2017-10-30 20:37
 **/
class SolutionL {
    /**
     * https://www.felix021.com/blog/read.php?2040
     * 主要利用回文字符串的对称性，对已知的子回文串不再计算。对未知的(i>rightMargin)则循环比对
     *
     * @param s 入参字符串
     * @return 最长回文字符串
     */
    String longestPalindrome(String s) {
        int[] p = new int[s.length()];
        //已知最长回文的又边界
        int rightMargin = 0;
        //已知最长回文的中心
        int longestMid = 0;
        for (int i = 0; i < s.length(); i++) {
            p[i] = rightMargin > i ? Math.min(p[2 * longestMid - i] + 1, rightMargin - i + 1) : 1;
            while ((i + p[i]) < s.length() && (i - p[i]) > -1 && s.charAt(i + p[i]) == s.charAt(i - p[i])) {
                //如果是回文,一直累加
                p[i]++;
            }
            //新回文>老回文长度，替换
            if (i + p[i] > rightMargin) {
                rightMargin = i + p[i];
                longestMid = i;
            }
        }
        int max = -1;
        for (int i = 0; i < s.length(); i++) {
            if (p[i] > max) {
                max = p[i];
            }
        }
        if (max == -1) {
            if (s.length() == 0) {
                return "";
            } else {
                return String.valueOf(s.charAt(0));
            }
        } else {
            return "";
        }
    }

    private boolean judgePalindromic(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}

public class LongestPalindromic {
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
            String ret = new SolutionL().longestPalindrome(s);
            System.out.print(ret);
        }
    }
}