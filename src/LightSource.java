
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
    
    private Light light;
    
    public LightSource(Model model, Light light) {
        super(model);
        this.light = new Light(light);
    }
    
    public void rotate(float radians, Vector3f axis) {
        super.rotate(radians, axis);
        setLightPosition();
    }
    
    public void translate(Vector3f vector) {
        super.translate(vector);
        setLightPosition();
    }
    
    public void setLightPosition() {
        this.light.setPosition(new Vector3f(modelMatrix.m30, modelMatrix.m31, modelMatrix.m32));
    }

    public Light getLight() {
        return light;
    }
}
