
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;

public class Main {

    private static final int FPS = 60;

    public static void main(String[] args) {
        //Generic setup logic
        OpenGLStarter.setupOpenGl();
        Keyboard.enableRepeatEvents(true);
        //setting up everything
        ArrayList<Model> modelbulb = ModelLoader.loadModel("objBulb.obj");
        ArrayList<Model> modelCube = ModelLoader.loadModel("Jack Daniel Bottle.obj");
        System.out.println("finished");

//        Model model1 = new Model(Constants.cubeVertices, 36);
        WorldModel worldModel1 = new WorldModel(modelCube);
        WorldModel worldModel2 = new WorldModel(modelCube);
        worldModel2.translate(new Vector3f(2.0f, 2.0f, 1.0f));
        Light light1 = new Light(new Vector3f(0.2f, 0.2f, 0.2f),
                new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f),
                1f, 0.09f, 0.032f);

        LightSource lightSource1 = new LightSource(modelbulb, light1);
        lightSource1.translate(new Vector3f(1f, 0f, -1f));
        lightSource1.scale(new Vector3f(0.1f, 0.1f, 0.1f));

        LightSource lightSource2 = new LightSource(modelbulb, light1);
        lightSource2.translate(new Vector3f(0f, 0f, 1f));
        lightSource2.scale(new Vector3f(0.1f, 0.1f, 0.1f));

        Material material1 = new Material(1, 1, 32f);

        SceneShaderProgram sceneShaderProgram = new SceneShaderProgram();
        sceneShaderProgram.specifySceneVertexAttribute(modelbulb);
        sceneShaderProgram.specifySceneVertexAttribute(modelCube);

        TextureLoader.loadBindTextures(sceneShaderProgram, "res/diffuse.jpg", GL13.GL_TEXTURE0);
        TextureLoader.loadBindTextures(sceneShaderProgram, "res/diffuse.jpg", GL13.GL_TEXTURE1);
        TextureLoader.loadBindTextures(sceneShaderProgram, "res/specular.jpg", GL13.GL_TEXTURE2);

        Camera camera = new Camera();
        camera.translateZ--;
        camera.moveCamera();

        //setting uniforms
        sceneShaderProgram.setViewMatrix(camera.getViewMatrix());
        sceneShaderProgram.setProjMatrix(camera.getProjectionMatrix());

        //setting up lighting
        sceneShaderProgram.setPointLights(lightSource1.getLight());
        sceneShaderProgram.setPointLights(lightSource2.getLight());
        sceneShaderProgram.setMaterial(material1);

        Renderer sceneRenderer = new Renderer(sceneShaderProgram);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GLUtils.errorCheck();
        //Game loop starts
        boolean active = true;
        while (active) {
            //Show things on the display
            Display.update();
            //Sync to 60 fps (If possible)
            Display.sync(FPS);

            //Clear all channels: color, depth, and stencils
            sceneRenderer.prepare();

            //rendering stuff
            sceneRenderer.render(lightSource1);
            sceneRenderer.render(lightSource2);
            sceneRenderer.render(worldModel1);
            sceneRenderer.render(worldModel2);

            //Check if pressed the close button
            boolean closeRequested = Display.isCloseRequested();

            // Keyboard
            while (Keyboard.next()) {
                Input.listenKeyboard(camera);
                boolean isDown = Keyboard.getEventKeyState();
                if (isDown == true) {
                    camera.moveCamera();
                    sceneShaderProgram.setViewMatrix(camera.getViewMatrix());
                }
            }

            //Pressed? Break out of the loop
            if (closeRequested) {
                active = false;
            }
        }
    }

}
