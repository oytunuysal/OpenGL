package shaders;


import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
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
public class SceneVertexShader extends Shader {

    private int model, view, proj, uniformInverseTransposedModel;
    private int shaderProgramLocation;

    public SceneVertexShader(int shaderType, String sourceFileName, int shaderProgramLocation) {
        super(shaderType, sourceFileName);
        this.shaderProgramLocation = shaderProgramLocation;
    }

    public void initialize() {
        //Vertex Shader Uniforms
        model = GL20.glGetUniformLocation(shaderProgramLocation, "model");
        view = GL20.glGetUniformLocation(shaderProgramLocation, "view");
        proj = GL20.glGetUniformLocation(shaderProgramLocation, "proj");
        uniformInverseTransposedModel = GL20.glGetUniformLocation(shaderProgramLocation, "inverseTransposedModel");
    }

    public void setModelMatrix(FloatBuffer matrix) {
        GL20.glUniformMatrix4(model, false, matrix);
        normalVector(matrix);
    }

    public void setViewMatrix(FloatBuffer matrix) {
        GL20.glUniformMatrix4(view, false, matrix);
    }

    public void setProjMatrix(FloatBuffer matrix) {
        GL20.glUniformMatrix4(proj, false, matrix);
    }

    private void normalVector(FloatBuffer model) {
        FloatBuffer ITbuffer = BufferUtils.createFloatBuffer(16);
        Matrix4f inverseTransposedModel = new Matrix4f();
        inverseTransposedModel.load(model);
        inverseTransposedModel.invert();
        inverseTransposedModel.transpose();
        inverseTransposedModel.store(ITbuffer);
        ITbuffer.flip();
        GL20.glUniformMatrix4(uniformInverseTransposedModel, false, ITbuffer);
    }

}
