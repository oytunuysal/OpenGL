package models;


import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

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
    private String name;
    //ebo;

    public Model(float[] verteces, int vertexCount) {
        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        FloatBuffer floatBuffer = BufferUtilities.toBuffer(verteces);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
        this.vertexCount = vertexCount;
    }

    public Model(String name, ArrayList<Vector3f> verteces, int vertexCount) {
        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        float[] floatArray = new float[verteces.size() * 3];
        int index = 0;
        for (Vector3f vector : verteces) {
            floatArray[index++] = vector.x;
            floatArray[index++] = vector.y;
            floatArray[index++] = vector.z;
        }
        FloatBuffer floatBuffer = BufferUtilities.toBuffer(floatArray);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
        this.vertexCount = vertexCount;
        this.name = name;
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
