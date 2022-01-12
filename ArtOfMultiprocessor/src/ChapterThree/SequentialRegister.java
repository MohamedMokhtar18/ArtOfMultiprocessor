package ChapterThree;

public class SequentialRegister<T> implements Register<T> {
	private T value;

	public T read() {
		return value;
	}

	public void write(T v) {
		value = v;
	}
}