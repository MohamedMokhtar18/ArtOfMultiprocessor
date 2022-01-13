package ChapterSix;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CompositeLock implements Lock {
	private static final int SIZE = 64;
	private static final int MIN_BACKOFF = 1;
	private static final int MAX_BACKOFF = 265;
	AtomicStampedReference<QNodeCL> tail;
	QNodeCL[] waiting;
	Random random;
	ThreadLocal<QNodeCL> myNode = new ThreadLocal<QNodeCL>() {
		protected QNodeCL initialValue() {
			return null;
		};
	};
	public CompositeLock() {
		tail = new AtomicStampedReference<QNodeCL>(null, 0);
		waiting = new QNodeCL[SIZE];
		for (int i = 0; i < waiting.length; i++) {
			waiting[i] = new QNodeCL();
		}
		random = new Random();
	}

	@Override
	public void unlock() {
		QNodeCL acqNode = myNode.get();
		acqNode.state.set(State.RELEASED);
		myNode.set(null);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		long patience = TimeUnit.MILLISECONDS.convert(time, unit);
		long startTime = System.currentTimeMillis();
		Backoff backoff = new Backoff(MIN_BACKOFF, MAX_BACKOFF);
		try {
			QNodeCL node = acquireQNode(backoff, startTime, patience);
			QNodeCL pred = spliceQNode(node, startTime, patience);
			waitForPredecessor(pred, node, startTime, patience);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	private QNodeCL acquireQNode(Backoff backoff, long startTime, long patience)
			throws TimeoutException, InterruptedException {
		QNodeCL node = waiting[random.nextInt(SIZE)];
		QNodeCL currTail;
	    int[] currStamp = {0};

		while (true) {
			if (node.state.compareAndSet(State.FREE, State.WAITING)) {
				return node;
			}
			currTail = tail.get(currStamp);
			State state = node.state.get();
			if (state == State.ABORTED || state == State.RELEASED) {
				if (node == currTail) {
					QNodeCL myPred = null;
					if (state == State.ABORTED) {
						myPred = node.pred;
					}
					if (tail.compareAndSet(currTail, myPred, currStamp[0], currStamp[0] + 1)) {
						node.state.set(State.WAITING);
						return node;
					}
				}
			}
			backoff.backoff();
			if (timeout(patience, startTime)) {
				throw new TimeoutException();
			}
		}
	}

	private QNodeCL spliceQNode(QNodeCL node, long startTime, long patience) throws TimeoutException {
		QNodeCL currTail;
	    int[] currStamp = {0};

		do {
			currTail = tail.get(currStamp);
			if (timeout(startTime, patience)) {
				node.state.set(State.FREE);
				throw new TimeoutException();
			}
		} while (!tail.compareAndSet(currTail, node, currStamp[0], currStamp[0] + 1));
		return currTail;
	}

	private void waitForPredecessor(QNodeCL pred, QNodeCL node, long startTime, long patience) throws TimeoutException {
		int[] stamp = { 0 };
		if (pred == null) {
			myNode.set(node);
			return;
		}
		State predState = pred.state.get();
		while (predState != State.RELEASED) {
			if (predState == State.ABORTED) {
				QNodeCL temp = pred;
				pred = pred.pred;
				temp.state.set(State.FREE);
			}
			if (timeout(patience, startTime)) {
				node.pred = pred;
				node.state.set(State.ABORTED);
				throw new TimeoutException();
			}
			predState = pred.state.get();
		}
		pred.state.set(State.FREE);
		myNode.set(node);
		return;
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

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	private boolean timeout(long startTime, long patience) {
	    return System.currentTimeMillis() - startTime > patience;
	  }
}
