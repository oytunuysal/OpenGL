package models;


import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oytun
 */
public class BufferUtilities {

    public static FloatBuffer toBuffer(float[] array) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    public static IntBuffer toBuffer(int[] array) {
        IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    public static ByteBuffer toBuffer(byte[] array) {
        ByteBuffer buffer = org.lwjgl.BufferUtils.createByteBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    private static void printBuffer(FloatBuffer buf) {
        buf.rewind();
        int z = 0;
        for (int i = 0; i < buf.remaining(); i++) {
            System.out.print(buf.get(i) + " ");
            if (++z == 4) {
                System.out.println("");
                z = 0;
            }
        }
        System.out.println(" ");
        buf.rewind();
    }
}
