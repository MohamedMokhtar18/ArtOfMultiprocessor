package ChapterFive;

public class LFUniversal {
	private Node[] head;
	private Node tail;

	public LFUniversal(int n) {
		tail = new Node(null);
		tail.seq = 1;
		for (int i = 0; i < n; i++)
			head[i] = tail;
	}

	public Response apply(Invoc invoc) {
		int i = (int) Thread.currentThread().getId();
		Node prefer = new Node(invoc);
		while (prefer.seq == 0) {
			Node before = Node.max(head);
			Node after = before.decideNext.decide(prefer);
			before.next = after;
			after.seq = before.seq + 1;
			head[i] = after;
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
