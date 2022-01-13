package ChapterSix;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BackoffLock implements Lock {
	private AtomicBoolean state = new AtomicBoolean(false);
	private static final int MIN_DELAY = 1;
	private static final int MAX_DELAY = 128;

	public void lock() {
		Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
		while (true) {
			while (state.get()) {
			}
			;
			if (!state.getAndSet(true)) {
				return;
			} else {
				try {
					backoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void unlock() {
		state.set(false);
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
