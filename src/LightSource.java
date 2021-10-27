
import java.util.ArrayList;
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
public class LightSource extends WorldModel {
    
    private final Light light;
    
    public LightSource(ArrayList<Model> model, Light light) {
        super(model);
        this.light = new Light(light);
    }
    
    @Override
    public void rotate(Vector3f rotationVector) {
        super.rotate(rotationVector);
        setLightPosition();
    }
    
    @Override
    public void translate(Vector3f vector) {
        super.translate(vector);
        setLightPosition();
    }
    
    public void setLightPosition() {
        this.light.setPosition(getPosition());
    }

    public Light getLight() {
        return light;
    }
}
