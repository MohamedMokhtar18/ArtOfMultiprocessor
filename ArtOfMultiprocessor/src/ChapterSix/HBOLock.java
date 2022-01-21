package ChapterSix;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class HBOLock implements Lock {
	private static final int LOCAL_MIN_DELAY = 1;
	private static final int LOCAL_MAX_DELAY = 256;
	private static final int REMOTE_MIN_DELAY = 1;
	private static final int REMOTE_MAX_DELAY = 512;
	private static final int FREE = -1;
	AtomicInteger state;
	private static ThreadLocal threadID = new ThreadLocal();

	public HBOLock() {
		state = new AtomicInteger(FREE);
	}

	public void lock() {
		int myCluster = (int) threadID.get() / 2;
		Backoff localBackoff = new Backoff(LOCAL_MIN_DELAY, LOCAL_MAX_DELAY);
		Backoff remoteBackoff = new Backoff(REMOTE_MIN_DELAY, REMOTE_MAX_DELAY);
		while (true) {
			if (state.compareAndSet(FREE, myCluster)) {
				return;
			}
			int lockState = state.get();
			if (lockState == myCluster) {
				try {
					localBackoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					remoteBackoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void unlock() {
		state.set(FREE);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}
