package game;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;

public class GLUtils {

    public static void errorCheck() {
        int errorID;
        while ((errorID = GL11.glGetError()) != GL11.GL_NO_ERROR) {
            String msg = String.format("GLError: [%s]", GLU.gluErrorString(errorID));

            System.err.println(msg);

            new RuntimeException().printStackTrace();
            System.exit(-1);
        }
    }

    public static FloatBuffer toBuffer(Matrix4f matrix) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        matrix.store(floatBuffer);
        floatBuffer.flip();
        return floatBuffer;
    }

    public static FloatBuffer removeTransformations(Matrix4f matrix) {
        Matrix4f transMatrix = new Matrix4f(matrix);
//        transMatrix.m03 = 0f;
//        transMatrix.m13 = 0f;
//        transMatrix.m23 = 0f;
        transMatrix.m30 = 0f;
        transMatrix.m31 = 0f;
        transMatrix.m32 = 0f;
//        transMatrix.m33 = 0f;
        return toBuffer(transMatrix);
    }
}
