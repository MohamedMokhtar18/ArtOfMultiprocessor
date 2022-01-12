package ChapterFour;

public class RMWConsensus<T> extends ConsensusProtocol {
	private RMWRegister v;

	public RMWConsensus(int N) {
		// TODO Auto-generated constructor stub
	}

	private RMWRegister r = v;

	public T decide(Object value) {
		propose(value);
		if (r.getAndMumble() == v.getAndMumble())
			return (T) proposed.get((int) Thread.currentThread().getId());
		else
			return (T) proposed.get(1-(int) Thread.currentThread().getId());
	}
}
