package ChapterFour;

public class QueueConsensus<T> extends ConsensusProtocol<T> {
	private static final int WIN = 0; // first thread
	private static final int LOSE = 1; // second thread
	Queue queue;

// initialize queue with two items
	public QueueConsensus() {
		queue = new Queue();
		queue.enq(WIN);
		queue.enq(LOSE);
	}

	// figure out which thread was first
	public T decide(T Value) {
		propose(Value);
		int status = (int) queue.deq();
		int i = (int) Thread.currentThread().getId();
		if (status == WIN)
			return proposed.get(i);
		else
			return proposed.get(1 - i);
	}
}
