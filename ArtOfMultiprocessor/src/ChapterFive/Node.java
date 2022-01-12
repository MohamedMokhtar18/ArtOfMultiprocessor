package ChapterFive;

import java.lang.Comparable;
import java.util.LinkedList;

import ChapterFour.Consensus;
import ChapterFour.QueueConsensus;

public class Node implements Comparable {
	public Invoc invoc; // method name and args
	public Consensus<Node> decideNext; // decide next Node in list
	public Node next; // the next node
	public int seq; // sequence number

	public Node(Invoc invoc) {
		invoc = invoc;
		decideNext = new QueueConsensus();
		// decideNext = new ConsensusProtocol<Node>(); it wouldn't compile
		seq = 0;
	}

	public static Node max(Node[] array) {
		Node max = array[0];
		for (int i = 1; i < array.length; i++)
			if (max.seq < array[i].seq)
				max = array[i];
		return max;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Node max(LinkedList<Node> array) {
		Node max = array.get(0);
		for (int i = 1; i < array.size(); i++)
			if (max.seq < array.get(i).seq)
				max = array.get(i);
		return max;
	}
}
