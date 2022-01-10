package ChapterThree;

public class RegBooleanMRSWRegister implements Register<Boolean> {
	ThreadLocal<Boolean> last;
	boolean s_value; // safe MRSW register

	RegBooleanMRSWRegister(int capacity) {
		last = new ThreadLocal<Boolean>() {
			protected Boolean initialValue() {
				return false;
			};
		};
	}

	public void write(Boolean x) {
		if (x != last.get()) {
			last.set(x);
			s_value = x;
		}
	}

	public Boolean read() {
		return s_value;
	}
}
