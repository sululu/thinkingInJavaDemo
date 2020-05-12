package concurrency;

public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    public int next(){
        ++currentEvenValue; //Danger point here!
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test( new EvenGenerator() );
    }
}
