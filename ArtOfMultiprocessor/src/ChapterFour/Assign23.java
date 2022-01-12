package ChapterFour;

public class Assign23<T> {
	int[] r = new int[3];

	public Assign23(int init) {
		for (int i = 0; i < r.length; i++)
			r[i] = init;
	}

	public synchronized void assign(T v0, T v1, int i0, int i1) {
		r[i0] = (int) v0;
		r[i1] = (int) v1;
	}

	public synchronized int read(int i) {
		return r[i];
	}
}
