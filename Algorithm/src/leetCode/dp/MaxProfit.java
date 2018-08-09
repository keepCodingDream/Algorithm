package leetCode.dp;

/**
 * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
 * <p>
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * <p>
 * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 示例:
 * <p>
 * 输入: [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 *
 * @author tracy.
 * @create 2018-08-06 16:34
 **/
public class MaxProfit {

    /**
     * 根据动态规划的原理，如果我能保证sell[i-1]是i-1天卖出的最大利润，那么我只要确保 sell[]
     * @param prices 价格数组
     * @return 最大利润
     */
    public static int maxProfit(int[] prices) {
        //第i天是买入的最大收益
        int buy[] = new int[prices.length];
        //第i天是卖出的最大收益
        int sell[] = new int[prices.length];

        buy[0] = -prices[0];
        sell[0] = 0;

        for (int i = 1; i < prices.length; i++) {
            int delta = prices[i] - prices[i - 1];
            // 第i-1天买入，第i天卖出；前一天不卖，今天卖
            sell[i] = Math.max(buy[i - 1] + prices[i], sell[i - 1] + delta);
            if (i > 2) {
                //第i-2天卖出，今天买入的最大收益；昨天不买、今天买
                buy[i] = Math.max(sell[i - 2] - prices[i], buy[i - 1] - delta);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1, 2, 3, 0, 2}));
    }
}
