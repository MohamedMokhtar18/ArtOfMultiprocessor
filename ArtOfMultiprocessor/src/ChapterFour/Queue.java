package ChapterFour;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Queue {
	AtomicInteger head = new AtomicInteger(0);
	AtomicReference items[] = new AtomicReference[Integer.MAX_VALUE];

	void enq(Object x) {
		int slot = head.getAndIncrement();
		items[slot] = (AtomicReference) x;
	}

	@SuppressWarnings("unchecked")
	Object deq() {
		while (true) {
			int limit = head.get();
			for (int i = 0; i < limit; i++) {
				Object y = new Object();
				items[i].getAndSet(y); // swap
				if (y != null)
					return y;
			}
		}
	}
}