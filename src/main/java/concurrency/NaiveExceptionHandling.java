package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NaiveExceptionHandling {
    public static void main(String[] args) {
        ExecutorService exec = null;
        try {
            exec = Executors.newCachedThreadPool();
            exec.execute( new ExceptionThread() );
        } catch (Exception e) {
            //This statement will Not execute!
            System.out.println( "Exception has been handled!" );

        }
    }
}
