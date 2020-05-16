package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Sll{
    private volatile double d = 0.0;
    public synchronized void One(){
        try {
           if(true){
               wait();
               for (int i = 0; i < 1000000; i++) {
                   d = d + (Math.PI + Math.E) / d;
                   System.out.print("d");
               }
               System.out.println();
           }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void Tow(){
        notifyAll();
    }
}

class SllRun implements Runnable{
    public static Sll sll = new Sll();
    public void run() {
        sll.One();
    }
}
public class Test {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        SllRun r = new SllRun();
        exec.execute( r );
        TimeUnit.MILLISECONDS.sleep( 10 );
        SllRun.sll.Tow();

        exec.shutdownNow();

    }
}
