package ChapterFour;

import java.util.concurrent.atomic.AtomicInteger;

class CASConsensus extends ConsensusProtocol {
	public CASConsensus(int N) {
		super(N);
		// TODO Auto-generated constructor stub
	}

	private final int FIRST = -1;
	private AtomicInteger r = new AtomicInteger(FIRST);

	public Object decide(Object value) {
		propose(value);
		int i = (int) Thread.currentThread().getId();
		if (r.compareAndSet(FIRST, i)) // I won
			return proposed[i];
		else // I lost
			return proposed[r.get()];
	}
}
