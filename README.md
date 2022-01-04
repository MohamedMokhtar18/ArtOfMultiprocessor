# ArtOfMultiprocessor
## Chapter One
### Lock One
The LockOne algorithm is inadequate because it deadlocks if thread executions
are interleaved. If writeA(flag[A] = true) and writeB(flag[B] = true) events
occur before readA(flag[B]) and readB(flag[A]) events, then both threads wait
forever. Nevertheless, LockOne has an interesting property: if one thread runs
before the other, no deadlock occurs, and all is well.
### Lock Two
The LockTwo class is inadequate because it deadlocks if one thread runs
completely ahead of the other. Nevertheless, LockTwo has an interesting property: if the threads run concurrently, the lock() method succeeds. The LockOne
and LockTwo classes complement one another: each succeeds under conditions
that cause the other to deadlock.
### The Peterson Lock
We now combine the LockOne and LockTwo algorithms to construct a starvation-free Lock algorithm
#### The Peterson lock algorithm is starvation-free
Proof: Suppose not. Suppose (without loss of generality) that A runs forever
in the lock() method. It must be executing the while statement, waiting until
either flag[B] becomes false or victim is set to B.
What is B doing while A fails to make progress? Perhaps B is repeatedly entering and leaving its critical section. If so, however, then B sets victim to B as soon
as it reenters the critical section. Once victim is set to B, it does not change, and
A must eventually return from the lock() method, a contradiction.
So it must be that B is also stuck in its lock() method call, waiting until either
flag[A] becomes false or victim is set to A. But victim cannot be both A and
B, a contradiction. 
####  The Peterson lock algorithm is deadlock-free
### The Filter Lock
We now consider two mutual exclusion protocols that work for n threads, where
n is greater than 2. The first solution, the Filter lock, is a direct generalization
of the Peterson lock to multiple threads. The second solution, the Bakery lock,
is perhaps the simplest and best known n-thread solution.

The Peterson lock uses a two-element boolean flag array to indicate
whether a thread is trying to enter the critical section. The Filter lock generalizes this notion with an n-element integer level[] array, where the value of
level[A] indicates the highest level that thread A is trying to enter. Each thread
must pass through n − 1 levels of “exclusion” to enter its critical section. Each
level ` has a distinct victim[`] field used to “filter out” one thread, excluding it
from the next level
#### The Filter lock algorithm satisfies mutual exclusion
#### The Filter lock algorithm is starvation-free.
Proof: We argue by reverse induction on the levels. The base case, level n − 1, is
trivial, because it contains at the most one thread. For the induction hypothesis,
assume that every thread that reaches level j + 1 or higher, eventually enters (and
leaves) its critical section.
Suppose A is stuck at level j. Eventually, by the induction hypothesis, there
are no threads at higher levels. Once A sets level[A] to j, then any thread at
level j − 1 that subsequently reads level[A] is prevented from entering level j.
Eventually, no more threads enter level j from lower levels. All threads stuck at
level j are in the waiting loop at Line 17, and the values of the victim and level
fields no longer change.
We now argue by induction on the number of threads stuck at level j. For the
base case, if A is the only thread at level j or higher, then clearly it will enter level
j + 1. For the induction hypothesis, we assume that fewer than k threads cannot
be stuck at level j. Suppose threads A and B are stuck at level j. A is stuck as long as it reads victim[j] = A, and B is stuck as long as it reads victim[j] = B.
The victim field is unchanging, and it cannot be equal to both A and B, so one
of the two threads will enter level j + 1, reducing the number of stuck threads to
k − 1, contradicting the induction hypothesis. 
##### The Filter lock algorithm is deadlock-free
### Fairness
A lock is first-come-first-served if, whenever, thread A finishes
its doorway before thread B starts its doorway, then A cannot be overtaken by B

The starvation-freedom property guarantees that every thread that calls lock()
eventually enters the critical section, but it makes no guarantees about how long
this may take. Ideally (and very informally) if A calls lock() before B, then A
should enter the critical section before B. Unfortunately, using the tools at hand
we cannot determine which thread called lock() first. Instead, we split the lock()
method into two sections of code (with corresponding execution intervals):
1.A doorway section, whose execution interval DA consists of a bounded number of steps, and
2.waiting section, whose execution interval WA may take an unbounded number of steps.
