package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast{
    public enum Status{DRY, BUTTERED, BUTTERED2, JAMMED}
    private Status status = Status.DRY;
    private final int id;

    public Toast(int id) {
        this.id = id;
    }
    public void butter(){status = Status.BUTTERED;}
    public void butter2(){status = Status.BUTTERED2;}
    public void jam(){status = Status.JAMMED;}
    public Status getStatus(){return status;}
    public int getId(){return id;}

    @Override
    public String toString() {
        return "Toast{" +
                "status=" + status +
                ", id=" + id +
                '}';
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast>{}

class Toaster implements Runnable{
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random( 47 );
    public Toaster(ToastQueue tq){toastQueue = tq;}

    public void run() {
        try {
            while(!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep( 100 + rand.nextInt( 500 ) );
                //Make toast
                Toast t = new Toast( count++ );
                System.out.println( t );
                //Insert into queue
                toastQueue.put( t );
            }
        } catch (InterruptedException e) {
            System.out.println( "Toaster interrupted" );
        }
        System.out.println( "Toaster off" );
    }
}

//Apply butter to toast:
class Butterer implements Runnable{
    private ToastQueue dryQueue, butteredQueue;

    public Butterer(ToastQueue dryQueue, ToastQueue butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    public void run() {
        try {
            while(!Thread.interrupted()){
                Toast t = dryQueue.take();
                t.butter();
                System.out.println( t );
                butteredQueue.put( t );
            }
        } catch (InterruptedException e) {
            System.out.println( "Butter interrupted" );
        }
        System.out.println( "Butterer off" );
    }
}

//Apply butter to toast:
class Butterer2 implements Runnable{
    private ToastQueue dryQueue, butteredQueue;

    public Butterer2(ToastQueue dryQueue, ToastQueue butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    public void run() {
        try {
            while(!Thread.interrupted()){
                Toast t = dryQueue.take();
                t.butter2();
                System.out.println( t );
                butteredQueue.put( t );
                TimeUnit.MILLISECONDS.sleep( 500 );
            }
        } catch (InterruptedException e) {
            System.out.println( "Butter interrupted" );
        }
        System.out.println( "Butterer off" );
    }
}

//Apply jam to buttered toast:
class Jammer implements Runnable{
    private ToastQueue butteredQueue, finishedQueue;

    public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    public void run(){
        try {
            while(!Thread.interrupted()){
                Toast t = butteredQueue.take();
                t.jam();
                System.out.println( t );
                finishedQueue.put( t );
            }
        } catch (InterruptedException e) {
            System.out.println( "Jammer interrupted" );
        }
        System.out.println( "Jammer off" );
    }
}

//Consumer the toast:
class Eater implements Runnable{
    private ToastQueue finishedQueue;

    private int counter  =0;
    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    public void run() {
        try {
            while(!Thread.interrupted()){
                Toast t = finishedQueue.take();
                if(t.getId() != counter++ || t.getStatus() != Toast.Status.JAMMED){
                    System.out.println(">>>> Error: " + t);
                    System.exit(-1);
                }else{
                    System.out.println( "Chomp! " + t );
                }
            }
        } catch (InterruptedException e) {
            System.out.println( "Eater interrupted" );
        }
        System.out.println( "Eater off" );
    }
}
public class ToastOMatic {
    public static void main(String[] args) throws Exception{
        ToastQueue dryQueue = new ToastQueue(),
                butteredQueue = new ToastQueue(),
                finishedQueue = new ToastQueue();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute( new Toaster( dryQueue ) );
        executorService.execute( new Butterer( dryQueue, butteredQueue ) );
        executorService.execute( new Butterer2( dryQueue, butteredQueue ) );
        executorService.execute( new Jammer( butteredQueue,finishedQueue ) );
        executorService.execute( new Eater( finishedQueue ) );
        TimeUnit.SECONDS.sleep( 5 );
        executorService.shutdownNow();
    }
}
