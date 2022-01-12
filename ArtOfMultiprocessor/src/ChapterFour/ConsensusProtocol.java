package ChapterFour;

public abstract class ConsensusProtocol<T> implements Consensus<T> {
	int N;
	protected T[] proposed = (T[]) new Object[N];

// announce my input value to the other threads
	void propose(T value) {
		proposed[(int) Thread.currentThread().getId()] = value;
	}

	public ConsensusProtocol(int N) {
		this.N = N;
	}

// figure out which thread was first
	abstract public T decide(T value);
}