package ChapterOne;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class LockOne implements Lock {
	private boolean[] flag = new boolean[2];
	@Override
	public void lock() {
		int i = (int) Thread.currentThread().getId();
		int j = 1 - i;
		flag[i] = true;
		while (flag[j]) {} 
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
	public void unlock() {
		int i = (int) Thread.currentThread().getId();
		flag[i] = false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
