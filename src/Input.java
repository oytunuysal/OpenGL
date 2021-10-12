
import org.lwjgl.input.Keyboard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oytun
 */
public class Input {

    public static void listenKeyboard(Camera camera) {
        char character = Keyboard.getEventCharacter();
        int key = Keyboard.getEventKey();
        boolean isDown = Keyboard.getEventKeyState();
        if (key == 203 && isDown == true) {
            camera.rotateZ -= 2;
        }
        if (key == 205 && isDown == true) {
            camera.rotateZ += 2;
        }
        if (key == 200 && isDown == true) {
            camera.rotateX -= 2;
        }
        if (key == 208 && isDown == true) {
            camera.rotateX += 2;
        }
        if (key == 17 && isDown == true) {
            camera.translateY -= 0.2f;
        }
        if (key == 30 && isDown == true) {
            camera.translateX += 0.2f;
        }
        if (key == 31 && isDown == true) {
            camera.translateY += 0.2f;
        }
        if (key == 32 && isDown == true) {
            camera.translateX -= 0.2f;
        }
        if (key == 57 && isDown == true) {
            camera.translateZ -= 0.2f;
        }
        if (key == 56 && isDown == true) {
            camera.translateZ += 0.2f;
        }

//        System.out.println(key);
        //17 30 31 32
    }
}
