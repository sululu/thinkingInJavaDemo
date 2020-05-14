package concurrency;

public class Run {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef = new HasSelfPrivateNum();
        ThreadA threadA = new ThreadA( numRef );
        threadA.start();
        ThreadB threadB = new ThreadB( numRef );
        threadB.start();
    }
}
class Run_2{
    public static void main(String[] args) {
        HasSelfPrivateNum numRef1 = new HasSelfPrivateNum();
        HasSelfPrivateNum numRef2 = new HasSelfPrivateNum();

        ThreadA threadA = new ThreadA( numRef1 );
        threadA.start();
        ThreadB threadB = new ThreadB( numRef2 );
        threadB.start();


    }
}

class HasSelfPrivateNum{
    private int num = 0;
    synchronized  public void addI(String username){
        try {
            if(username.equals( "a" )){
                num = 100;
                System.out.println( "a set over!" );
                Thread.sleep( 2000 );
            }else{
                num = 200;
                System.out.println( "b set over!" );
            }
            System.out.println( username + " num = " + num );
        } catch (InterruptedException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class ThreadA extends Thread{
    private HasSelfPrivateNum numRef;

    public ThreadA(HasSelfPrivateNum numRef){
        super();
        this.numRef = numRef;
    }

    @Override
    public void run(){
        super.run();
        numRef.addI( "a" );
    }
}

class ThreadB extends Thread{
    private HasSelfPrivateNum numRef;
    public ThreadB(HasSelfPrivateNum numRef){
        super();
        this.numRef = numRef;
    }

    @Override
    public void run(){
        super.run();
        numRef.addI("b");
    }
}