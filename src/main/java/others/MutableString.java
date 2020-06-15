package others;

import java.util.ArrayList;

class StringCreator extends Thread{
    MutableString ms;
    public StringCreator(MutableString muts){
        ms = muts;
    }
    public void run(){
        while(true)
            ms.str = new String("hello");
    }
}

class StringReader extends Thread{
    MutableString ms;
    public StringReader(MutableString muts){
        ms = muts;
    }

    public void run(){
        while(true){
            if(!(ms.str.equals("hello"))){
                System.out.println("String is not immutable!");
                break;
            }
        }
    }
}
public class MutableString {
    public String str;

    public static void main(String[] args) {
        MutableString ms = new MutableString();
        new StringCreator( ms ).start();
        new StringReader( ms ).start();
    }
}
