package ChapterThree;

public class AtomicMRSWRegister<T> implements Register<T> {
	ThreadLocal<Long> lastStamp;
	private StampedValue<T>[][] a_table; // each entry is SRSW atomic

	public AtomicMRSWRegister(T init, int readers) {
		lastStamp = new ThreadLocal<Long>() {
			protected Long initialValue() {
				return (long) 0;
			};
		};
		a_table = (StampedValue<T>[][]) new StampedValue[readers][readers];
		StampedValue<T> value = new StampedValue<T>(init);
		for (int i = 0; i < readers; i++) {
			for (int j = 0; j < readers; j++) {
				a_table[i][j] = value;
			}
		}
	}

	public T read() {
		int me = (int) Thread.currentThread().getId();
		StampedValue<T> value = a_table[me][me];
		for (int i = 0; i < a_table.length; i++) {
			value = StampedValue.max(value, a_table[i][me]);
		}
		for (int i = 0; i < a_table.length; i++) {
			if (i == me)
				continue;
			a_table[me][i] = value;
		}
		return (T) value;
	}

	public void write(T v) {
		long stamp = lastStamp.get() + 1;
		lastStamp.set(stamp);
		StampedValue<T> value = new StampedValue<T>(stamp, v);
		for (int i = 0; i < a_table.length; i++) {
			a_table[i][i] = value;
		}
	}
}
