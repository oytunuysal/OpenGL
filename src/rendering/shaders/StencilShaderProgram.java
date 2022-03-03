package rendering.shaders;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import rendering.models.Model;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class StencilShaderProgram extends ShaderProgram {

    private SceneVertexShader sceneVertexShader;
    private Shader fragmentShader;

    public StencilShaderProgram() {
        shaderProgramLocation = GL20.glCreateProgram();
        this.sceneVertexShader = sceneVertexShader = new SceneVertexShader(GL20.GL_VERTEX_SHADER,
                "scene_vertex_shader.vert", shaderProgramLocation);

        this.fragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER,
                "stencil_testing.frag");

        GL20.glAttachShader(shaderProgramLocation, sceneVertexShader.getShaderLocation());
        GL20.glAttachShader(shaderProgramLocation, fragmentShader.getShaderLocation());
        GL30.glBindFragDataLocation(shaderProgramLocation, 0, "outColor");
        GL20.glLinkProgram(shaderProgramLocation);

        sceneVertexShader.initialize();
    }

    public void specifySceneVertexAttribute(ArrayList<Model> models) {
        //is this even necessary?
        for (Model model : models) {
            specifyVertexAttribute(model, "position", 3, GL11.GL_FLOAT, true, Float.BYTES * 9, 0);
            specifyVertexAttribute(model, "texcoord", 3, GL11.GL_FLOAT, false, Float.BYTES * 9, Float.BYTES * 3);
            specifyVertexAttribute(model, "normalVector", 3, GL11.GL_FLOAT, false, Float.BYTES * 9, Float.BYTES * 6);
        }
    }

    public SceneVertexShader getSceneVertexShader() {
        return sceneVertexShader;
    }

    public Shader getFragmentShader() {
        return fragmentShader;
    }

    public void setModelMatrix(FloatBuffer matrix) {
        super.use();
        sceneVertexShader.setModelMatrix(matrix);
    }

    public void setViewMatrix(FloatBuffer matrix) {
        super.use();
        sceneVertexShader.setViewMatrix(matrix);

    }

    public void setProjMatrix(FloatBuffer matrix) {
        super.use();
        sceneVertexShader.setProjMatrix(matrix);
    }

}
