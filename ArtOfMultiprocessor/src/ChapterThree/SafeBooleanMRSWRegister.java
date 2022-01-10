package ChapterThree;

public class SafeBooleanMRSWRegister implements Register<Boolean> {
	boolean[] s_table; // array of safe SRSW registers

	public SafeBooleanMRSWRegister(int capacity) {
		s_table = new boolean[capacity];
	}

	public Boolean read() {
		return s_table[(int) Thread.currentThread().getId()];
	}

	public void write(Boolean x) {
		for (int i = 0; i < s_table.length; i++)
			s_table[i] = x;
	}
}
