package baidu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 2017百度之星大赛第三题
 *
 * @author tracy.
 * @create 2017-08-05 13:17
 **/
public class Question3 {
    public static void main(String[] args) {
        System.out.print("1234".compareTo("1311"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            Integer maxAttack = Integer.MIN_VALUE;
            Integer maxDefine = Integer.MIN_VALUE;
            String firstLine = sc.nextLine();
            String[] MN = firstLine.split("\\s+");
            Integer N = Integer.valueOf(MN[0]);
            Integer M = Integer.valueOf(MN[1]);

            //因为打死怪兽是生命值小于0，所以怪兽可以用一维数组表示
            Integer[][] monstersInt = new Integer[N][2];
            Integer[][] attackInt = new Integer[M][2];
            List<Weapon> payForAttack = new ArrayList<Weapon>(M);

            for (int i = 0; i < N; i++) {
                String monsterLine = sc.nextLine();
                String[] monster = monsterLine.split("\\s+");
                Integer itemBlod = Integer.valueOf(monster[0]);
                Integer itemDefine = Integer.valueOf(monster[1]);
                monstersInt[i][0] = itemBlod;
                monstersInt[i][1] = itemDefine;
                if (itemDefine > maxDefine) {
                    maxDefine = itemDefine;
                }
            }
            for (int i = 0; i < M; i++) {
                String attackLine = sc.nextLine();
                String[] attack = attackLine.split("\\s+");
                Integer itemPay = Integer.valueOf(attack[0]);
                Integer itemAttack = Integer.valueOf(attack[1]);
                attackInt[i][0] = itemPay;
                attackInt[i][1] = itemAttack;
                if (itemAttack > maxAttack) {
                    maxAttack = itemAttack;
                }
                Weapon weapon = new Weapon();
                weapon.setAttackPay(itemPay);
                weapon.setAttackValue(itemAttack);
                weapon.setPayForAttack((double) (itemAttack / itemPay));
                payForAttack.add(weapon);
            }
            //check will beat
            if (maxAttack < maxDefine) {
                System.out.println(-1);
                continue;
            }
            int total = 0;
            //start to beat monster
            boolean willWrite = true;
            for (int i = 0; i < N; i++) {
                Integer monsterItem[] = monstersInt[i];
                // attack will > blood
                List<Weapon> filteredPay = new ArrayList<Weapon>(M);
                for (Weapon item : payForAttack) {
                    if (item.getAttackValue() >= monsterItem[1]) {
                        filteredPay.add(item);
                    }
                }
                Integer bloodLess = monsterItem[0];
                Integer define = monsterItem[1];
                //横坐标代表技能，纵坐标代表血量。表示1滴血的时候,a\b\c\d技能的花费，2滴血的时候，a\b\c\d技能的花费……
                int function[][] = new int[M + 1][bloodLess + 1];
                //F[i][v]=min{F[i-1][v],F[i-1][v-k*c[i]]+k*w[i]}
                //v=bloodLess index;
                //c[i]=攻击力-防御(因为一样，所以可以忽略)
                //w[i]=消耗能量
                int weaponIndex = 1;//F[i][v]的i
                for (Weapon item : filteredPay) {
                    for (int b = 1; b <= bloodLess; b++) {
                        int c_i = item.getAttackValue();
                        int w_i = item.getAttackPay();

                        int blood_total = b;
                        int k_total = 0;//攻击多少次
                        while (blood_total > 0) {
                            blood_total -= c_i - define;
                            k_total++;
                        }
                        if (k_total > 100000) {
                            break;
                        }
                        if (weaponIndex == 1) {
                            function[weaponIndex][b] = k_total * w_i;
                        } else {
                            for (int k = 0; k <= k_total; k++) {
                                if (b - k * (c_i - define) >= 1) {
                                    if (function[weaponIndex][b] == 0) {
                                        function[weaponIndex][b] = k_total * w_i;
                                    }
                                    function[weaponIndex][b] = Math.min(function[weaponIndex][b],
                                            function[weaponIndex - 1][b - k * (c_i - define)] + k * w_i);
                                }
                            }
                        }
                    }
                    weaponIndex++;
                }
                int little_total = Integer.MAX_VALUE;
                for (int kk = 1; kk <= filteredPay.size(); kk++) {
                    if (function[kk][bloodLess] < little_total) {
                        little_total = function[kk][bloodLess];
                    }
                }
                if (little_total == 0) {
                    willWrite = false;
                    System.out.println(-1);
                    break;
                }
                total += little_total;
            }
            if (willWrite) {
                System.out.println(total);
            }
        }
    }

    static class Weapon {
        private Double payForAttack;
        private Integer attackPay;
        private Integer attackValue;

        public Double getPayForAttack() {
            return payForAttack;
        }

        public Integer getAttackPay() {
            return attackPay;
        }

        public Integer getAttackValue() {
            return attackValue;
        }

        public void setPayForAttack(Double payForAttack) {
            this.payForAttack = payForAttack;
        }

        public void setAttackPay(Integer attackPay) {
            this.attackPay = attackPay;
        }

        public void setAttackValue(Integer attackValue) {
            this.attackValue = attackValue;
        }
    }
}
