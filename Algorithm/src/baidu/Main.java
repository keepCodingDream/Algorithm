package baidu;

import java.util.*;

/**
 * 复赛第一题
 *
 * @author tracy.
 * @create 2017-08-12 14:07
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String nm = sc.nextLine();
            int n = Integer.valueOf(nm.split(" ")[0]);
            int m = Integer.valueOf(nm.split(" ")[1]);
            int draw[][] = new int[n][m];
            Character a = '0';
            for (int i = 0; i < n; i++) {
                String lineItem = sc.nextLine();
                for (int j = 0; j < m; j++) {
                    draw[i][j] = a.equals(lineItem.charAt(j)) ? 0 : 1;
                }
            }
            boolean isOne = true;
            for (int i = 0; i < n; i++) {
                boolean flag = false;
                int status = 0;
                int start = draw[i][0];
                int willCheckIndex[][] = new int[n][m];
                int indexCount = 0;
                int length_change_count = 0;//行变更统计
                for (int j = 1; j < m; j++) {
                    if (draw[i][j] == 1) {
                        willCheckIndex[i][indexCount++] = j;
                    }
                    if (draw[i][j] != start) {
                        start = draw[i][j];
                        length_change_count++;
                    }
                }
                if (i > 0 && isOne && status > 0) {
                    int same = illegal(willCheckIndex[i - 1], willCheckIndex[i]);
                    if (same != 1) {
                        isOne = false;
                        break;
                    }
                }
                int first = draw[i][0];
                if (length_change_count > 2) {
                    isOne = false;
                    break;
                }
                if (status == 0) {
                    if (first == 0) {
                        if (length_change_count == 0) {
                            //do nothing
                        } else if (length_change_count > 0) {
                            status = 1;
                        }
                    } else {
                        if (length_change_count == 2) {
                            isOne = false;
                            break;
                        } else {
                            status = 1;
                        }
                    }
                } else if (status == 1) {
                    if (first == 0) {
                        if (length_change_count == 0) {
                            status = 2;
                        }
                    } else {
                        if (length_change_count == 2) {
                            isOne = false;
                            break;
                        }
                    }
                } else if (status == 2) {
                    if (first == 0) {
                        if (length_change_count != 0) {
                            isOne = false;
                            break;
                        }
                    } else {
                        isOne = false;
                        break;
                    }
                }
                if (!isOne) {
                    break;
                }
            }
            if (isOne) {
                System.out.println(1);
                continue;
            }
            //judge 0
            boolean isTwo = true;
            int j_changeCount = 0;
            int latest_status = 0;//关注行，变化量0->没变化；1->"1"状态；2->"0"状态;3->"1"状态
            boolean allow_zero_or_one = true;//是否再允许变成0/1
            int willCheckIndex[][] = new int[n][m];
            for (int i = 0; i < n; i++) {
                int start = draw[i][0];
                int firstChart = draw[i][0];//每行第一个字符
                int length_change_count = 0;//行变更统计
                int indexCount = 0;
                if (start == 1) {
                    willCheckIndex[i][indexCount++] = 0;
                }
                boolean legal = false;//行是否判断过合法
                for (int j = 1; j < m; j++) {
                    if (draw[i][j] == 1) {
                        willCheckIndex[i][indexCount++] = j;
                    }
                    if (draw[i][j] != start) {
                        start = draw[i][j];
                        length_change_count++;
                    }
                }
                if (i > 0) {
                    int sameCount = illegal(willCheckIndex[i - 1], willCheckIndex[i]);
                    //"1"出现以后，只要sameCount!=0且不大于2
                    if (sameCount != 1 && sameCount != 2 && latest_status > 0) {
                        isTwo = false;
                        break;
                    }
                }
                //如果上一个状态是0，代表还没有"1"状态出现
                if (latest_status == 0) {
                    //如果第一个字符是0的话(统计变化量可以看出是0还是1)
                    if (firstChart == 0) {
                        if (length_change_count == 2) {
                            latest_status = 1;//代表第一个1出现
                        } else if (length_change_count != 0) {
                            //1还没有出现，不能出现0
                            isTwo = false;
                            break;
                        }
                    } else {
                        if (length_change_count <= 1) {
                            latest_status = 1;
                        } else {
                            //只要第一个字符是"1"，变化超过一次就是0了
                            isTwo = false;
                            break;
                        }
                    }
                } else if (latest_status == 1) {
                    //如果第一个字符是0的话(统计变化量可以看出是0还是1)
                    if (firstChart == 0) {
                        if (length_change_count == 2) {
                            //do nothing
                        } else if (length_change_count != 4) {
                            //1出现了，不能再出现全0,不等于4就是全0
                            isTwo = false;
                            break;
                        } else {
                            latest_status = 2;
                        }
                    } else {
                        if (length_change_count <= 1) {
                            //表示还是1，可以继续
                        } else if (length_change_count < 4) {
                            //2\3都可以表示成"0"
                            latest_status = 2;
                        } else {
                            isTwo = false;
                            break;
                        }
                    }
                } else if (latest_status == 2) {
                    if (firstChart == 0) {
                        if (length_change_count == 2) {
                            //又变成1
                            latest_status = 3;
                        } else if (length_change_count != 4) {
                            //不等于4代表不是"0"，不是0又不是1，非法
                            isTwo = false;
                            break;
                        }
                    } else {
                        if (length_change_count <= 1) {
                            //表示是1，切状态
                            latest_status = 3;
                        } else if (length_change_count < 4) {
                            //2\3都可以表示成"0"
                            // do nothings
                        } else {
                            isTwo = false;
                            break;
                        }
                    }
                } else if (latest_status == 3) {
                    //又变回了1
                    if (firstChart == 0) {
                        if (length_change_count == 2) {
                            //又变成1
                            //允许
                        } else if (length_change_count == 0) {
                            //从1变回0
                            latest_status = 4;
                        } else {
                            //不是1就非法
                            isTwo = false;
                            break;
                        }
                    } else {
                        if (length_change_count > 1) {
                            isTwo = false;
                            break;
                        }
                    }
                } else {
                    if (firstChart == 0) {
                        if (length_change_count != 0) {
                            isTwo = false;
                            break;
                        }
                    } else {
                        isTwo = false;
                        break;
                    }
                }

            }
            if (isTwo) {
                System.out.println(0);
                continue;
            }
            System.out.println(-1);
        }
    }

    private static int illegal(int before[], int after[]) {
        Map<Integer, Boolean> compareMap = new HashMap<Integer, Boolean>();
        List<Integer> same = new ArrayList<Integer>();
        for (int i = 0; i < before.length; i++) {
            compareMap.put(before[i], true);
        }
        for (int i = 0; i < after.length; i++) {
            if (compareMap.containsKey(after[i])) {
                same.add(after[i]);
            }
        }
        int success = 0;
        boolean flag = true;
        if (same.size() == 0) {
            return 0;
        }
        int start = same.get(0);
        for (int i = 1; i < same.size(); i++) {
            if (same.get(i) == start + 1) {
                if (flag) {
                    flag = false;
                    success++;
                }
            } else {
                flag = true;
            }
            start = same.get(i);
        }
        return success;
    }

}
