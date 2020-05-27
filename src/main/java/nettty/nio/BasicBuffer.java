package nettty.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate( 5 );

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put( i*2 );
        }

        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(3);
        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
        intBuffer.clear();

    }
}
