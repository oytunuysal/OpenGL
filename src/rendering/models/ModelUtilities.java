package rendering.models;

import game.WorldObject;
import java.util.ArrayList;
import java.util.Collections;
import rendering.Material;
import rendering.models.WorldModel;
import org.lwjgl.util.vector.Vector3f;

public class ModelUtilities {

    private static Vector3f cameraPosition;

    public static void setCameraPosition(Vector3f cameraPosition) {
        ModelUtilities.cameraPosition = cameraPosition;
    }

    public static void createObject(ArrayList<WorldObject> objectList, WorldModel model, String textureName, Material material) {
        WorldObject gameObject = new WorldObject(model, textureName, material);
        objectList.add(gameObject);
    }

    public static void order(ArrayList<WorldObject> objectList) {
        for (WorldObject gameObject : objectList) {
            gameObject.refreshDistance(cameraPosition);
        }
        Collections.sort(objectList);
    }

}
