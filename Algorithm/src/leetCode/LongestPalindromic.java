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
    String longestPalindrome(String s) {
        String result = "";
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (length - i < result.length()) {
                //如果剩下的已经不足以result长度直接退出
                break;
            }
            //从最后一个字母往前遍历，这样先遍历的到的就是最长的回文
            int endIndex = length - 1;
            int flag = 0;
            while (endIndex > i) {
                for (int j = endIndex; j > i; j--) {
                    if (j - i < result.length()) {
                        break;
                    }
                    if (s.charAt(j) == s.charAt(i)) {
                        String subString = s.substring(i, j + 1);
                        if (judgePalindromic(subString)) {
                            if (subString.length() > result.length()) {
                                result = subString;
                            }
                            flag = 1;
                            //同一个起点，最先遍历到的就是最长的回文
                            break;
                        }
                    }
                }
                if (flag == 1) {
                    break;
                }
                endIndex--;
            }
        }
        if ("".equals(result) && s.length() > 0) {
            result = String.valueOf(s.charAt(0));
        }
        return result;
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