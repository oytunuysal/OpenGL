/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import models.Material;
import models.WorldModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Oytun
 */
public class ObjectFactory {
    
    private static Vector3f cameraPosition;
    
    public static void setCameraPosition(Vector3f cameraPosition) {
        ObjectFactory.cameraPosition = cameraPosition;
    }
    
    public static void createObject(ArrayList<GameObject> objectList, WorldModel model, String textureName, Material material) {
        GameObject gameObject = new GameObject(model, textureName, material);
        objectList.add(gameObject);
    }
    
    public static void refresh(ArrayList<GameObject> objectList) {
        for (GameObject gameObject : objectList) {
            gameObject.refreshDistance(cameraPosition);
        }
        Collections.sort(objectList);
    }
    
}
