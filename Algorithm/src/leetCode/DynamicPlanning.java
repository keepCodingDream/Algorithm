package leetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same, where in each step you can delete one character in either string.
 * <p>
 * Example 1:
 * Input: "sea", "eat"
 * Output: 2
 * Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
 * Note:
 * The length of given words won't exceed 500.
 * Characters in given words can only be lower-case letters.
 *
 * @author tracy.
 * @create 2018-02-01 21:08
 **/
class SolutionMinDistance {
    /**
     * 该题表面求删除多少次(一次只能删除一个)才能让两个字符串相等。
     * 实际还是求两个字符串的公共字符串个数。(不一定连续)
     * 当i ==0 || j ==0: dp(i,j)=0;
     * <p>
     * 当word1[i]!=word2[j]: dp(i,j)=max(dp(i-1,j),dp(i,j-1))——相当于将二维表(i,j)之前最长的记录保存下来。(这里不处理就是连续子串)
     * <p>
     * 当word2[i]=word2[j]: dp(i,j)=dp(i-1,j-1)+1;——相当于又找到一个更长的，加1
     * <p>
     * 我们只要保证dp[i][j] 保存的是work1前i个字符、work2前j个字符，最长串是值就行。
     * 这样，只要两个数组遍历完了，最长的也就知道了。
     *
     * @param word1
     * @param word2
     * @return
     */
    int minDistance(String word1, String word2) {
        //利用动态规划的思想，map[i][j]保存word1前i个字符、work2前j个字符最长记录。
        int map[][] = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    map[i][j] = map[i - 1][j - 1] + 1;
                } else {
                    map[i][j] = Math.max(map[i - 1][j], map[i][j - 1]);
                }
            }
        }
        int commonLength = map[word1.length()][word2.length()];
        return word1.length() + word2.length() - commonLength * 2;
    }
}

public class DynamicPlanning {
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
            String word1 = stringToString(line);
            line = in.readLine();
            String word2 = stringToString(line);
            int ret = new SolutionMinDistance().minDistance(word1, word2);
            String out = String.valueOf(ret);
            System.out.print(out);
        }
    }
}