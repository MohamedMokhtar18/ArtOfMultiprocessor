package ChapterSix;

import java.util.concurrent.TimeUnit;

public class CompositeFastPathLock extends CompositeLock {
	private static final int FASTPATH = 3;

	private boolean fastPathLock() {
		int oldStamp, newStamp;
		int stamp[] = { 0 };
		QNodeCL qnode;
		qnode = tail.get(stamp);
		oldStamp = stamp[0];
		if (qnode != null) {
			return false;
		}
		if ((oldStamp & FASTPATH) != 0) {
			return false;
		}
		newStamp = (oldStamp + 1) | FASTPATH;
		return tail.compareAndSet(qnode, null, oldStamp, newStamp);
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		if (fastPathLock()) {
			return true;
		}
		if (super.tryLock(time, unit)) {
			while ((tail.getStamp() & FASTPATH) != 0) {
			}
			;
			return true;
		}
		return false;
	}

	private boolean fastPathUnlock() {
		int oldStamp, newStamp;
		oldStamp = tail.getStamp();
		if ((oldStamp & FASTPATH) == 0) {
			return false;
		}
		int[] stamp = { 0 };
		QNodeCL qnode;
		do {
			qnode = tail.get(stamp);
			oldStamp = stamp[0];
			newStamp = oldStamp & (FASTPATH);
		} while (!tail.compareAndSet(qnode, qnode, oldStamp, newStamp));
		return true;
	}

	public void unlock() {
		if (!fastPathUnlock()) {
			super.unlock();
		}
		;
	}
}
