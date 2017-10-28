package leetCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Your are given an array of integers prices, for which the i-th element is the price of a given stock on day i; and a non-negative integer fee representing a transaction fee.
 * <p>
 * You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction. You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share before you buy again.)
 * <p>
 * Return the maximum profit you can make.
 * <p>
 * Example 1:
 * Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
 * Output: 8
 * Explanation: The maximum profit can be achieved by:
 * Buying at prices[0] = 1
 * Selling at prices[3] = 8
 * Buying at prices[4] = 4
 * Selling at prices[5] = 9
 * The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 * Note:
 * <p>
 * 0 < prices.length <= 50000.
 * 0 < prices[i] < 50000.
 * 0 <= fee < 50000.ø
 */

public class StockTransaction {


    static class Solution {
        int maxProfit(int[] prices, int fee) {
            //股票的操作可以分为买入、卖出、什么都不做。
            //hold表示当前价格下，持有股票的总收益(cash-持有股票成本价)
            //cash表示卖出股票以后锁定的利润。
            int[] hold = new int[prices.length];
            int[] cash = new int[prices.length];
            //表示状态为持有股票的总利润
            hold[0] = -prices[0];
            //表示为目前手中现金总利润
            cash[0] = 0;
            for (int i = 1; i < prices.length; i++) {
                //当前价格下买入股票如果比上次收益高就持有，价格为现金利润-股票价格
                hold[i] = Math.max(cash[i - 1] - prices[i], hold[i - 1]);
                //计算现金利润时，需要用当前价格减掉股票成本和手续费(在没有利润的时候hold即为买入的最小成本，
                // 因为累计的利润并不会影响股票决策，所以可以直接加上hold)
                cash[i] = Math.max(prices[i] + hold[i - 1] - fee, cash[i - 1]);
            }
            return cash[prices.length - 1];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            int[] prices = stringToIntegerArray(line);
            line = in.readLine();
            int fee = Integer.parseInt(line);

            int ret = new Solution().maxProfit(prices, fee);

            String out = String.valueOf(ret);

            System.out.print(out);
        }
    }

    /**
     * 输入的字符串转为int数组
     */
    private static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }
        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }
}
