package ChapterSix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class HCLHLock implements Lock {
	private static ThreadLocal threadID = new ThreadLocal();
	static final int MAX_CLUSTERS = 20;
	List<AtomicReference<QNodeHCLH>> localQueues;
	AtomicReference<QNodeHCLH> globalQueue;
	ThreadLocal<QNodeHCLH> currNode=new ThreadLocal<QNodeHCLH>(){protected QNodeHCLH initialValue(){return new QNodeHCLH();};};
	ThreadLocal<QNodeHCLH> predNode=new ThreadLocal<QNodeHCLH>(){protected QNodeHCLH initialValue(){return null;};};

	public HCLHLock() {
		localQueues = new ArrayList<AtomicReference<QNodeHCLH>>(MAX_CLUSTERS);
		for (int i = 0; i < MAX_CLUSTERS; i++) {
			localQueues.add(new AtomicReference<QNodeHCLH>());
		}
		QNodeHCLH head = new QNodeHCLH();
		globalQueue = new AtomicReference<QNodeHCLH>(head);
	}

	public void lock() {
		QNodeHCLH myNode = currNode.get();
		AtomicReference<QNodeHCLH> localQueue = localQueues.get((int) threadID.get() / 2);
		// splice my QNode into local queue
		QNodeHCLH myPred = null;
		do {
			myPred = localQueue.get();
		} while (!localQueue.compareAndSet(myPred, myNode));
		if (myPred != null) {
			boolean iOwnLock = myPred.waitForGrantOrClusterMaster();
			if (iOwnLock) {
				predNode.set(myPred);
				return;
			}
		}
		// I am the cluster master: splice local queue into global queue.
		QNodeHCLH localTail = null;
		do {
			myPred = globalQueue.get();
			localTail = localQueue.get();
		} while (!globalQueue.compareAndSet(myPred, localTail));
		// inform successor it is the new master
		localTail.setTailWhenSpliced(true);
		while (myPred.isSuccessorMustWait()) {
		}
		;
		predNode.set(myPred);
		return;
	}

	public void unlock() {
		QNodeHCLH myNode = currNode.get();
		myNode.setSuccessorMustWait(false);
		QNodeHCLH node = predNode.get();
		node.unlock();
		currNode.set(node);
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
