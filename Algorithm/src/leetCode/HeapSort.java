package leetCode;

import java.util.ArrayList;

/**
 * 堆排序
 *
 * @author tracy.
 * @create 2018-02-12 10:14
 **/
public class HeapSort {
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        int length = input.length;
        //第一个循环,是遍历k次，一次遍历一个最小的。
        ArrayList<Integer> result = new ArrayList<>(k);
        if (k > input.length) {
            return result;
        }
        for (int i = 0; i < k; i++) {
            for (int j = length - i - 1; j > 0; j--) {
                int father = j / 2;
                int judge = j % 2;
                if (judge == 0) {
                    //右孩子节点，求父节点需要减一
                    father--;
                }
                if (father >= 0) {
                    if (input[j] < input[father]) {
                        input[j] = input[j] ^ input[father];
                        input[father] = input[j] ^ input[father];
                        input[j] = input[j] ^ input[father];
                    }
                }
            }
            result.add(input[0]);
            input[0] = input[length - i - 1] ^ input[0];
            input[length - i - 1] = input[length - i - 1] ^ input[0];
            input[0] = input[length - i - 1] ^ input[0];
        }
        return result;
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        System.out.print(heapSort.GetLeastNumbers_Solution(new int[]{4, 5, 1, 6, 2, 7, 3, 8}, 4).toString());
    }
}
