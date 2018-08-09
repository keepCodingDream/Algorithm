package leetCode;

/**
 * 给定一个正整数 N，找到并返回 N 的二进制表示中两个连续的 1 之间的最长距离。
 * <p>
 * 如果没有两个连续的 1，返回 0 。
 *
 * @author tracy.
 * @create 2018-07-18 16:02
 **/
public class BinaryGap {
    public static void main(String[] args) {
        System.out.println(new SolutionB().binaryGap(8));
    }
}

class SolutionB {
    public int binaryGap(int n) {
        String string = Integer.toBinaryString(n);
        int max = 0;
        int i = 0;
        boolean flag = false;
        while (i < string.length() - 1) {
            int count = 0;
            if (string.charAt(i) == '1') {
                for (int j = i + 1; j < string.length(); j++) {
                    count++;
                    if (string.charAt(j) == '1') {
                        i = j;
                        if (count > max) {
                            max = count;
                        }
                        break;
                    }
                    if (j == string.length() - 1) {
                        flag = true;
                    }
                }
            } else {
                i++;
            }
            if (flag) {
                break;
            }
        }
        return max;
    }
}