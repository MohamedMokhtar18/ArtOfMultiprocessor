package ChapterSix;

import java.util.concurrent.atomic.AtomicInteger;

public class QNode {
	private static ThreadLocal threadID = new ThreadLocal();

	// private boolean tailWhenSpliced;
	private static final int TWS_MASK = 0x80000000;
	// private boolean successorMustWait = false;
	private static final int SMW_MASK = 0x40000000;
	// private int clusterID;
	private static final int CLUSTER_MASK = 0x3FFFFFFF;
	AtomicInteger state;

	public QNode() {
		state = new AtomicInteger(0);
	}

	public void unlock() {
	 int oldState = 0;
	 int newState =(int)threadID.get()/2;
	 // successorMustWait = true;
	 newState |= SMW_MASK;
	 // tailWhenSpliced = false;
	 newState &= (TWS_MASK);
	 do {
	 oldState = state.get();
	 } while (! state.compareAndSet(oldState, newState));
	 }

	public int getClusterID() {
		return state.get() & CLUSTER_MASK;
	}
	// other getters and setters omitted.
}
