package concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

class LiftOffRunner implements Runnable{
    private BlockingQueue<LiftOff> rockets;

    public LiftOffRunner(BlockingQueue<LiftOff> rockets) {
        this.rockets = rockets;
    }

    public void run() {
        try {
            while(!Thread.interrupted()){
                LiftOff rocket = rockets.take();
                rocket.run();
            }
        } catch (InterruptedException e) {
            System.out.println( "Waking from take()" );
        }
        System.out.println( "Exiting LiftOffRunner" );
    }
}

class LiftOffRunner2 implements Runnable{
    private BlockingQueue<LiftOff> rockets;

    public LiftOffRunner2(BlockingQueue<LiftOff> rockets) {
        this.rockets = rockets;
    }


    public void run() {
        try {
            while(!Thread.interrupted()){
                rockets.put( new LiftOff( 5 ) );
                TimeUnit.SECONDS.sleep( 2 );
            }
        } catch (InterruptedException e) {
            System.out.println( "Waking from take()" );
        }
        System.out.println( "Exiting LiftOffRunner" );
    }
}
public class TestBlockingQueues {
    static void getKey(){
        try {
            new BufferedReader( new InputStreamReader( System.in ) ).readLine();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }
    static void getKey(String message){
        System.out.print( message );
        getKey();
    }

    static void test(String msg, BlockingQueue<LiftOff> queue){
        System.out.println( msg );
        LiftOffRunner runner = new LiftOffRunner( queue );
        LiftOffRunner2 runner2 = new LiftOffRunner2( queue );
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute( runner );
        executorService.execute( runner2 );
        getKey("Please 'Enter' (" + msg +")");
        executorService.shutdownNow();
        System.out.println( "Finished " + msg + " test" );
    }

    public static void main(String[] args) {
        test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>(  ) );
        test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>( 3 ) );
        test("SynchronousQueue", new SynchronousQueue<LiftOff>(  ) );
    }
}
