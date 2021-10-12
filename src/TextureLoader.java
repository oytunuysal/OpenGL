
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

    public static void loadBindTextures(ShaderProgram shaderProgram, String resourceLocation, int textureSlot) {
        Texture texture = null;
        try {
            shaderProgram.use();
            GL13.glActiveTexture(textureSlot);
            texture = org.newdawn.slick.opengl.TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(resourceLocation));
            texture.bind();

            GL20.glUniform1i(shaderProgram.getUniformLocation("tex"), 0);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        } catch (Exception e) {
            System.err.print(e);
        }

    }
}
