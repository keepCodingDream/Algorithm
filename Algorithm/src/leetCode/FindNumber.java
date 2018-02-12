package leetCode;

/**
 * @author tracy.
 * @create 2018-02-11 21:29
 **/
public class FindNumber {
    public static boolean find(int target, int[][] array) {
        //求出数组的第一个维度长度
        int firstNum = array.length;
        //求出数字的第二个维度的长度
        int secondNum = array[0].length;
        if (firstNum <= 0 || secondNum <= 0) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < firstNum; i++) {
            if (array[i][0] <= target) {
                result = halfFind(array[i], 0, secondNum - 1, target);
                if (result != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int halfFind(int a[], int start, int end, int target) {
        if (end < start) {
            return -1;
        }
        int mid = (end + start) / 2;
        if (a[mid] == target) {
            return mid;
        }
        if (a[mid] > target) {
            return halfFind(a, start, mid - 1, target);
        } else {
            return halfFind(a, mid + 1, end, target);
        }
    }

    public static void main(String[] args) {
        int[][] a = new int[][]{{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        System.out.println(find(22, a));
    }
}
