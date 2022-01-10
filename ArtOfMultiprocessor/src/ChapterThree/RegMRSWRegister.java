package ChapterThree;

public class RegMRSWRegister implements Register<Byte> {
	private static int RANGE = Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
	boolean[] r_bit = new boolean[RANGE]; // regular boolean MRSW

	public RegMRSWRegister(int capacity) {
		for (int i = 1; i < r_bit.length; i++)
			r_bit[i] = false;
		r_bit[0] = true;
	}

	public void write(Byte x) {
		r_bit[x] = true;
		for (int i = x - 1; i >= 0; i--)
			r_bit[i] = false;
	}

	public Byte read() {
		for (int i = 0; i < RANGE; i++)
			if (r_bit[i]) {
				return (byte) i;
			}
		return -1; // impossible
	}
}
