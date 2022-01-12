package ChapterFour;

public class RMWConsensus<T> extends ConsensusProtocol {
	private RMWRegister v;

	public RMWConsensus(int N) {
		super(N);
		// TODO Auto-generated constructor stub
	}

	private RMWRegister r = v;

	public T decide(Object value) {
		propose(value);
		if (r.getAndMumble() == v.getAndMumble())
			return (T) proposed[(int) Thread.currentThread().getId()];
		else
			return (T) proposed[1 - (int) Thread.currentThread().getId()];
	}
}
