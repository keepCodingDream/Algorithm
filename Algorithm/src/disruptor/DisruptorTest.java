package disruptor;

import com.lmax.disruptor.*;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tracy.
 * @create 2018-05-18 17:20
 **/
public class DisruptorTest {
    public static void main(String[] args) {
        //手动创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3,
                60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DefaultThreadFactory());
        //EntryFactory ringBuffer 初始化会直接创建好size个对象
        EntryFactory<MyEntry> entryFactory = MyEntry::new;
        //创建3个消费者(handler)
        Worker worker1 = new Worker("worker1");
        Worker worker2 = new Worker("worker2");
        //主角RingBuffer
        RingBuffer<MyEntry> ringBuffer = new RingBuffer<>(entryFactory, 1 << 15);
        //创建第一个barrier1，给消费者1和2用
        ConsumerBarrier<MyEntry> barrier1 = ringBuffer.createConsumerBarrier();
        BatchConsumer<MyEntry> consumer1 = new BatchConsumer<>(barrier1, worker1);
        BatchConsumer<MyEntry> consumer2 = new BatchConsumer<>(barrier1, worker2);
        //创建第二个barrier2,先决条件是batchConsumer1、batchConsumer2消费完
        ConsumerBarrier<MyEntry> barrier2 = ringBuffer.createConsumerBarrier(consumer1, consumer2);
        //消费者3取决于消费者1和2消费完成才发动，所以，消费者3的barrier是barrier2
        //打到线程池开始消费
        long start = System.currentTimeMillis();
        Worker3 worker3 = new Worker3(start);
        BatchConsumer consumer3 = new BatchConsumer<>(barrier2, worker3);
        executor.execute(consumer1);
        executor.execute(consumer2);
        executor.execute(consumer3);
        //创建生产者,因为生产者要关注消费最慢的消费者，所以producerBarrier关注的是consumer3
        ProducerBarrier<MyEntry> producerBarrier = ringBuffer.createProducerBarrier(consumer3);
        for (int i = 1; i <= 6000000; i++) {
            MyEntry myEntry = producerBarrier.nextEntry();
            myEntry.setName(i + "haha");
            myEntry.setCount(i);
            producerBarrier.commit(myEntry);
        }
    }

    static class Worker3 implements BatchHandler<MyEntry> {

        private long startTime;

        public Worker3(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void onAvailable(MyEntry myEntry) throws Exception {
            if (myEntry.getCount() == 6000000) {
                System.out.println("Task spend:" + (System.currentTimeMillis() - startTime));
            }
        }

        @Override
        public void onEndOfBatch() throws Exception {

        }
    }

    static class Worker implements BatchHandler<MyEntry> {
        private String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void onAvailable(MyEntry myEntry) throws Exception {
            myEntry.setName(myEntry.getName() + ":" + name);
            //System.out.println(name + " start handler:--" + myEntry.getName());
        }

        @Override
        public void onEndOfBatch() throws Exception {
            //System.out.println(name + "Task end!");
        }
    }

    static class MyEntry extends AbstractEntry {
        private String name;
        private int count;

        public MyEntry() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }


    /**
     * 线程池的线程产生工厂
     */
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
