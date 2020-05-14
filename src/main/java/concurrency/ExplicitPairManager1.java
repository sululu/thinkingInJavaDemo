package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitPairManager1 extends PairManager {
    private Lock lock = new ReentrantLock(  );
    public  void increment(){
        lock.lock();
        try{
            p.incrementX();
            p.incrementY();
            store( getPair() );
        }finally {
            lock.unlock();
        }
    }
}
