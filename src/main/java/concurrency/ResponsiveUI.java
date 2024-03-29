package concurrency;

class UnresponsiveUI{
    private volatile double d = 1;
    public UnresponsiveUI() throws Exception{
        while(d > 0)
            d = d + (Math.PI + Math.E) / d;
        System.in.read(); //Never gets here
    }

    public static void main(String[] args)  {
        try {
            UnresponsiveUI unresponsiveUI = new UnresponsiveUI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
public class ResponsiveUI extends Thread{
    private static volatile double d = 1;

    public ResponsiveUI(){
        setDaemon(true);
        start();
    }
    public void run(){
        while(true){
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws Exception{
        //! new UnresponsiveUI(); //Must kill this process
        new ResponsiveUI();
        System.in.read();
        System.out.println(d); //Show progress
    }
}
