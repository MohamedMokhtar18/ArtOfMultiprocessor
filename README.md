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
What is B doing while A fails to make progress? Perhaps B is repeatedly entering and leaving its critical section. If so, however, then B sets victim to B as soon
as it reenters the critical section. Once victim is set to B, it does not change, and
A must eventually return from the lock() method, a contradiction.
So it must be that B is also stuck in its lock() method call, waiting until either
flag[A] becomes false or victim is set to A. But victim cannot be both A and
B, a contradiction. 
