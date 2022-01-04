package ChapterOne;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock {
	boolean[] flag;
	int[] label;
	private int n;
	public Bakery(int n) {
        this.n = n;
		flag = new boolean[n];
		label = new int[n];	
		for (int i = 0; i < n; i++) {
			flag[i] = false;
			label[i] = 0;
			}
	}
	@Override
	public void lock() {
		int i = (int) Thread.currentThread().getId();
		flag[i] = true;
		int max=label[0];
		for(int j=0;j<n;j++) {
		if(max<label[j]) {
			max=label[j];
		} 
		}
		label[i]=max+1;
		for (int k = 0; k < n; k++) {
			while (k != i &&flag[k] && label[k] < label[i]) {};

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
		int i = (int) Thread.currentThread().getId();
		flag[i]=false;

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
