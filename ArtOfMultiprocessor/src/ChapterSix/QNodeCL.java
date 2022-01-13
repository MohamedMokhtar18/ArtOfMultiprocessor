package ChapterSix;

import java.util.concurrent.atomic.AtomicReference;

enum State {
	FREE, WAITING, RELEASED, ABORTED
};

class QNodeCL {
	AtomicReference<State> state;
	QNodeCL pred;

	public QNodeCL() {
		state = new AtomicReference<State>(State.FREE);
	}
}
