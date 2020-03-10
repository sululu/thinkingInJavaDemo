package exceptions;

public class Test {
    public static StringBuffer testFinally(){
       StringBuffer s = new StringBuffer( "Hello" );
       try{
           return s;
       }catch(Exception e){
           return null;
       }finally {
           s.append( "World" );
           System.out.println( "execute finally2" );
       }
    }
    public static void main(String[] args){
        StringBuffer resultRef = testFinally();
        System.out.println( resultRef );
    }
}
