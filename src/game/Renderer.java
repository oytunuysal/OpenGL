package game;

import shaders.DepthTestingShaderProgram;
import models.WorldModel;
import models.Model;
import shaders.SceneShaderProgram;
import shaders.ShaderProgram;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import shaders.StencilShaderProgram;

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

    ShaderProgram shaderProgram;

    private static boolean isOutlineEnabled = false;
    private static Vector3f originalScale = new Vector3f();

    public Renderer(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public static void clear() {
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
    }

    public static void enableStencil() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilMask(0xFF);
    }

    public static void enableOutline() {
        GL11.glStencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF);
        GL11.glStencilMask(0x00);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        isOutlineEnabled = true;
    }

    public static void disableOutline() {
        GL11.glStencilMask(0xFF);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        isOutlineEnabled = false;
    }

    public void render(WorldModel worldModel) {
        shaderProgram.use();
        if (isOutlineEnabled == true && shaderProgram instanceof StencilShaderProgram) {
            originalScale.set(worldModel.getScale());
            worldModel.scale(1.1f);
            worldModel.setMatrixTo(Main.worldMatrix);
            worldModel.scaleVector(originalScale);
        } else {
            worldModel.setMatrixTo(Main.worldMatrix);
        }

        //really bad
        if (shaderProgram instanceof DepthTestingShaderProgram) {
            ((DepthTestingShaderProgram) shaderProgram).setModelMatrix(WorldModel.getModelMatrixf(Main.worldMatrix));
        } else if (shaderProgram instanceof SceneShaderProgram) {
            ((SceneShaderProgram) shaderProgram).setModelMatrix(WorldModel.getModelMatrixf(Main.worldMatrix));
        } else if (shaderProgram instanceof StencilShaderProgram) {
            ((StencilShaderProgram) shaderProgram).setModelMatrix(WorldModel.getModelMatrixf(Main.worldMatrix));

        }
        //worldModel.getModelMatrixf()
        ArrayList<Model> models = worldModel.getSubModels();
        for (Model model : models) {
            GL30.glBindVertexArray(model.getVao());
            GL20.glEnableVertexAttribArray(0);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }
    }
}
