package thread;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 该测试用例测试了排它锁(可重入/不可重入锁)和读写锁
 * 
 * @author tracy
 *
 */
public class ThreadTest {
  public static void main(String[] args) {
    // 1.Mutex test
    // 线程会在lock.lock()处阻塞。当没有获取锁时，该线程会被挂起。知道有锁的持有者释放锁，bb线程的挂起状态才会被解除
    // Mutex 是不可重入锁，也就是说线程自己再调用自己的lock也会被阻塞
    TestObject runClass = new TestObject(new Mutex());
    new Thread(new TestThread(runClass), "aa").start();
    new Thread(new TestThread(runClass), "bb").start();
    // 2.ReentrantLock是可冲入锁
    TestObject runClass2 = new TestObject(new ReentrantLock());
    new Thread(new TestThread(runClass2), "cc").start();
    new Thread(new TestThread(runClass2), "dd").start();
    // 3.读写锁示例 log：
    // write lock is holded
    // write lock is holded
    // thread=eewrite:0
    // thread=eewrite:1
    // thread=eewrite:2
    // thread=eewrite:3
    // thread=eewrite:4
    // thread=eewrite:5
    // thread=eewrite:6
    // thread=eewrite:7
    // thread=eewrite:8
    // thread=eewrite:9
    // thread=ffkey= ee0 and value= 0
    // thread=ffkey= ee1 and value= 1
    // thread=ffkey= ee0 and value= 0
    // thread=ffkey= ee1 and value= 1
    // thread=ffkey= ee2 and value= 2
    // thread=ffkey= ee2 and value= 2
    // thread=ffkey= ee3 and value= 3
    // thread=ffkey= ee4 and value= 4
    // thread=ffkey= ee3 and value= 3
    // thread=ffkey= ee4 and value= 4
    // thread=ffkey= ee5 and value= 5
    // thread=ffkey= ee6 and value= 6
    // thread=ffkey= ee7 and value= 7
    // thread=ffkey= ee5 and value= 5
    // thread=ffkey= ee6 and value= 6
    // thread=ffkey= ee7 and value= 7
    // thread=ffkey= ee8 and value= 8
    // thread=ffkey= ee8 and value= 8
    // thread=ffkey= ee9 and value= 9
    // thread=ffkey= ee9 and value= 9

    // 可以看到写锁占有时,读锁会打印write lock is holded，并且会被挂起。等到写锁释放，两个读锁同时进行(log的线程名不一致)
    TestObject runClass3 = new TestObject(new ReentrantReadWriteLock(), new LinkedHashMap<String, Integer>());
    new Thread(new TestThread(runClass3), "ee").start();
    new Thread(new TestThread(runClass3), "ff").start();
    new Thread(new TestThread(runClass3), "ff").start();
  }

  private static class TestObject {
    Lock lock;
    Map<String, Integer> map;
    ReentrantReadWriteLock readWriteLock;

    public TestObject(Lock lock) {
      this.lock = lock;
    }

    public TestObject(ReentrantReadWriteLock readWriteLock, Map<String, Integer> map) {
      this.readWriteLock = readWriteLock;
      this.map = map;
    }

    public void read() {
      if (readWriteLock.isWriteLocked()) {
        System.out.println("write lock is holded");
      }
      readWriteLock.readLock().lock();
      try {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          System.out.println("thread=" + Thread.currentThread().getName() + "key= " + entry.getKey() + " and value= "
              + entry.getValue());
        }
      } catch (Exception e) {

      } finally {
        readWriteLock.readLock().unlock();
      }
    }

    public void write() {
      if (readWriteLock.isWriteLocked()) {
        System.out.println(Thread.currentThread().getName() + "Hold the write lock");
      }
      readWriteLock.writeLock().lock();
      try {
        for (int i = 0; i < 10; i++) {
          System.out.println("thread=" + Thread.currentThread().getName() + "write:" + i);
          map.put(Thread.currentThread().getName() + i, i);
          TimeUnit.SECONDS.sleep(1);
        }
      } catch (Exception e) {

      } finally {
        readWriteLock.writeLock().unlock();
      }
    }

    public void printLog() {
      lock.lock();
      System.out.println(Thread.currentThread().getName() + "start");
      lock.lock();// ReentrantLock可以继续往下执行
      try {
        System.out.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(10);
      } catch (Exception e) {
        // ignore
      } finally {
        lock.unlock();
      }
    }
  }

  private static class TestThread implements Runnable {
    TestObject test;

    public TestThread(TestObject test) {
      this.test = test;
    }

    @Override
    public void run() {
      if ("ee".equals(Thread.currentThread().getName())) {
        test.write();
      } else if (Thread.currentThread().getName().contains("ff")) {
        test.read();
      } else {
        test.printLog();
      }
    }

  }
}
