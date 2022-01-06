/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import models.Model;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shaders.Shader;
import shaders.ShaderProgram;

/**
 *
 * @author Oytun
 */
public class SkyboxProgram extends ShaderProgram {

    private CubeMap cubeMap;
    private int view, proj, skybox;
    //private Vector3f direction;
    private final Shader vertexShader;
    private final Shader fragmentShader;

    public SkyboxProgram(CubeMap cubeMap) {
        this.cubeMap = cubeMap;
        shaderProgramLocation = GL20.glCreateProgram();
        vertexShader = new Shader(GL20.GL_VERTEX_SHADER,
                "skybox_shader.vert");
        fragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER,
                "skybox_shader.frag");
        GL20.glAttachShader(shaderProgramLocation, vertexShader.getShaderLocation());
        GL20.glAttachShader(shaderProgramLocation, fragmentShader.getShaderLocation());
        GL30.glBindFragDataLocation(shaderProgramLocation, 0, "FragColor");
        GL20.glLinkProgram(shaderProgramLocation);
        initialize();
    }

    private void initialize() {
        //Vertex Shader Uniforms
        view = GL20.glGetUniformLocation(shaderProgramLocation, "view");
        proj = GL20.glGetUniformLocation(shaderProgramLocation, "proj");
        skybox = GL20.glGetUniformLocation(shaderProgramLocation, "skybox");
    }

    public void specifyVertexAttribute(ArrayList<Model> models) {
        //is this even necessary?
        for (Model model : models) {
            specifyVertexAttribute(model, "aPos", 3, GL11.GL_FLOAT, true, Float.BYTES * 3, 0);
        }
    }

    public void setViewMatrix(FloatBuffer matrix) {
        use();
        GL20.glUniformMatrix4(view, false, matrix);
    }

    public void setProjMatrix(FloatBuffer matrix) {
        use();
        GL20.glUniformMatrix4(proj, false, matrix);
    }
    
    public void setSkybox(){
        use();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, cubeMap.getCubemapId());
    }

    public CubeMap getCubeMap() {
        return cubeMap;
    }

}
