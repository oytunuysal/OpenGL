
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oytun
 */
public class Model {

    //vao, vbo, ebo and attributes
    int vao, vbo;
    private final int vertexCount;
    //ebo;

    public Model(float[] verteces, int vertexCount) {
        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        FloatBuffer floatBuffer = toBuffer(verteces);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
        this.vertexCount = vertexCount;
    }

    public static FloatBuffer toBuffer(float[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    public static IntBuffer toBuffer(int[] array) {
        IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    public static ByteBuffer toBuffer(byte[] array) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    public int getVao() {
        return vao;
    }

    public int getVbo() {
        return vbo;
    }

    public int getVertexCount() {
        return vertexCount;
    }

}
