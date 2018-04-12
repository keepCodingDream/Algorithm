package elevatorSimulation;

import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random(System.currentTimeMillis());
        int peopleSize = 2000;
        Integer[] targetFloor = new Integer[peopleSize];
        for (int i = 0; i < peopleSize; i++) {
            //目标楼层一共3层
            targetFloor[i] = random.nextInt(30) + 1;
        }
        //1.这种是简单的不分单双层
        //Queue<Integer> targetQueue = new LinkedBlockingDeque<>(Arrays.asList(targetFloor));
        //simpleStep(targetQueue);
        //2.区分单双层
        Queue<Integer> queue1 = new LinkedBlockingDeque<>();
        Queue<Integer> queue2 = new LinkedBlockingDeque<>();
        for (int item : targetFloor) {
            if (item % 2 == 0) {
                queue1.offer(item);
            } else {
                queue2.offer(item);
            }
        }
        System.out.println("分组完毕，第一组：" + queue1.size() + "人；第二组:" + queue2.size() + "人");
        final CountDownLatch latch = new CountDownLatch(6);
        Thread elevator1 = new Thread(new Elevator(queue1, 15, latch), "1");
        Thread elevator2 = new Thread(new Elevator(queue1, 15, latch), "2");
        Thread elevator3 = new Thread(new Elevator(queue1, 15, latch), "3");
        Thread elevator4 = new Thread(new Elevator(queue2, 15, latch), "4");
        Thread elevator5 = new Thread(new Elevator(queue2, 15, latch), "5");
        Thread elevator6 = new Thread(new Elevator(queue2, 15, latch), "6");
        long start = System.currentTimeMillis();
        elevator1.start();
        elevator2.start();
        elevator3.start();
        elevator4.start();
        elevator5.start();
        elevator6.start();
        System.out.println("线程全部启动完毕，等待结束……");
        latch.await();
        System.out.println("任务全部执行完毕，执行时长:" + (System.currentTimeMillis() - start));

    }


    /**
     * 模拟6部电梯跑所有用户
     */
    private static void simpleStep(Queue<Integer> targetQueue) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(6);
        Thread elevator1 = new Thread(new Elevator(targetQueue, 15, latch), "1");
        Thread elevator2 = new Thread(new Elevator(targetQueue, 15, latch), "2");
        Thread elevator3 = new Thread(new Elevator(targetQueue, 15, latch), "3");
        Thread elevator4 = new Thread(new Elevator(targetQueue, 15, latch), "4");
        Thread elevator5 = new Thread(new Elevator(targetQueue, 15, latch), "5");
        Thread elevator6 = new Thread(new Elevator(targetQueue, 15, latch), "6");
        long start = System.currentTimeMillis();
        elevator1.start();
        elevator2.start();
        elevator3.start();
        elevator4.start();
        elevator5.start();
        elevator6.start();
        System.out.println("线程全部启动完毕，等待结束……");
        latch.await();
        System.out.println("任务全部执行完毕，执行时长:" + (System.currentTimeMillis() - start));
    }

    static class Elevator implements Runnable {
        private Queue<Integer> targetQueue;
        private CountDownLatch latch;
        private int elevatorSize = 15;

        public Elevator(Queue<Integer> targetQueue, int elevatorSize, CountDownLatch latch) {
            this.targetQueue = targetQueue;
            this.elevatorSize = elevatorSize;
            this.latch = latch;
        }

        @Override
        public void run() {
            Integer floorNum = 0;
            while (floorNum != null) {
                int currentSize = 0;
                Set<Integer> allMemFloor = new HashSet<>();
                while (currentSize < elevatorSize) {
                    floorNum = targetQueue.poll();
                    if (floorNum != null) {
                        currentSize++;
                        allMemFloor.add(floorNum);
                    } else {
                        //这种情况就是人满了，直接走
                        break;
                    }
                }
                int allFloors = allMemFloor.size();
                System.out.println("电梯--" + Thread.currentThread().getName() + "号启动，搭载人数:" + currentSize);
                try {
                    //每一个楼层停靠300ms
                    Thread.sleep(150 * allFloors);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
            latch.countDown();
        }
    }
}
