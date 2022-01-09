package ChapterTwo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/* A lock-based FIFO queue. The queue’s items are kept in an array items, where
head is the index of the next item to dequeue, and tail is the index of the first open array
slot (modulo the capacity). The lock field is a lock that ensures that methods are mutually
exclusive. Initially head and tail are zero, and the queue is empty. If enq() finds the queue
is full, i.e., head and tail differ by the queue size, then it throws an exception. Otherwise,
there is room, so enq() stores the item at array entry tail, and then increments tail. The
deq() method works in a symmetric w*/
class LockBasedQueue<T> {
int head, tail;
T[] items;
Lock lock;
public LockBasedQueue(int capacity) {
head = 0; tail = 0;
lock = new ReentrantLock();
items = (T[])new Object[capacity];
}
 public void enq(T x) throws Exception {
 lock.lock();
 try {
 if (tail - head == items.length)
 throw new Exception();
 items[tail % items.length] = x;
 tail++;
 } finally {
 lock.unlock();
 }
 }
 public T deq() throws NullPointerException {
 lock.lock();
 try {
 if (tail == head)
 throw new NullPointerException();
 T x = items[head % items.length];
 head++;
 return x;
 } finally {
 lock.unlock();
 }
 }
 }