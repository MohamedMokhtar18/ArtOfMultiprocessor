package ChapterTwo;
/*A single-enqueuer/single-dequeuer FIFO queue. The structure is identical to that
of the lock-based FIFO queue, except that there is no need for the lock to coordinate access*/
class WaitFreeQueue<T> {
volatile int head = 0, tail = 0;
T[] items;
public WaitFreeQueue(int capacity) {
items = (T[]) new Object[capacity];
}
public void enq(T x) throws Exception {
if (tail - head == items.length)
throw new Exception();
items[tail % items.length] = x;
tail++;
}
public T deq() throws Exception {
if (tail - head == 0)
throw new Exception();
T x = items[head % items.length];
head++;
return x;
}
}