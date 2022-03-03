package game;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

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

    public static boolean isKeyUp(int key) {
        return !isKeyDown(key);
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
