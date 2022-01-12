package ChapterFour;

import java.util.ArrayList;

public abstract class ConsensusProtocol<T>  
implements Consensus<T> {
private int N;
protected ArrayList<T> proposed = new ArrayList<T>();

protected void propose(T value) {
proposed.add((int) Thread.currentThread().getId(), value);
}

abstract public T decide(T value);
}
