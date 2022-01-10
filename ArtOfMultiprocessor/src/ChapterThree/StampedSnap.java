package ChapterThree;

public class StampedSnap<T> {
	public long stamp;
	public T value;
	public T[] snap;

	public StampedSnap(T value) {
		stamp = 0;
		value = value;
		snap = null;
	}

	public StampedSnap(long label, T value, T[] snap) {
		stamp = label;
		value = value;
		snap = snap;
	}
}
