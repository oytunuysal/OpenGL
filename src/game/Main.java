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
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
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
        ArrayList<Model> quad = ModelLoader.loadQuad();
        System.out.println("Model loading finished.");

//        Model model1 = new Model(Constants.cubeVertices, 36);
        WorldModel worldModel1 = new WorldModel(backpack);
        WorldModel worldModel2 = new WorldModel(backpack);
        WorldModel worldModel3 = new WorldModel(quad);
        worldModel2.translate(new Vector3f(2.0f, 2.0f, 1.0f));
        worldModel3.translate(new Vector3f(2f, -2f, -1f));
        Light light1 = new Light(new Vector3f(0.2f, 0.2f, 0.2f),
                new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f),
                1f, 0.09f, 0.032f);

        LightSource lightSource1 = new LightSource(modelbulb, light1);
        lightSource1.scaleVector(new Vector3f(0.3f, 0.3f, 0.3f));
        lightSource1.rotate(new Vector3f(90, 0, 0));
        lightSource1.translate(new Vector3f(2f, 0f, 1f));

        LightSource lightSource2 = new LightSource(modelbulb, light1);
        lightSource2.scaleVector(new Vector3f(0.1f, 0.1f, 0.1f));
        lightSource2.rotate(new Vector3f(90, 0, 0));
        lightSource2.translate(new Vector3f(0f, 0f, 1f));

        //SceneShaderProgram
        SceneShaderProgram sceneShaderProgram = new SceneShaderProgram();
        sceneShaderProgram.specifySceneVertexAttribute(modelbulb);
        sceneShaderProgram.specifySceneVertexAttribute(backpack);
        sceneShaderProgram.specifySceneVertexAttribute(quad);

        //DepthTestingProgram
        DepthTestingShaderProgram depthTestingShaderProgram = new DepthTestingShaderProgram();
        depthTestingShaderProgram.specifySceneVertexAttribute(modelbulb);
        depthTestingShaderProgram.specifySceneVertexAttribute(backpack);
        depthTestingShaderProgram.specifySceneVertexAttribute(quad);

        //StencilTestingProgram
        StencilShaderProgram stencilShaderProgram = new StencilShaderProgram();
        stencilShaderProgram.specifySceneVertexAttribute(modelbulb);
        stencilShaderProgram.specifySceneVertexAttribute(backpack);
        stencilShaderProgram.specifySceneVertexAttribute(quad);
        //Loading Textures
        TextureLoader.loadTexture("res/diffuse.jpg");
        TextureLoader.loadTexture("res/specular.jpg");
        TextureLoader.loadTexture("res/grass.png");
        TextureLoader.loadTexture("res/white.png");
        TextureLoader.loadTexture("res/4.png");

        Material mBackpack = new Material("diffuse", "specular", 32f);
        Material mLightbulb = new Material("white", "white", 0f);
        Material mGrass = new Material("grass", "grass", 0f);

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

        //setting up lighting
        sceneShaderProgram.setPointLights(lightSource1.getLight());
        sceneShaderProgram.setPointLights(lightSource2.getLight());

        //Setting material
//        sceneShaderProgram.setMaterial(material1);
        Renderer sceneRenderer = new Renderer(sceneShaderProgram);
        Renderer depthRenderer = new Renderer(depthTestingShaderProgram);
        Renderer sencilRenderer = new Renderer(stencilShaderProgram);
        ArrayList<Renderer> rendererList = new ArrayList<>();
        rendererList.add(sceneRenderer);
        rendererList.add(depthRenderer);

        //creating GameOBjects
        //diffuse is a texture name here.
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        GameObject backpack1 = new GameObject(worldModel1, "diffuse", mBackpack);
        GameObject backpack2 = new GameObject(worldModel2, "diffuse", mBackpack);
        GameObject lightbulb1 = new GameObject(lightSource1, "white", mLightbulb);
        GameObject lightbulb2 = new GameObject(lightSource2, "white", mLightbulb);
        GameObject grass = new GameObject(worldModel3, "grass", mGrass);

        //I need to seperate opaque objects and sort them near to far
        ObjectFactory.setCameraPosition(camera.getPosition());
        gameObjects.add(backpack1);
        gameObjects.add(backpack2);
        gameObjects.add(lightbulb1);
        gameObjects.add(lightbulb2);
        gameObjects.add(grass);

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

            Renderer.enableStencil();

            //sorted by distance
            ObjectFactory.refresh(gameObjects);

            //rendering stuff
            for (GameObject gameObject : gameObjects) {
                rendererList.get(activeRenderer).render(gameObject);
            }

            for (int i = 0; i < stencilCount; i++) {
                sencilRenderer.stencilRender(gameObjects.get(i));
            }

            if (loopCount == 0) {
                stencilCount++;
                stencilCount = stencilCount % (gameObjects.size() + 1);
            }

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
                ObjectFactory.setCameraPosition(camera.getPosition());
                System.out.println(-camera.getPosition().x + ", " + -camera.getPosition().y + ", " + -camera.getPosition().z);
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
