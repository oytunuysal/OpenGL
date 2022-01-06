package game;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.BufferUtilities;
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
    
    public static void loadCubemapTextures(ArrayList<String> faces) {
        for (int i = 0; i < faces.size(); i++) {
            String face = faces.get(i);
            Texture texture = null;
            int firstIndex = face.lastIndexOf("/");
            int extension = face.lastIndexOf(".");
            String textureName = face.substring(firstIndex + 1, extension);
            System.out.println(textureName);
            String textureFormat = face.substring(extension + 1);
            System.out.println(textureFormat);
            try {
                texture = org.newdawn.slick.opengl.TextureLoader.getTexture(textureFormat, ResourceLoader.getResourceAsStream(face));
                System.out.println(texture.getImageHeight());
                GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGB, texture.getImageWidth(), texture.getImageHeight(),
                        0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, BufferUtilities.toBuffer(texture.getTextureData()));
                textures.put(textureName, texture);
                
            } catch (Exception e) {
                System.err.print(e);
            }
        }
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL12.GL_TEXTURE_WRAP_R, GL12.GL_CLAMP_TO_EDGE);
    }
    
    public static void bindTexture(String textureName, int textureSlot) {
        GL13.glActiveTexture(textureSlot);
        Texture texture = textures.get(textureName);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
//        GLUtils.errorCheck();
    }
}
