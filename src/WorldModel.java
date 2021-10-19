
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
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
public class WorldModel {

    //model matrix?
    ArrayList<Model> model;
    Matrix4f modelMatrix;

    public WorldModel(ArrayList<Model> model) {
        this.model = model;
        modelMatrix = new Matrix4f();
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void rotate(float radians, Vector3f axis) {
        Matrix4f.rotate((float) radians, axis, this.modelMatrix, this.modelMatrix);
    }

    public void translate(Vector3f vector) {
        this.modelMatrix.translate(vector);
    }
    
    public void scale(Vector3f vector){
        this.modelMatrix.scale(vector);
    }

    public FloatBuffer getModelMatrixf() {
        return GLUtils.toBuffer(modelMatrix);
    }
    
    public ArrayList<Model> getModels(){
        return model;
    }

//    public int getVao() {
//        return model.getVao();
//    }
//
//    public int getVbo() {
//        return model.getVbo();
//    }
//
//    public int getVertexCount() {
//        return model.getVertexCount();
//    }

}
