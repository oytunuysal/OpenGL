package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import shaders.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oytun
 */
public class TextureLoader {

    private static Map<String, Texture> textures = new HashMap<String, Texture>();

    //Relative path
    public static void loadTexture(String resourcePath) {
        Texture texture = null;
        int firstIndex = resourcePath.lastIndexOf("/");
        int extension = resourcePath.lastIndexOf(".");
        String textureName = resourcePath.substring(firstIndex + 1, extension);
        System.out.println(textureName);
        String textureFormat = resourcePath.substring(extension + 1);
        System.out.println(textureFormat);
        try {
            texture = org.newdawn.slick.opengl.TextureLoader.getTexture(textureFormat, ResourceLoader.getResourceAsStream(resourcePath));
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            textures.put(textureName, texture);
        } catch (Exception e) {
            System.err.print(e);
        }

    }

    public static void bindTexture(String textureName, int textureSlot) {
        GL13.glActiveTexture(textureSlot);
        Texture texture = textures.get(textureName);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
//        GLUtils.errorCheck();
    }
}
