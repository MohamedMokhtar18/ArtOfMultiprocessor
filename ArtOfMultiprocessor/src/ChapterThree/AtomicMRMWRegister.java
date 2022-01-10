package ChapterThree;

public class AtomicMRMWRegister<T> implements Register<T>{
private StampedValue<T>[] a_table; // array of atomic MRSW registers
public AtomicMRMWRegister(int capacity, T init) {
a_table = (StampedValue<T>[]) new StampedValue[capacity];
StampedValue<T> value = new StampedValue<T>(init);
for (int j = 0; j < a_table.length; j++) {
a_table[j] = value;
}
}
 public void write(T value) {
 int me = (int) Thread.currentThread().getId();
 StampedValue<T> max = StampedValue.MIN_VALUE;
 for (int i = 0; i < a_table.length; i++) {
 max = StampedValue.max(max, a_table[i]);
 }
 a_table[me] = new StampedValue(max.stamp + 1, value);
 }
 public T read() {
 StampedValue<T> max = StampedValue.MIN_VALUE;
 for (int i = 0; i < a_table.length; i++) {
 max = StampedValue.max(max, a_table[i]);
 }
 return max.value;
 }
 }