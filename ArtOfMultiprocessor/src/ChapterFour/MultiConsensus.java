package ChapterFour;

public class MultiConsensus<T> extends ConsensusProtocol<T> {
public MultiConsensus(int N) {
		super(N);
		// TODO Auto-generated constructor stub
	}
private final int NULL = -1;
Assign23 assign23 = new Assign23(NULL);
public T decide(T value) {
propose(value);
int i = (int) Thread.currentThread().getId();
int j = 1-i;
// double assignment
assign23.assign(i, i, i, i+1);
 int other = assign23.read((i+2) % 3);
 if (other == NULL || other == assign23.read(1))
 return proposed[i]; // I win
 else
 return proposed[j]; // I lose
 }
 }