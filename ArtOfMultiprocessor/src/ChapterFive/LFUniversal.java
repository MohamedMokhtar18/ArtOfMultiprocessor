package ChapterFive;

import java.util.LinkedList;

public class LFUniversal {
	private LinkedList<Node> head = new LinkedList<Node>();
	// private Node[] head;
	private Node tail;

	public LFUniversal(Invoc invoc) {
		tail = new Node(invoc);
		tail.seq = 1;
		// for (int i = 0; i < n; i++)
		head.add(tail);
	}

	public Response apply(Invoc invoc) {
		int i = (int) Thread.currentThread().getId();
		Node prefer = new Node(invoc);
		while (prefer.seq == 0) {
			Node before = Node.max(head);
			Node after = before.decideNext.decide(prefer);
			before.next = after;
			after.seq = before.seq + 1;
			// head[i] = after;
			head.get(i);
		}
		SeqObject myObject = null;// new SeqObject(); won't compile
		Node current = tail.next;
		while (current != prefer) {
			myObject.apply(current.invoc);
			current = current.next;
		}
		return myObject.apply(current.invoc);
	}
}
