package rendering;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import rendering.shaders.ShaderProgram;

public class CubeMap {

    private int cubemapId;
    private ArrayList<String> facePaths;

    public CubeMap(ArrayList<String> facePaths) {
        createCubemap();
        this.facePaths = facePaths;
//        facePaths = new ArrayList<>();
//        facePaths.add("res/skybox/right.jpg");
//        facePaths.add("res/skybox/left.jpg");
//        facePaths.add("res/skybox/top.jpg");
//        facePaths.add("res/skybox/bottom.jpg");
//        facePaths.add("res/skybox/front.jpg");
//        facePaths.add("res/skybox/back.jpg");
        loadTextures();
    }

    private void createCubemap() {
        cubemapId = GL11.glGenTextures();
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, cubemapId);
    }

    private void loadTextures() {
        TextureLoader.loadCubemapTextures(facePaths);
    }

    public int getCubemapId() {
        return cubemapId;
    }

    public void setSkybox() {
//        shaderProgram.use();
        TextureLoader.bindCubemap(cubemapId, GL13.GL_TEXTURE10);
    }

}
