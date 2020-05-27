package concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Taskportion implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private static Random rand = new Random(47);
    private final CountDownLatch latch;

    public Taskportion(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException e) {
            //Acceptable way to exit
        }

    }

    public void doWork() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep( rand.nextInt( 2000 ) );
        System.out.println( this + "completed" );
    }

    @Override
    public String toString() {
        return String.format( "%1$-3d", id );
    }
}

//Waits on the CountDownLatch:
class WaitingTask implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;
    WaitingTask(CountDownLatch latch){
        this.latch = latch;
    }
    public void run(){
        try {
            latch.await();
            System.out.println( "Latch barrier passed for " + this );
        } catch (InterruptedException e) {
            System.out.println( this + " interrupted" );
        }
    }

    public String toString(){
        return String.format( "WaitingTask %1$-3d", id );
    }
}
public class CountDownlatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch( SIZE );
        for (int i = 0; i < 10; i++)
            exec.execute( new WaitingTask( latch ) );
        for (int i = 0; i < SIZE; i++) {
            exec.execute( new Taskportion( latch ) );
        }
        System.out.println( "Launched all tasks" );
        exec.shutdown();
    }
}
