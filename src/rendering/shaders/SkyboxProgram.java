package rendering.shaders;

import rendering.CubeMap;
import rendering.CubeMap;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import rendering.models.Model;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.shaders.Shader;
import rendering.shaders.ShaderProgram;

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
        view = GL20.glGetUniformLocation(shaderProgramLocation, "viewWithoutTransformations");
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

    public void setSkybox() {
        use();
        cubeMap.setSkybox();
    }

    public CubeMap getCubeMap() {
        return cubeMap;
    }

}
