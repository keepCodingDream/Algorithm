package leetCode;

import java.util.*;

/**
 * 给定两个大小相等的数组 A 和 B，A 相对于 B 的优势可以用满足 A[i] > B[i] 的索引 i 的数目来描述。
 * <p>
 * 返回 A 的任意排列，使其相对于 B 的优势最大化。
 *
 * @author tracy.
 * @create 2018-07-18 17:10
 **/
public class AdvantageCount {
    public static void main(String[] args) {

        new AdvantageCountSolution().advantageCount(new int[]{2, 0, 4, 1, 2}, new int[]{1, 3, 0, 0, 2});
    }
}

class AdvantageCountSolution {
    public int[] advantageCount(int[] A, int[] B) {
        //保存B的原始数据
        int[] bCopy = B.clone();
        //先两个都排序，类似田忌赛马，以最小的去比最大的
        Arrays.sort(A);
        Arrays.sort(B);
        //开始田忌赛马，A[i]如果比B[i]小就将他与最小的调换，其余都进1
        for (int i = B.length - 1; i >= 0; i--) {
            if (A[i] <= B[i]) {
                int swap = A[0];
                for (int j = 0; j < i; j++) {
                    A[j] = A[j + 1];
                }
                A[i] = swap;
            }
        }
        Map<Integer, List<Integer>> positionB = new HashMap<>(B.length);
        Map<Integer, Integer> positionA = new HashMap<>(B.length);
        for (int i = 0; i < B.length; i++) {
            List<Integer> bPosition;
            if (!positionB.containsKey(B[i])) {
                bPosition = new ArrayList<>(4);
            } else {
                bPosition = positionB.get(B[i]);
            }
            bPosition.add(i);
            positionB.put(B[i], bPosition);
            positionA.put(i, A[i]);
        }
        int[] result = new int[B.length];
        int index = 0;
        Map<Integer, Integer> indexCache = new HashMap<>(B.length);
        for (int i = 0; i < B.length; i++) {
            List<Integer> listP = positionB.get(bCopy[i]);
            Integer cache = indexCache.get(bCopy[i]);
            int position;
            if (cache == null) {
                position = 0;
                indexCache.put(bCopy[i], 0);
            } else {
                position = cache + 1;
            }
            indexCache.put(bCopy[i], position);
            result[index++] = positionA.get(listP.get(position));
        }
        return result;
    }
}
