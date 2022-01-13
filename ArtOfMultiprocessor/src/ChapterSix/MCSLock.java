package ChapterSix;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSLock implements Lock {
	AtomicReference<QNodeMCS> tail;
	ThreadLocal<QNodeMCS> myNode;

	public MCSLock() {
		tail = new AtomicReference<QNodeMCS>(null);
		myNode = new ThreadLocal<QNodeMCS>() {
			protected QNodeMCS initialValue() {
				return new QNodeMCS();
			}
		};
	}

	@Override
	public void lock() {
		QNodeMCS qnode = myNode.get();
		QNodeMCS pred = tail.getAndSet(qnode);
		if (pred != null) {
			qnode.locked = true;
			pred.next = qnode;
			// wait until predecessor gives up the lock
			while (qnode.locked) {
			}
		}
	}

	@Override
	public void unlock() {
		QNodeMCS qnode = myNode.get();
		if (qnode.next == null) {
			if (tail.compareAndSet(qnode, null))
				return;
			// wait until successor fills in the next field
			while (qnode.next == null) {
			}
		}
		qnode.next.locked = false;
		qnode.next = null;
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
