package ChapterFive;

import java.util.LinkedList;

public class Universal {
	private Node[] announce; // array added to coordinate helping
	private Node[] head;
	// private LinkedList<Node> head = new LinkedList<Node>();
	private Node tail;
	private int n;
	private Node prefer;

	public Universal(Invoc invoc) {
		tail = new Node(invoc);
		tail.seq = 1;
		for (int j = 0; j < n; j++) {
			head[j] = tail;
		}
	}

	public Response apply(Invoc invoc) {
		int i = (int) Thread.currentThread().getId();
		announce[i] = new Node(invoc);
		head[i] = Node.max(head);
		while (announce[i].seq == 0) {
			Node before = head[i];
			Node help = announce[(before.seq + 1) % n];
			if (help.seq == 0)
				prefer = help;
			else
				prefer = announce[i];
			Node after = before.decideNext.decide(prefer);
			before.next = after;
			after.seq = before.seq + 1;
			head[i] = after;
		}
		SeqObject MyObject =null; //new SeqObject();
		Node current = tail.next;
		while (current != announce[i]) {
			MyObject.apply(current.invoc);
			current = current.next;
		}
		head[i] = announce[i];
		return MyObject.apply(current.invoc);
	}
}
