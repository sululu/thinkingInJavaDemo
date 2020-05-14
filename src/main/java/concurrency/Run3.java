package concurrency;

import javax.xml.ws.Service;

public class Run3 {
    public static void main(String[] args) {
        ThreadE e = new ThreadE();
        e.setName( "E" );
        e.start();

        ThreadF f = new ThreadF();
        f.setName( "F" );
        f.start();
    }
}

class Service3{
    /*synchronized public static void printA(){
        try {
            System.out.println( "线程名称为：" + Thread.currentThread().getName() +
                    "在" + System.currentTimeMillis() + "进入printA");
            Thread.sleep( 3000 );
            System.out.println( "线程名称为：" + Thread.currentThread().getName() +
                    "在" + System.currentTimeMillis() + "离开printA");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    synchronized public static void printB(){
        System.out.println( "线程名称为：" + Thread.currentThread().getName() +
                "在" + System.currentTimeMillis() + "进入printB");
        System.out.println( "线程名称为：" + Thread.currentThread().getName() +
                "在" + System.currentTimeMillis() + "离开printB");
    }*/
    public static void printA(){
        synchronized (Service.class) {
            try {
                System.out.println( "线程名称为：" + Thread.currentThread().getName()
                + "在" + System.currentTimeMillis() + "进入printA");
                Thread.sleep( 3000 );
                System.out.println( "线程名称为：" + Thread.currentThread().getName()
                + "在" + System.currentTimeMillis() + "离开printA");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printB(){
        synchronized (Service.class){
            System.out.println( "线程名称为：" + Thread.currentThread().getName()
            + "在" + System.currentTimeMillis() + "进入printB");
            System.out.println( "线程名称为：" +  Thread.currentThread().getName()
            + "在" + System.currentTimeMillis() + "离开printB");
        }
    }
}

class ThreadE extends Thread{
    @Override
    public void run() {
        Service3.printA();
    }
}

class ThreadF extends Thread{
    @Override
    public void run() {
        Service3.printB();
    }
}