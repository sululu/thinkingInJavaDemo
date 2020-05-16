package concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.*;

class Sender implements Runnable{
    private Random rand = new Random(  );
    private PipedWriter out = new PipedWriter(  );
    private BlockingQueue<Character> queue = new LinkedBlockingQueue<Character>(  );

    public PipedWriter getPipedWriter(){return out;}

    public Sender(BlockingQueue<Character> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while(true){
                for(char c = 'A'; c <= 'z'; c++){
                   queue.put( c );
                    // out.write( c );
                    TimeUnit.MILLISECONDS.sleep( rand.nextInt( 500 ) );
                }
            }
        } /*catch (IOException e) {
            System.out.println( e + " Sender write exception" );
        }*/ catch (InterruptedException e) {
            System.out.println( e + " Sender sleep interrupted" );
        }
    }
}

class Receiver implements Runnable{
    private PipedReader in;
    private BlockingQueue<Character> queue = new LinkedBlockingQueue<Character>(  );

    public Receiver(Sender sender) throws IOException {
        this.in = new PipedReader( sender.getPipedWriter() );
    }

    public Receiver(BlockingQueue<Character> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while(true){
              //  System.out.println( "Read: " + (char)in.read() + "," );
                System.out.println( "Read: " + queue.take() );
            }
        } catch (InterruptedException e) {
            System.out.println( e + " Receiver read exception" );
        }
    }
}
public class PipedIO {
    public static void main(String[] args) throws Exception{
        LinkedBlockingQueue queue = new LinkedBlockingQueue(  );
        Sender sender = new Sender(queue);
        Receiver receiver = new Receiver( queue );
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute( sender );
        executorService.execute( receiver );
        TimeUnit.SECONDS.sleep( 4 );
        executorService.shutdownNow();
    }
}
