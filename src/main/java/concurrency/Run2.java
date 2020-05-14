package concurrency;

public class Run2 {

    public static void main(String[] args) {
        ObjectService service = new ObjectService();
        ThreadC c = new ThreadC( service );
        c.setName( "a" );
        c.start();

        ThreadD d = new ThreadD( service );
        d.setName( "b" );
        d.start();

    }


}

class ObjectService{
   public void serviceMethod(){
       try{
           synchronized (this){
               System.out.println( "begin time=" + System.currentTimeMillis() );
               Thread.sleep( 2000 );
               System.out.println( "end time=" + System.currentTimeMillis() );
           }
       }catch(InterruptedException e){
           e.printStackTrace();
       }
   }
}

class ThreadC extends Thread{
    private ObjectService service;
    public ThreadC(ObjectService service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        super.run();
        service.serviceMethod();
    }
}

class ThreadD extends Thread{
    private ObjectService service;

    public ThreadD(ObjectService service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.serviceMethod();
    }
}