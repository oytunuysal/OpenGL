
import org.lwjgl.opengl.GL11;
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
public class Renderer {
    //not sure about here(SceneShaderProgram)
    SceneShaderProgram shaderProgram;

    public Renderer(SceneShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public void prepare() {
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
    }

    public void render(WorldModel model) {
        shaderProgram.use();
        shaderProgram.setModelMatrix(model.getModel());
        GL30.glBindVertexArray(model.getVao());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
