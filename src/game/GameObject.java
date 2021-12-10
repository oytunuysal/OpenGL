/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import models.Material;
import models.WorldModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Oytun
 */
public class GameObject implements Comparable<GameObject> {

    private WorldModel model;
    private String textureName;
    private Material material;
    private double distanceToCamera;
    //material??

    public GameObject(WorldModel model, String textureName, Material material) {
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
    public int compareTo(GameObject gameObject) {
        return Double.compare(gameObject.getDistanceToCamera(), getDistanceToCamera());
    }

}
