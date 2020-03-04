package containers;

import net.mindview.util.Countries;

import java.util.*;

public class Lists {
    private static boolean b;
    private static String s;
    private static int i;
    private static Iterator<String> it;
    private static ListIterator<String> lit;

    public static void basicTest(List<String> a){
        a.add( 1, "x" ); //Add at location 1
        a.add( "x" ); //Add at end
        //Add a collection:
        a.addAll( Countries.names(25) );
        //Add a collection starting at location 3:
        a.addAll( 3, Countries.names( 25 ) );
        b = a.contains ( "1" ); //Is is in there?
        //Is the entire collection in there?
        b = a.containsAll( Countries.names(25) );
        //List allow random access, which is cheap for ArrayList, expensive for LinkedList
        s = a.get(1); //Get (typed) object at location 1
        i = a.indexOf("1"); //Tell index of object
        b = a.isEmpty(); //An elements inside?
        it = a.iterator();
        lit = a.listIterator();
        lit = a.listIterator( 3 );
        i = a.lastIndexOf( "1" );
        a.remove(1); //Remove location 1
        a.remove("3"); //Remove this object
        a.set(1, "y"); //Set location 1 to "y"
        //Keep everything that's in the argument (the intersection of the two sets):
        a.retainAll(Countries.names(25));
        //Remove everything that's in the argument :
        a.removeAll(Countries.names(25));
        i = a.size(); //How big it is?
        a.clear(); //Remove all elements
        System.out.println( "test" );

    }

    public static void iterMotion(List<String> a){
        ListIterator<String> it = a.listIterator();
        b = it.hasNext();
        b = it.hasPrevious();
        s = it.next();
        i = it.nextIndex();
        s = it.previous();
        i = it.previousIndex();
        System.out.println( "test" );
    }

    public static void iterManipulation(List<String> a){
        ListIterator<String> it = a.listIterator();
        it.add( "47" );
        //Must move to an element after add():
        it.next();
        //Remove the element after the newly produced one:
        it.remove();
        //Muse move to an element after remove():
        it.next();
        //Change the element after the deleted one:
        it.set("47");
        System.out.println( "test" );
    }

    public static void testVisual(List<String> a){
        System.out.println( a );
        List<String> b = Countries.names( 25 );
        System.out.println( "b = " + b );
        a.addAll( b );
        a.addAll( b );
        System.out.println( a );
        //Insert, remove, and replace element
        //using a ListIterator
        ListIterator<String> x = a.listIterator(a.size()/2);
        x.add( "one" );
        System.out.println( a );
        System.out.println( x.next() );
        x.remove();
        System.out.println( x.next() );
        x.set( "47" );
        System.out.println( a );
        //Travers the list backwords:
        x = a.listIterator(a.size());
        while(x.hasPrevious())
            System.out.print(x.previous() + " "  );
        System.out.println(  );
        System.out.println( "testVisual finished" );
    }

    public static void testLinkedList(){
        LinkedList<String> ll = new LinkedList<String>(  );
        ll.addAll( Countries.names( 25 ) );
        System.out.println( ll );
        //Treat it like a stack, pushing:
        ll.addFirst( "one" );
        ll.addFirst( "two" );
        System.out.println( ll );
        //Like "peeking" at the top of a stack:
        System.out.println( ll.getFirst() );
        //Like popping a stack:
        System.out.println( ll.removeFirst() );
        System.out.println( ll.removeFirst() );
        //Treat it like a queue, pulling elements
        //off the tail end:
        System.out.println( ll.removeLast() );
        System.out.println( ll );
    }

    public static void main(String[] args){
        //Make and fill a new list each time:
       /* basicTest( new LinkedList<String>( Countries.names( 25 ) ) );

        basicTest( new ArrayList<String>( Countries.names(25) ) );*/
      /* iterMotion( new LinkedList<String>( Countries.names( 25 ) ) );
       iterMotion( new ArrayList<String>( Countries.names(25) ) );*/

      /* iterManipulation( new LinkedList<String>( Countries.names( 25 ) ) );
       iterManipulation(new ArrayList<String>( Countries.names(25) )  );*/
      testVisual( new LinkedList<String>( Countries.names( 25 ) )  );
        testVisual(new ArrayList<String>( Countries.names(25) )  );
    }
}
