package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占式所，内部维护一个队列同步器
 * <p>
 * 1.当调用tryLock的时候，CAS更新state(释放状态是0,被获取状态是1)，如果更新成功，则直接从该方法返回，设置当前线程为锁拥有线程,返回true。
 * 如果更新失败，则返回false。同时将该线程假如等待队列，并将该线程挂起
 * <p>
 * 2.当任务执行完毕，首先CAS更新status为0，然后将等待队列中的下一个线程unPark
 * 
 * @author tracy
 *
 */
public class Mutex implements Lock {
  private static class Sync extends AbstractQueuedSynchronizer {

    /**
     * 
     */
    private static final long serialVersionUID = 2750045854273028308L;

    protected boolean isHeldExclusively() {
      return getState() == 1;
    }

    protected boolean tryAcquire(int acquire) {
      if (compareAndSetState(0, 1)) {
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      return false;
    }

    protected boolean tryRelease(int release) {
      if (getState() == 0) {
        throw new IllegalMonitorStateException("锁已经被释放");
      }
      setExclusiveOwnerThread(null);
      setState(0);
      return true;
    }

    Condition newCondition() {
      return new ConditionObject();
    }
  }

  private Sync sync;

  public Mutex() {
    sync = new Sync();
  }

  @Override
  public void lock() {
    sync.acquire(1);

  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
  }

  @Override
  public boolean tryLock() {
    return sync.tryAcquire(1);
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(time));
  }

  @Override
  public void unlock() {
    sync.release(0);
  }

  @Override
  public Condition newCondition() {
    return sync.newCondition();
  }

}
