package sort;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
 * 利用java ForkJoin实现的快排
 *
 * @author tracy.
 * @create 2017-12-23 11:00
 **/
public class QuickSortByForkJoin {
    static SortItem sortItem = new SortItem();

    public static void main(String[] args) {
        int a[] = new int[2000000];
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(100);
        }
        long commonBegin = System.currentTimeMillis();
        mySort(a, 0, a.length - 1);
        //真正实验的时候是分开跑的，避免第一个排序已经排好了影响第二个。Common spend：18
        System.out.println("Common spend" + (System.currentTimeMillis() - commonBegin));
        SortItem sortItem = new SortItem(a, 0, a.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long forkBegin = System.currentTimeMillis();
        forkJoinPool.invoke(sortItem);
        //真正实验的时候是分开跑的，避免第一个排序已经排好了影响第二个。Fork join spend：1
        System.out.println("Fork join spend" + (System.currentTimeMillis() - forkBegin));
        //本人pc配置: 16 GB 1600 MHz DDR3/2.2 GHz Intel Core i7/MacOS High Sierra   没有吹牛，真的只要1毫秒~
    }

    /**
     * 普通的二分法
     *
     * @param list  原始list
     * @param start 开始点
     * @param end   结束点
     */
    private static void mySort(int[] list, int start, int end) {
        if (start < end) {
            int mid = sortItem.work(list, start, end);
            mySort(list, start, mid - 1);
            mySort(list, mid + 1, end);
        }
    }

    private static void print(int a[]) {
        for (int anA : a) {
            System.out.print(anA + " ");
        }
        System.out.println();
    }

    /**
     * 排序的工作类
     */
    static class SortItem extends RecursiveAction {
        private int[] list = null;
        private int start;
        private int end;

        SortItem() {
        }

        SortItem(int[] list, int start, int end) {
            this.list = list;
            this.start = start;
            this.end = end;
        }

        public int work(int list[], int start, int end) {
            //所有比这个小的，移到左边，所有比这个大的，移到右边
            int compareValue = list[start];
            int i = start;
            int j = end;
            while (i < j) {
                boolean hasMoving = false;
                while (list[i] < compareValue && i < j) {
                    hasMoving = true;
                    i++;
                }
                while (list[j] > compareValue && i < j) {
                    hasMoving = true;
                    j--;
                }
                if (i < j) {
                    //第一步求出a 与 b 不同的二进制
                    list[i] = list[i] ^ list[j];
                    //用b异或上 a与自己不同的地方就得到了原来的a (相当于a^(b^b))
                    list[j] = list[j] ^ list[i];
                    //同理，相当于(a^b^a) 得到原来的b
                    list[i] = list[i] ^ list[j];
                }
                if (!hasMoving && list[i] == list[j]) {
                    break;
                }
                //这里需要处理  类似2 4 4 5 7 8 i=1\j=2 这种情况。永远死循环
            }
            return j;
        }

        @Override
        protected void compute() {
            if (start < end) {
                int mid = work(list, start, end);
                SortItem left = new SortItem(list, start, mid - 1);
                left.fork();
                SortItem right = new SortItem(list, mid + 1, end);
                right.fork();
            }
        }
    }
}
