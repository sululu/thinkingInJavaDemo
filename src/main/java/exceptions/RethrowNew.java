package exceptions;

class OneException extends Exception{
    public OneException(String message) {
        super(message);
    }
}
class TwoException extends Exception{
    public TwoException(String message) {
        super(message);
    }
}
public class RethrowNew {
    public static void f() throws OneException{
        System.out.println("originating the exception in f()");
        throw new OneException("trhown from f()");
    }
    public static void main(String[] args){
        try{
            try{
                f();
            }catch(OneException e){
                System.out.println("Caught in inner try, e.printStackTrace()");
                e.printStackTrace();
                throw new TwoException("from inner try");
            }
        }catch (TwoException e){
            System.out.println("Caught in outer try, e.printStackTrace");
            e.printStackTrace(System.out);
        }
    }

}
