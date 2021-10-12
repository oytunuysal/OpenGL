
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oytun
 */
public class Camera {

    //maybe store vec3 instead?
//    public float scaleX, scaleY, scaleZ;
    public int rotateX, rotateY, rotateZ;
    public float translateX, translateY, translateZ;
    private Matrix4f viewMatrix, projectionMatrix;

    public Camera() {
//        scaleX = 1.0f;
//        scaleY = 1.0f;
//        scaleZ = 1.0f;
        rotateX = 0;
        rotateY = 0;
        rotateZ = 0;
        translateX = 0.0f;
        translateY = 0.0f;
        translateZ = 0.0f;

        viewMatrix = new Matrix4f();
        moveCamera();
        createProjectionAndPerspective();
    }

    public void moveCamera() {
        this.viewMatrix.setIdentity();
        this.viewMatrix.rotate((float) Math.toRadians(rotateX), new Vector3f(1.0f, 0.0f, 0.0f));
        this.viewMatrix.rotate((float) Math.toRadians(rotateY), new Vector3f(0.0f, 1.0f, 0.0f));
        this.viewMatrix.rotate((float) Math.toRadians(rotateZ), new Vector3f(0.0f, 0.0f, 1.0f));
        this.viewMatrix.translate(new Vector3f(translateX, translateY, translateZ));
    }

    private void createProjectionAndPerspective() {
        projectionMatrix = new Matrix4f();
        setProjectionMatrix(projectionMatrix, 90, 0.1f, 20.0f);
//        System.out.println(proj);
    }

    private void setProjectionMatrix(Matrix4f matrix, float angleOfView, float near, float far) {
        float scale = 1 / (float) Math.tan(angleOfView * 0.5 * Math.PI / 180);
        matrix.m00 = scale;// scale the x coordinates of the projected point 
        matrix.m11 = scale;// scale the y coordinates of the projected point 
        matrix.m22 = -far / (far - near); // used to remap z to [0,1] 
        matrix.m32 = -far * near / (far - near); // used to remap z [0,1]
        matrix.m23 = -1; // set w = -z 
        matrix.m33 = 0;
    }

    public FloatBuffer getViewMatrix() {
        return GLUtils.toBuffer(viewMatrix);
    }

    public FloatBuffer getProjectionMatrix() {
        return GLUtils.toBuffer(projectionMatrix);
    }

    public FloatBuffer toBuffer() {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        floatBuffer.flip();
        return floatBuffer;
    }

    public void offsetTranslate(Vector3f vector) {
        translateX += vector.x;
        translateY += vector.y;
        translateZ += vector.z;
    }

    public void offsetRotate(Vector3f vector) {
        rotateX += vector.x;
        rotateY += vector.y;
        rotateZ += vector.z;
    }
}
