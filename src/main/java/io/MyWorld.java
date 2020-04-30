package io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class House implements Serializable{}

class Animal implements  Serializable{
    public String name;
    public House preferredHouse;
    Animal(String nm, House h){
        name  = nm;
        preferredHouse = h;
    }

    public String toString(){
        return name + "["+super.toString()+"], " + preferredHouse + "\n";
    }
}
public class MyWorld {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        House house = new House();
        List<Animal> animals = new ArrayList<Animal>(  );
        animals.add( new Animal( "Bosco the dog", house ) );
        animals.add( new Animal( "Ralph the hamster", house ) );
        animals.add( new Animal( "Molly the cat", house ) );

        System.out.println( "animals:\n" + animals );

        ByteArrayOutputStream buf1 = new ByteArrayOutputStream(  );
        ObjectOutputStream o1 = new ObjectOutputStream( buf1 );
        o1.writeObject( animals );
        animals.get( 0 ).name = "One";

        o1.writeObject( animals ); //Write a 2nd set
        //Write to a different stream:
        ByteArrayOutputStream buf2 = new ByteArrayOutputStream(  );
        ObjectOutputStream o2 = new ObjectOutputStream( buf2 );
        o2.writeObject( animals );
        //Now get them back:
        ObjectInputStream in1 = new ObjectInputStream( new ByteArrayInputStream( buf1.toByteArray() ) );
        ObjectInputStream in2 = new ObjectInputStream( new ByteArrayInputStream( buf2.toByteArray() ) );
        List animals1 = (List)in1.readObject();
        List animals2 = (List)in1.readObject();
        List animals3 = (List)in2.readObject();

        System.out.println( animals1 );
        System.out.println( animals2 );
        System.out.println( animals3 );
    }
}

