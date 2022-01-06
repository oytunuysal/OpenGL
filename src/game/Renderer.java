package game;

import shaders.DepthTestingShaderProgram;
import models.WorldModel;
import models.Model;
import shaders.SceneShaderProgram;
import shaders.ShaderProgram;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import shaders.PostProcessingProgram;
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

    public void stencilRender(GameObject gameObject) {
        enableOutline();
        originalScale.set(gameObject.getModel().getScale());
        gameObject.getModel().scale(1.1f);
        render(gameObject);
        gameObject.getModel().scaleVector(originalScale);
        disableOutline();
    }

    public void render(ArrayList<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            render(gameObject);
        }
    }

    public void render(GameObject gameObject) {
        shaderProgram.use();
        gameObject.getModel().setMatrixTo(Main.worldMatrix);

        //really bad
        if (shaderProgram instanceof DepthTestingShaderProgram) {
            ((DepthTestingShaderProgram) shaderProgram).setModelMatrix(WorldModel.getModelMatrixf(Main.worldMatrix));
        } else if (shaderProgram instanceof SceneShaderProgram) {
            ((SceneShaderProgram) shaderProgram).setModelMatrix(WorldModel.getModelMatrixf(Main.worldMatrix));
            ((SceneShaderProgram) shaderProgram).setMaterial(gameObject.getMaterial());

        } else if (shaderProgram instanceof StencilShaderProgram) {
            ((StencilShaderProgram) shaderProgram).setModelMatrix(WorldModel.getModelMatrixf(Main.worldMatrix));

        }

        //worldModel.getModelMatrixf()
        ArrayList<Model> models = gameObject.getModel().getSubModels();
        TextureLoader.bindTexture(gameObject.getTextureName(), GL13.GL_TEXTURE0);
        TextureLoader.bindTexture(gameObject.getMaterial().getDiffuseTextureName(), GL13.GL_TEXTURE1);
        TextureLoader.bindTexture(gameObject.getMaterial().getSpecularTextureName(), GL13.GL_TEXTURE2);
        GL11.glGetError();

        for (Model model : models) {
            GL30.glBindVertexArray(model.getVao());
            GL20.glEnableVertexAttribArray(0);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }

    }

    public static void postProcess(PostProcessingProgram postProcessingProgram,
            ArrayList<Model> quad2d, FrameBuffer frameBuffer) {
        frameBuffer.unbindCurrentFrameBuffer();
        Renderer.clear();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        postProcessingProgram.use();
        GL30.glBindVertexArray(quad2d.get(0).getVao());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, frameBuffer.getFrameBufferTexture());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

    }

    public static void skybox(SkyboxProgram skyboxProgram, ArrayList<Model> skyboxCube) {
        skyboxProgram.use();
        GL11.glDepthMask(false);
//        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL30.glBindVertexArray(skyboxCube.get(0).getVao());
        skyboxProgram.setSkybox();
//        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skyboxProgram.getCubeMap().getCubemapId());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 36);
//        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glDepthMask(true);
    }
}
