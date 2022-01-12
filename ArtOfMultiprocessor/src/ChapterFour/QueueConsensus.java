package ChapterFour;

import java.util.Queue;

public class QueueConsensus<T> extends ConsensusProtocol<T> {
	private static final int WIN = 0; // first thread
	private static final int LOSE = 1; // second thread
	Queue<Integer> queue;

// initialize queue with two items
	public QueueConsensus(int N) {
		super(N);
		queue.add(WIN);
		queue.add(LOSE);

	}

	// figure out which thread was first
	public T decide(T Value) {
		propose(Value);
		int status = queue.peek();
		int i = (int) Thread.currentThread().getId();
		if (status == WIN)
			return proposed[i];
		else
			return proposed[1 - i];
	}
}
