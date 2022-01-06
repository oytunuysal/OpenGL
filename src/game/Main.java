package game;

import shaders.DepthTestingShaderProgram;
import models.WorldModel;
import models.LightSource;
import models.Light;
import models.Model;
import models.ModelLoader;
import models.Material;
import shaders.SceneShaderProgram;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shaders.PostProcessingProgram;
import shaders.StencilShaderProgram;

public class Main {

    private static final int FPS = 60;
    public static Matrix4f worldMatrix = new Matrix4f();

    public static void main(String[] args) {
        //Generic setup logic
        OpenGLStarter.setupOpenGl();
        Keyboard.enableRepeatEvents(true);
        //setting up everything
        ArrayList<Model> modelbulb = ModelLoader.loadModel("objBulb.obj");
        ArrayList<Model> backpack = ModelLoader.loadModel("backpack.obj");
        ArrayList<Model> quad3d = ModelLoader.load3dQuad();
        ArrayList<Model> quad2d = ModelLoader.load2dQuad();
        ArrayList<Model> skyboxCube = ModelLoader.loadSkybox();

        System.out.println("Model loading finished.");

//        Model model1 = new Model(Constants.cubeVertices, 36);
        WorldModel wmBackpack1 = new WorldModel(backpack);
        WorldModel wmBackpack2 = new WorldModel(backpack);
        WorldModel wmQuad1 = new WorldModel(quad3d);
        WorldModel wmQuad2 = new WorldModel(quad3d);
        WorldModel wmQuad3 = new WorldModel(quad3d);
        WorldModel wmQuad4 = new WorldModel(quad3d);
        wmBackpack2.translate(new Vector3f(2.0f, 2.0f, 1.0f));
        wmQuad1.translate(new Vector3f(2.0f, 2.0f, 3.0f));
        wmQuad2.translate(new Vector3f(2f, 2f, 3.5f));
        wmQuad3.translate(new Vector3f(2f, 2f, 4.0f));
        wmQuad4.translate(new Vector3f(2f, 2f, -0.5f));
        Light light1 = new Light(new Vector3f(0.2f, 0.2f, 0.2f),
                new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f),
                1f, 0.09f, 0.032f);

        LightSource lsLight1 = new LightSource(modelbulb, light1);
        lsLight1.scaleVector(new Vector3f(0.3f, 0.3f, 0.3f));
        lsLight1.rotate(new Vector3f(90, 0, 0));
        lsLight1.translate(new Vector3f(2f, 0f, 1f));

        LightSource lsLight2 = new LightSource(modelbulb, light1);
        lsLight2.scaleVector(new Vector3f(0.1f, 0.1f, 0.1f));
        lsLight2.rotate(new Vector3f(90, 0, 0));
        lsLight2.translate(new Vector3f(0f, 0f, 1f));

        //SceneShaderProgram
        SceneShaderProgram sceneShaderProgram = new SceneShaderProgram();
        sceneShaderProgram.specifySceneVertexAttribute(modelbulb);
        sceneShaderProgram.specifySceneVertexAttribute(backpack);
        sceneShaderProgram.specifySceneVertexAttribute(quad3d);

        //DepthTestingProgram
        DepthTestingShaderProgram depthTestingShaderProgram = new DepthTestingShaderProgram();
        depthTestingShaderProgram.specifySceneVertexAttribute(modelbulb);
        depthTestingShaderProgram.specifySceneVertexAttribute(backpack);
        depthTestingShaderProgram.specifySceneVertexAttribute(quad3d);

        //StencilTestingProgram
        StencilShaderProgram stencilShaderProgram = new StencilShaderProgram();
        stencilShaderProgram.specifySceneVertexAttribute(modelbulb);
        stencilShaderProgram.specifySceneVertexAttribute(backpack);
        stencilShaderProgram.specifySceneVertexAttribute(quad3d);

        ArrayList<String> facePaths = new ArrayList<>();
        facePaths.add("res/skybox/right.png");
        facePaths.add("res/skybox/left.png");
        facePaths.add("res/skybox/top.png");
        facePaths.add("res/skybox/bottom.png");
        facePaths.add("res/skybox/front.png");
        facePaths.add("res/skybox/back.png");

        //Cubemap
        CubeMap cubeMap = new CubeMap(facePaths);

        //Skybox
        SkyboxProgram skyboxProgram = new SkyboxProgram(cubeMap);
        skyboxProgram.specifyVertexAttribute(skyboxCube);

        //PostProcessingProgram
        PostProcessingProgram postProcessingProgram = new PostProcessingProgram();
        postProcessingProgram.specifyVertexAttribute(quad2d);

        //use shader?
        FrameBuffer frameBuffer = new FrameBuffer(800, 600);
        frameBuffer.createDepthStencilTextureAttachment(800, 600);
        frameBuffer.unbindCurrentFrameBuffer();

        //Loading Textures
        TextureLoader.loadTexture("res/diffuse.jpg");
        TextureLoader.loadTexture("res/specular.jpg");
        TextureLoader.loadTexture("res/grass.png");
        TextureLoader.loadTexture("res/white.png");
        TextureLoader.loadTexture("res/4.png");
        TextureLoader.loadTexture("res/window.png");
        TextureLoader.loadTexture("res/window2.png");

        Material mBackpack = new Material("diffuse", "specular", 32f);
        Material mLightbulb = new Material("white", "white", 0f);
        Material mGrass = new Material("grass", "grass", 0f);
        Material mWindow = new Material("window", "window", 0f);
        Material mWindow2 = new Material("window2", "window2", 0f);

        Camera camera = new Camera();
        camera.offsetTranslate(new Vector3f(0, 0, -1));
        camera.moveCamera();

        //setting uniforms
        sceneShaderProgram.setViewMatrix(camera.getViewMatrix(), camera.getPosition());
        sceneShaderProgram.setProjMatrix(camera.getProjectionMatrix());

        depthTestingShaderProgram.setViewMatrix(camera.getViewMatrix());
        depthTestingShaderProgram.setProjMatrix(camera.getProjectionMatrix());

        stencilShaderProgram.setViewMatrix(camera.getViewMatrix());
        stencilShaderProgram.setProjMatrix(camera.getProjectionMatrix());

        skyboxProgram.setViewMatrix(camera.getViewMatrixWithoutTrans());
        skyboxProgram.setProjMatrix(camera.getProjectionMatrix());

        //setting up lighting
        sceneShaderProgram.setPointLights(lsLight1.getLight());
        sceneShaderProgram.setPointLights(lsLight2.getLight());

        //Setting material
//        sceneShaderProgram.setMaterial(material1);
        Renderer sceneRenderer = new Renderer(sceneShaderProgram);
        Renderer depthRenderer = new Renderer(depthTestingShaderProgram);
        Renderer sencilRenderer = new Renderer(stencilShaderProgram);
//        Renderer postRenderer = new Renderer(postProcessingProgram);
        ArrayList<Renderer> rendererList = new ArrayList<>();
        rendererList.add(sceneRenderer);
        rendererList.add(depthRenderer);

        //creating GameOBjects
        //diffuse is a texture name here.
        ArrayList<GameObject> opaqueObjects = new ArrayList<>();
        ArrayList<GameObject> transparentObjects = new ArrayList<>();
        GameObject backpack1 = new GameObject(wmBackpack1, "diffuse", mBackpack);
        GameObject backpack2 = new GameObject(wmBackpack2, "diffuse", mBackpack);
        GameObject lightbulb1 = new GameObject(lsLight1, "white", mLightbulb);
        GameObject lightbulb2 = new GameObject(lsLight2, "white", mLightbulb);
        GameObject grass = new GameObject(wmQuad1, "grass", mGrass);
        GameObject window = new GameObject(wmQuad2, "window", mWindow);
        GameObject window1 = new GameObject(wmQuad3, "window2", mWindow2);
        GameObject window2 = new GameObject(wmQuad4, "window", mWindow);

        //I need to seperate opaque objects and sort them near to far
        ObjectFactory.setCameraPosition(camera.getPosition());
        opaqueObjects.add(backpack1);
        opaqueObjects.add(backpack2);
        opaqueObjects.add(lightbulb1);
        opaqueObjects.add(lightbulb2);
        transparentObjects.add(grass);
        transparentObjects.add(window);
        transparentObjects.add(window1);
        transparentObjects.add(window2);

        //Enable Blending
        frameBuffer.bindFrameBuffer();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        //Game loop starts
        boolean active = true;
        boolean lock = false;
        int activeRenderer = 0;
        int loopCount = 0;
        int stencilCount = 0;
        while (active) {
            //Show things on the display
            Display.update();
            //Sync to 60 fps (If possible)
            Display.sync(FPS);

            //Clear all channels: color, depth, and stencils
            Renderer.clear();

            //Clear framebuffer
            frameBuffer.bindFrameBuffer();
            Renderer.clear();

            Renderer.skybox(skyboxProgram, skyboxCube);

            Renderer.enableStencil();

            //sorted objects by distance
            ObjectFactory.order(opaqueObjects);
            Collections.reverse(opaqueObjects);
            ObjectFactory.order(transparentObjects);
//            Collections.reverse(transparentObjects);

            GL11.glEnable(GL11.GL_CULL_FACE);

            //rendering stuff
            rendererList.get(activeRenderer).render(opaqueObjects);
            for (int i = 0; i < stencilCount; i++) {
                sencilRenderer.stencilRender(opaqueObjects.get(i));
            }

            GL11.glDisable(GL11.GL_CULL_FACE);
            //disable writing to depth buffer
            GL11.glDepthMask(false);
            rendererList.get(activeRenderer).render(transparentObjects);
            GL11.glDepthMask(true);
            if (loopCount == 0) {
                stencilCount++;
                stencilCount = stencilCount % (opaqueObjects.size() + 1);
            }

            //rendering with post processing
            Renderer.postProcess(postProcessingProgram, quad2d, frameBuffer);

            // Keyboard
            Input.getInputs();
            if (Input.isKeyDown(Keyboard.KEY_TAB) && !lock) {
                activeRenderer = ++activeRenderer % rendererList.size();
                lock = true;
            } else if (Input.isKeyUp(Keyboard.KEY_TAB) && lock) {
                lock = false;
            }
            if (camera.update()) {
                sceneShaderProgram.setViewMatrix(camera.getViewMatrix(), camera.getPosition());
                depthTestingShaderProgram.setViewMatrix(camera.getViewMatrix());
                stencilShaderProgram.setViewMatrix(camera.getViewMatrix());
                skyboxProgram.setViewMatrix(camera.getViewMatrixWithoutTrans());
                ObjectFactory.setCameraPosition(camera.getPosition());
//                System.out.println(camera.getPosition().x + ", " + camera.getPosition().y + ", " + camera.getPosition().z);
            }

            loopCount++;
            loopCount = loopCount % 30;
            GLUtils.errorCheck();

            //Check if pressed the close button
            boolean closeRequested = Display.isCloseRequested();
            //Pressed? Break out of the loop
            if (closeRequested) {
                active = false;
            }
        }
    }

}
