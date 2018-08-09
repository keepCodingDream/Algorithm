package leetCode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个整数数组 nums ，找出一个序列中乘积最大的连续子序列（该序列至少包含一个数）。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 * 示例 2:
 * <p>
 * 输入: [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * @author tracy.
 * @create 2018-08-03 17:39
 **/
public class MaxProduct {
    public int maxProduct(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        boolean containsZ = false;
        //0是硬分割，必须切开单独计算
        List<List<Integer>> subList = new ArrayList<>(10);
        Integer itemSum = null;
        List<Integer> item = new ArrayList<>();
        for (int num : nums) {
            if (num == 0) {
                containsZ = true;
                if (itemSum != null) {
                    item.add(itemSum);
                    itemSum = null;
                }
                if (item.size() > 0) {
                    subList.add(item);
                    item = new ArrayList<>();
                }
            } else if (num > 0) {
                if (itemSum == null) {
                    itemSum = 1;
                }
                itemSum *= num;
            } else {
                if (itemSum != null) {
                    item.add(itemSum);
                }
                item.add(num);
                itemSum = null;
            }
        }
        if (item.size() > 0) {
            if (itemSum != null) {
                item.add(itemSum);
            }
            subList.add(item);
            itemSum = null;
        }
        if (itemSum != null) {
            item.add(itemSum);
            subList.add(item);
        }
        //开始单独计算每个字串的大小，取最大返回
        int[] subTotal = new int[subList.size()];
        int index = 0;
        for (List<Integer> list : subList) {
            //负数的个数
            int lowCount = 0;
            itemSum = 1;
            for (int itemL : list) {
                if (itemL < 0) {
                    lowCount++;
                }
                itemSum *= itemL;
            }
            if (lowCount % 2 == 0) {
                subTotal[index++] = itemSum;
            } else {
                if (list.size() == 1) {
                    subTotal[index++] = itemSum;
                } else {
                    Integer leftLow = null;
                    int rightLow = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) < 0) {
                            if (leftLow == null) {
                                leftLow = i;
                            }
                            rightLow = i;
                        }
                    }
                    Integer leftVal;
                    if (leftLow != null && leftLow > 0) {
                        leftVal = list.get(leftLow - 1) * list.get(leftLow);
                    } else {
                        leftVal = list.get(leftLow);
                    }
                    Integer rightVal;
                    if (rightLow < list.size() - 1) {
                        rightVal = list.get(rightLow + 1) * list.get(rightLow);
                    } else {
                        rightVal = list.get(rightLow);
                    }
                    int leftT = itemSum / leftVal;
                    int rightT = itemSum / rightVal;
                    if (leftT > rightT) {
                        subTotal[index++] = leftT;
                    } else {
                        subTotal[index++] = rightT;
                    }
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for (int tt : subTotal) {
            if (tt > max) {
                max = tt;
            }
        }
        if (max < 0 && containsZ) {
            max = 0;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new MaxProduct().maxProduct(new int[]{0,-2,0}));
    }
}
