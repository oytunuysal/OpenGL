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
        ArrayList<Model> modelCube = ModelLoader.loadModel("backpack.obj");
        System.out.println("Model loading finished.");

//        Model model1 = new Model(Constants.cubeVertices, 36);
        WorldModel worldModel1 = new WorldModel(modelCube);
        WorldModel worldModel2 = new WorldModel(modelCube);
        worldModel2.translate(new Vector3f(2.0f, 2.0f, 1.0f));
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

        Material material1 = new Material(1, 1, 32f);

        //SceneShaderProgram
        SceneShaderProgram sceneShaderProgram = new SceneShaderProgram();
        sceneShaderProgram.specifySceneVertexAttribute(modelbulb);
        sceneShaderProgram.specifySceneVertexAttribute(modelCube);

        //DepthTestingProgram
        DepthTestingShaderProgram depthTestingShaderProgram = new DepthTestingShaderProgram();
        depthTestingShaderProgram.specifySceneVertexAttribute(modelbulb);
        depthTestingShaderProgram.specifySceneVertexAttribute(modelCube);

        //StencilTestingProgram
        StencilShaderProgram stencilShaderProgram = new StencilShaderProgram();
        stencilShaderProgram.specifySceneVertexAttribute(modelbulb);
        stencilShaderProgram.specifySceneVertexAttribute(modelCube);

        TextureLoader.loadBindTextures(sceneShaderProgram, "res/diffuse.jpg", GL13.GL_TEXTURE0);
        TextureLoader.loadBindTextures(sceneShaderProgram, "res/diffuse.jpg", GL13.GL_TEXTURE1);
        TextureLoader.loadBindTextures(sceneShaderProgram, "res/specular.jpg", GL13.GL_TEXTURE2);

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
        sceneShaderProgram.setMaterial(material1);

        Renderer sceneRenderer = new Renderer(sceneShaderProgram);
        Renderer depthRenderer = new Renderer(depthTestingShaderProgram);
        Renderer sencilRenderer = new Renderer(stencilShaderProgram);
        ArrayList<Renderer> rendererList = new ArrayList<>();
        rendererList.add(sceneRenderer);
        rendererList.add(depthRenderer);
//        rendererList.add(sencilRenderer);
//        GL11.glDepthFunc(GL_ALWAYS); 
        GLUtils.errorCheck();

        //Game loop starts
        boolean active = true;
        boolean lock = false;
        int activeRenderer = 0;
        while (active) {
            //Show things on the display
            Display.update();
            //Sync to 60 fps (If possible)
            Display.sync(FPS);

            //Clear all channels: color, depth, and stencils
            Renderer.clear();

            Renderer.enableStencil();

            //rendering stuff
            rendererList.get(activeRenderer).render(lightSource1);
            rendererList.get(activeRenderer).render(lightSource2);
            rendererList.get(activeRenderer).render(worldModel1);
            rendererList.get(activeRenderer).render(worldModel2);

            Renderer.enableOutline();
            sencilRenderer.render(lightSource1);
            Renderer.disableOutline();

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
            }

            //Check if pressed the close button
            boolean closeRequested = Display.isCloseRequested();
            //Pressed? Break out of the loop
            if (closeRequested) {
                active = false;
            }
        }
    }

}
