package game;


import models.GeometryUtils;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
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
    public int rotateX, rotateY, rotateZ;
    private Vector3f position, negativePosition;
    private Matrix4f viewMatrix, projectionMatrix;
    private Vector2f direction2d;

    public Camera() {
        rotateX = 0;
        rotateY = 0;
        rotateZ = 0;
        position = new Vector3f();
        negativePosition = new Vector3f();
        rotateMovementVector();
        viewMatrix = new Matrix4f();
        moveCamera();
        createProjectionAndPerspective();
    }

    public void moveCamera() {
        this.viewMatrix.setIdentity();
        this.viewMatrix.rotate((float) Math.toRadians(rotateX), GeometryUtils.ROTATION_X);
        this.viewMatrix.rotate((float) Math.toRadians(rotateY), GeometryUtils.ROTATION_Y);
        this.viewMatrix.rotate((float) Math.toRadians(rotateZ), GeometryUtils.ROTATION_Z);

        this.viewMatrix.translate(position);

    }

    private void createProjectionAndPerspective() {
        projectionMatrix = new Matrix4f();
        setProjectionMatrix(projectionMatrix, 90, 0.1f, 10.0f);
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
        Vector3f.add(position, vector, position);
    }

    public void offsetRotate(Vector3f vector) {
        rotateX += vector.x;
        rotateY += vector.y;
        rotateZ += vector.z;
    }

    public Vector3f getPosition() {
        position.negate(negativePosition);
        return negativePosition;
        //return position;
    }

    private void rotateMovementVector() {
        //trigonometric values
        float x, y, z;
        float zcos, zsin;
        zcos = (float) Math.cos(Math.toRadians(rotateZ));
        zsin = (float) Math.sin(Math.toRadians(rotateZ));

        Vector2f direction2dVector = new Vector2f(0, 1);
        x = -zsin;
        y = zcos;
        direction2dVector.set(x, y);
        direction2dVector.scale(0.05f);
        this.direction2d = direction2dVector;

//        float xcos, xsin;
//        xcos = (float) Math.cos(Math.toRadians(rotateX));
//        xsin = (float) Math.sin(Math.toRadians(rotateX));
//        float ycos, ysin;
//        ycos = (float) Math.cos(Math.toRadians(rotateY));
//        ysin = (float) Math.sin(Math.toRadians(rotateY));
//        Vector3f direction3dVector = new Vector3f(0f, 0f, -1f);
        //euler angle formula for 3d
//        //X axis
//        directionVector.setY(directionVector.getY() * xcos - directionVector.getZ() * xsin);
//        directionVector.setZ(directionVector.getY() * xsin + directionVector.getZ() * xcos);
//        //Y axis
//        directionVector.setX(directionVector.getX() * ycos + directionVector.getZ() * ysin);
//        directionVector.setZ(-directionVector.getX() * ysin + directionVector.getZ() * ycos);
//        //Z axis
//        directionVector.setX(directionVector.getX() * zcos - directionVector.getY() * zsin);
//        directionVector.setY(directionVector.getX() * zsin + directionVector.getY() * zcos);
//        System.out.println(directionVector);
//        directionVector.normalise();
//        System.out.println(directionVector);
//        direction3dVector.scale(0.05f);
//        System.out.println(direction3dVector);
//        this.direction3d = direction3dVector;
//        return direction3dVector;
    }

    private boolean processKeyPress() {
        //Returns true on camera movements from keyboard
        boolean changeFlag = false;
        if (Input.isKeyDown(Keyboard.KEY_LEFT)) {
            rotateZ -= 1;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_RIGHT)) {
            rotateZ += 1;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_UP)) {
            rotateX -= 1;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_DOWN)) {
            rotateX += 1;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_W)) {
//            translateY -= 0.05f;
            position.setX(position.getX() + direction2d.x);
            position.setY(position.getY() - direction2d.y);
//            translateX +=direction2d.x;
//            translateY -= direction2d.y;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_A)) {
//            translateX += 0.05f;
            position.setX(position.getX() + direction2d.y);
            position.setY(position.getY() + direction2d.x);
//            translateX += direction2d.y;
//            translateY += direction2d.x;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_S)) {
//            translateY += 0.05f;
            position.setX(position.getX() - direction2d.x);
            position.setY(position.getY() + direction2d.y);
//            translateX -= direction2d.x;
//            translateY += direction2d.y;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_D)) {
//            translateX -= 0.05f;
            position.setX(position.getX() - direction2d.y);
            position.setY(position.getY() - direction2d.x);
//            translateX += -direction2d.y;
//            translateY += -direction2d.x;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_SPACE)) {
            position.z -= 0.05f;
//            translateZ -= 0.05f;
            changeFlag = true;
        }
        if (Input.isKeyDown(Keyboard.KEY_LMENU)) {
            position.z += 0.05f;
//            translateZ += 0.05f;
            changeFlag = true;
        }
        return changeFlag;

    }

    private boolean mouseMovement() {
        //Returns true on rotation with mouseDelta
        //Right click
        if (Input.isRightClicked()) {
            Vector2f delta = Input.mouseDelta();
            Input.hideCursor(true);
            delta.scale(0.5f);
            rotateX -= delta.y;
            rotateZ += delta.x;
            return true;
        }
        Input.hideCursor(false);
        return false;
    }

    public boolean update() {
        boolean keyboardUpdate = processKeyPress();
        boolean mouseUpdate = mouseMovement();
        if (keyboardUpdate || mouseUpdate) {
            rotateMovementVector();
            moveCamera();
        }
        return keyboardUpdate || mouseUpdate;
    }
}
