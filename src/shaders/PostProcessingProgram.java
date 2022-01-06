/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaders;

import java.util.ArrayList;
import models.Model;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Oytun
 */
public class PostProcessingProgram extends ShaderProgram {

    private final Shader vertexShader;
    private final Shader fragmentShader;

    public PostProcessingProgram() {
        shaderProgramLocation = GL20.glCreateProgram();
        vertexShader = new Shader(GL20.GL_VERTEX_SHADER,
                "postprocessing_vertex_shader.vert");
        fragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER,
                "invert_color_shader.frag");
        GL20.glAttachShader(shaderProgramLocation, vertexShader.getShaderLocation());
        GL20.glAttachShader(shaderProgramLocation, fragmentShader.getShaderLocation());
        GL30.glBindFragDataLocation(shaderProgramLocation, 0, "outColor");
        GL20.glLinkProgram(shaderProgramLocation);
    }

    public void specifyVertexAttribute(ArrayList<Model> models) {
        //is this even necessary?
        for (Model model : models) {
            specifyVertexAttribute(model, "aPos", 2, GL11.GL_FLOAT, true, Float.BYTES * 4, 0);
            specifyVertexAttribute(model, "aTexCoords", 2, GL11.GL_FLOAT, false, Float.BYTES * 4, Float.BYTES * 2);
        }
    }

    public int getShaderProgramLocation() {
        return shaderProgramLocation;
    }

    public Shader getSceneVertexShader() {
        return vertexShader;
    }

    public Shader getSceneFragmentShader() {
        return fragmentShader;
    }

}
