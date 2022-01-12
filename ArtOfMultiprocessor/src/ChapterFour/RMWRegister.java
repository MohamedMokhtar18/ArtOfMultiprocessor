package ChapterFour;

import ChapterThree.SequentialRegister;

public abstract class RMWRegister {
	private int value;
	SequentialRegister s = new SequentialRegister();

	public int getAndMumble() {
		int prior = value;
		value = mumble(value);
		return prior;
	}

	private int mumble(int value2) {
		s.write(value2);
		return (int) s.read();
	}

	private int mumble() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int read() {
		int prior = value;
		value = value;
		return prior;
	}

	public int getAndSet(int v) {
		int prior = value;
		value = v;
		return prior;
	}

	public int getAndIncrement() {
		int prior = value;
		value = value + 1;
		return prior;
	}

	public int getAndAdd(int a) {
		int prior = value;
		value = value + a;
		return prior;
	}

	public boolean compareAndSet(int expected, int update) {
		if (value == expected) {
			value = update;
			return true;
		}
		return false;
	}
}
