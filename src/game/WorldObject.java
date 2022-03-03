package game;

import java.util.ArrayList;
import rendering.Material;
import rendering.models.WorldModel;
import org.lwjgl.util.vector.Vector3f;

public class WorldObject implements Comparable<WorldObject> {

    private WorldModel model;
    private String textureName;
    private Material material;
    private double distanceToCamera;
    //material??

    public WorldObject(WorldModel model, String textureName, Material material) {
        this.model = model;
        this.textureName = textureName;
        this.material = material;
    }

    public double getDistanceToCamera() {
        return distanceToCamera;
    }

    public Vector3f getPosition() {
        return model.getPosition();
    }

    public Material getMaterial() {
        return material;
    }

    public WorldModel getModel() {
        return model;
    }

    public String getTextureName() {
        return textureName;
    }

    public void refreshDistance(Vector3f cameraPosition) {
        this.distanceToCamera = Math.sqrt(Math.pow(cameraPosition.x - getPosition().x, 2)
                + Math.pow(cameraPosition.y - getPosition().y, 2)
                + Math.pow(cameraPosition.z - getPosition().z, 2));
    }

    //might cause some issues
    @Override
    public int compareTo(WorldObject gameObject) {
        return Double.compare(gameObject.getDistanceToCamera(), getDistanceToCamera());
    }

}
