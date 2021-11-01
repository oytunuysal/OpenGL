package models;

import game.GLUtils;
import java.nio.FloatBuffer;
import java.util.ArrayList;
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
    ArrayList<Model> models;
    private Vector3f scale;
    private Vector3f rotation;
    private Vector3f position;

    public WorldModel(ArrayList<Model> model) {
        this.models = model;
        scale = new Vector3f(1, 1, 1);
        rotation = new Vector3f();
        position = new Vector3f();
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getPosition() {
        return position;
    }
    
    public void scale(float scaleFloat){
        scale.scale(scaleFloat);
    }

    public void scaleVector(Vector3f scaleVector) {
        scale.set(scaleVector.getX(), scaleVector.getY(), scaleVector.getZ());
    }

    public void rotate(Vector3f rotationVector) {
        Vector3f.add(this.rotation, rotationVector, this.rotation);
    }

    public void translate(Vector3f offset) {
        Vector3f.add(this.position, offset, this.position);
    }

    public static FloatBuffer getModelMatrixf(Matrix4f modelMatrix) {
        return GLUtils.toBuffer(modelMatrix);
    }

    public ArrayList<Model> getSubModels() {
        return models;
    }

    public void setMatrixTo(Matrix4f worldMatrix) {
        worldMatrix.setIdentity();
        worldMatrix.translate(this.position);
        worldMatrix.rotate((float) Math.toRadians(rotation.x), GeometryUtils.ROTATION_X);
        worldMatrix.rotate((float) Math.toRadians(rotation.y), GeometryUtils.ROTATION_Y);
        worldMatrix.rotate((float) Math.toRadians(rotation.z), GeometryUtils.ROTATION_Z);
        worldMatrix.scale(this.scale);
    }
}
