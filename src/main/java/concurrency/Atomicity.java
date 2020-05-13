package concurrency;

public class Atomicity {
    int i;
    synchronized void f1(){i++;}
    synchronized void f2(){i += 3;}
}
