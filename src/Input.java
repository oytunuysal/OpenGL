
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

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

    public static HashMap<Integer, Boolean> keyboardEvents = new HashMap<>();
    private static Vector2f mouseDelta = new Vector2f();

    public static void getInputs() {
        while (Keyboard.next()) {
            keyboardEvents.put(Keyboard.getEventKey(), Keyboard.getEventKeyState());
        }
    }

    public static boolean isKeyDown(int key) {
        if (keyboardEvents.containsKey(key)) {
            return keyboardEvents.get(key);
        }
        return false;
    }

    public static Vector2f mouseDelta() {
        mouseDelta.set(Mouse.getDX(), Mouse.getDY());
        return mouseDelta;
    }

    public static boolean isRightClicked() {
        return Mouse.isButtonDown(1);
    }

    public static void hideCursor(boolean flag) {
        Mouse.setGrabbed(flag);
    }
}
