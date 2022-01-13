package ChapterSix;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TOLock implements Lock {
	static QNodeTO AVAILABLE = new QNodeTO();
	AtomicReference<QNodeTO> tail;
	ThreadLocal<QNodeTO> myNode;

	public TOLock() {
		tail = new AtomicReference<QNodeTO>(null);
		myNode = new ThreadLocal<QNodeTO>() {
			protected QNodeTO initialValue() {
				return new QNodeTO();
			}
		};
	}

	@Override
	public void lock() {
		// TODO Auto-generated method stub

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

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		long patience = TimeUnit.MILLISECONDS.convert(time, unit);
		QNodeTO qnode = new QNodeTO();
		myNode.set(qnode);
		qnode.pred = null;
		QNodeTO myPred = tail.getAndSet(qnode);
		if (myPred == null || myPred.pred == AVAILABLE) {
			return true;
		}
		while (System.currentTimeMillis() - startTime < patience) {
			QNodeTO predPred = myPred.pred;
			if (predPred == AVAILABLE) {
				return true;
			} else if (predPred != null) {
				myPred = predPred;
			}
		}
		if (!tail.compareAndSet(qnode, myPred))
			qnode.pred = myPred;
		return false;
	}

	public void unlock() {
		QNodeTO qnode = myNode.get();
		if (!tail.compareAndSet(qnode, null))
			qnode.pred = AVAILABLE;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}