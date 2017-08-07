package baidu;

import java.util.Scanner;

/**
 * 百度之星大赛第4题
 *
 * @author tracy.
 * @create 2017-08-06 00:27
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int no = sc.nextInt();
        for (int count = 1; count <= no; count++) {
            int maxMoney = sc.nextInt();
            int nu_of_cookie = sc.nextInt();
            int cookie[][] = new int[nu_of_cookie + 1][2];
            sc.nextLine();
            for (int i = 1; i <= nu_of_cookie; i++) {
                String value = sc.nextLine();
                String[] MN = value.split("\\s+");
                cookie[i][0] = Integer.valueOf(MN[0]);//score
                cookie[i][1] = Integer.valueOf(MN[1]);//cost
            }
            if (maxMoney > 0) {
                //F[i][v]=max(F[i-1][v],F[i-1][v-c[i]]+w[i])
                //v=预算,c[i]=第i个菜的价格,w[i]=第i个菜的得分
                int function[][] = new int[nu_of_cookie + 1][maxMoney + 1];
                //代表第i物品选择时，背包里的物品.index_1=i个物品;index_2=x容量;index_3=[1,2,3]背包里的物品
                String[][] path = new String[nu_of_cookie + 1][maxMoney + 1];
                for (int i = 1; i <= nu_of_cookie; i++) {
                    for (int j = 1; j <= maxMoney; j++) {
                        if (i == 1) {
                            if (cookie[i][1] > j) {
                                function[i][j] = 0;
                            } else {
                                function[i][j] = cookie[i][0];
                                path[i][j] = i + " ";
                            }
                        } else {
                            function[i][j] = function[i - 1][j];
                            path[i][j] = path[i - 1][j];
                            if (path[i][j] == null) {
                                path[i][j] = "";
                            }
                            if (j - cookie[i][1] >= 0) {
                                function[i][j] = Math.max(function[i - 1][j], function[i - 1][j - cookie[i][1]] + cookie[i][0]);
                                if (function[i][j] > function[i - 1][j]) {
                                    path[i][j] = path[i - 1][j - cookie[i][1]];
                                    if (path[i][j] == null) {
                                        path[i][j] = "";
                                    }
                                    path[i][j] += i + " ";
                                } else {
                                    //除了大，就是等于，所以这里需要判断字典
                                    String newOrder = path[i - 1][j - cookie[i][1]];
                                    if (newOrder == null) {
                                        newOrder = "";
                                    }
                                    newOrder += i + " ";
                                    if (judgeDirect(path[i][j], newOrder)) {
                                        path[i][j] = newOrder;
                                    }
                                }
                            }
                        }
                    }
                }
                // 数组右下角就是最大值
                int maxValue = function[nu_of_cookie][maxMoney];
                String final_order = null;
                for (int i = 1; i <= nu_of_cookie; i++) {
                    for (int j = 1; j <= maxMoney; j++) {
                        if (function[i][j] == maxValue) {
                            String order = path[i][j];
                            if (final_order == null || "".equals(final_order)) {
                                final_order = order.trim();
                            } else if (judgeDirect(final_order, order)) {
                                final_order = order;
                            }
                        }
                    }
                }
                System.out.println("Case #" + count + ":");
                int total_value = 0;
                if (final_order != null && !"".equals(final_order)) {
                    String[] orderInt = final_order.trim().split("\\s+");
                    for (String item : orderInt) {
                        if (item != null && !"".equals(item)) {
                            total_value += cookie[Integer.valueOf(item)][1];
                        }
                    }
                }
                System.out.println(function[nu_of_cookie][maxMoney] + " " + total_value);
                if (final_order != null && !"".equals(final_order)) {
                    System.out.println(final_order);
                }
            } else {
                System.out.println("Case #" + count + ":");
                int total_score = 0;
                String step = "";
                for (int i = 1; i <= nu_of_cookie; i++) {
                    if (cookie[i][1] == 0) {
                        total_score += cookie[i][0];
                        step += i + " ";
                    }
                }
                System.out.println(total_score + " " + 0);
                if (!"".equals(step)) {
                    System.out.println(step.trim());
                }
            }
        }
    }

    private static boolean judgeDirect(String a, String b) {
        String[] aInt = a.trim().split("\\s+");
        String[] bInt = b.trim().split("\\s+");
        int countA = 0;
        int countB = 0;
        for (String item : aInt) {
            if (item != null && !"".equals(item)) {
                countA += Integer.valueOf(item);
            }
        }
        for (String item : bInt) {
            if (item != null && !"".equals(item)) {
                countB += Integer.valueOf(item);
            }
        }
        //当后面字符串小于前面字符串，表名需要替换，返回true
        if (countB < countA) {
            return true;
        } else if (countA == countB) {
            if (aInt.length < bInt.length) {
                return true;
            } else if (aInt.length > bInt.length) {
                return false;
            } else {
                return a.trim().compareTo(b.trim()) < 0;
            }
        } else {
            return false;
        }
    }
}
