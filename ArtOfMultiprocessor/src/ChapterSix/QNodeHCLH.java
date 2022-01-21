package ChapterSix;

import java.util.concurrent.atomic.AtomicInteger;

class QNodeHCLH {
	// private boolean tailWhenSpliced;
	private static final int TWS_MASK = 0x80000000;
	// private boolean successorMustWait= false;
	private static final int SMW_MASK = 0x40000000;
	// private int clusterID;
	private static final int CLUSTER_MASK = 0x3FFFFFFF;
	AtomicInteger state;
	private static ThreadLocal threadID = new ThreadLocal();

	public QNodeHCLH() {
		state = new AtomicInteger(0);
	}

	boolean waitForGrantOrClusterMaster() {
		int myCluster = (int) threadID.get() / 2;
		while (true) {
			if (getClusterID() == myCluster && !isTailWhenSpliced() && !isSuccessorMustWait()) {
				return true;
			} else if (getClusterID() != myCluster || isTailWhenSpliced()) {
				return false;
			}
		}
	}

	public void unlock() {
		int oldState = 0;
		int newState = (int) threadID.get() / 2;
		// successorMustWait = true;
		newState |= SMW_MASK;
		// tailWhenSpliced = false;
		newState &= (~TWS_MASK);
		do {
			oldState = state.get();
		} while (!state.compareAndSet(oldState, newState));
	}

	public int getClusterID() {
		return state.get() & CLUSTER_MASK;
	}

	public void setClusterID(int clusterID) {
		int oldState, newState;
		do {
			oldState = state.get();
			newState = (oldState & ~CLUSTER_MASK) | clusterID;
		} while (!state.compareAndSet(oldState, newState));
	}

	public boolean isSuccessorMustWait() {
		return (state.get() & SMW_MASK) != 0;
	}

	public void setSuccessorMustWait(boolean successorMustWait) {
		int oldState, newState;
		do {
			oldState = state.get();
			if (successorMustWait) {
				newState = oldState | SMW_MASK;
			} else {
				newState = oldState & ~SMW_MASK;
			}
		} while (!state.compareAndSet(oldState, newState));
	}

	public boolean isTailWhenSpliced() {
		return (state.get() & TWS_MASK) != 0;
	}

	public void setTailWhenSpliced(boolean tailWhenSpliced) {
		int oldState, newState;
		do {
			oldState = state.get();
			if (tailWhenSpliced) {
				newState = oldState | TWS_MASK;
			} else {
				newState = oldState & TWS_MASK;
			}
		} while (!state.compareAndSet(oldState, newState));
	}
}