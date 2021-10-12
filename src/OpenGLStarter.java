
import java.io.File;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public class OpenGLStarter {

    private static final boolean DEBUG = true;

    public static void setupOpenGl() {
        try {
            //Setup the library path specifically to be the /lib folder
            System.setProperty("java.library.path", "lib");

            //Tell LWJGL to look in the /natives folder for .dll files
            System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());

            setupLwjgl();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static void setupLwjgl() throws LWJGLException {
        //0 alpha
        //8 bit depth
        //8 bit stencils
        PixelFormat pixelFormat = new PixelFormat(0, 8, 8);

        //Create attributes for drawing
        ContextAttribs contextAtrributes;
        if (DEBUG) {
            //OpenGL 4.3 has extra debug settings. So try if it works
            contextAtrributes = new ContextAttribs(4, 3);
            contextAtrributes.withDebug(true);
        } else {
            //Otherwise 4.2 - Don't remember why
            contextAtrributes = new ContextAttribs(4, 2);
        }

        //So you can use old shaders I think. Or newer?
        contextAtrributes.withForwardCompatible(false);
        //?? No idea. Maybe important
        contextAtrributes.withProfileCore(true);

        //Setting the screen size
        Display.setDisplayMode(new DisplayMode(800, 600));
        //Resizeable? Keep false for now. Lots of work to make it work otherwise
        Display.setResizable(false);
        //Create the display!!
        Display.create(pixelFormat, contextAtrributes);

        //Set the GL viewport. Usually 0, 0, width, height is what you want
        GL11.glViewport(0, 0, 800, 600);

        //Set to clear color. This one is known as cornflower blue and is super clich� as a test clear color!
        GL11.glClearColor(0.60f, 0.80f, 0.92f, 1);

        //If debug mode enabled, this should give more detailed reports if a shader fails to compile.. Maybe..
        if (DEBUG) {
            KHRDebugCallback callback = new KHRDebugCallback();
            GL43.glDebugMessageCallback(callback);
        }

        //Initialize Keyboard, so you can listen for keys
        Keyboard.create();
    }
}
