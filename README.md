# ArtOfMultiprocessor
## Chapter One
### LockOne
The LockOne algorithm is inadequate because it deadlocks if thread executions
are interleaved. If writeA(flag[A] = true) and writeB(flag[B] = true) events
occur before readA(flag[B]) and readB(flag[A]) events, then both threads wait
forever. Nevertheless, LockOne has an interesting property: if one thread runs
before the other, no deadlock occurs, and all is well.
