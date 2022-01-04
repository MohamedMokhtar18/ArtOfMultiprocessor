package ChapterOne;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Filter implements Lock {
	int[] level;
	int[] victim;
    private int n;
    /**
     * Constructor for Filter lock
     *
     * @param n thread count
     */
	public Filter(int n) {
        this.n = n;
		level = new int[n];
		victim = new int[n]; // use 1..n-1
		for (int i = 0; i < n; i++) {
			 level[i] = 0;
			 }
	}
	@Override
	public void lock() {
		int me = (int) Thread.currentThread().getId();
		for (int i = 1; i < n; i++) { 
			 level[me] = i;
			 victim[i] = me;
			// spin while conflicts exist
			 for (int k = 0; k < n; k++) {
			 while (k != me && (level[k] >= i && victim[i] == me)) {};
			 }
		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
