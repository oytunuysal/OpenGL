package models;


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
public class Light {

    private Vector3f position, ambient, diffuse, specular;
    private float constant, linear, quadratic;

    public Light(Vector3f ambient, Vector3f diffuse, Vector3f specular, float constant, float linear, float quadratic) {
        this.position = new Vector3f();
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.constant = constant;
        this.linear = linear;
        this.quadratic = quadratic;
    }
    
    public Light(Light light) {
        this.position = new Vector3f();
        this.ambient = light.ambient;
        this.diffuse = light.diffuse;
        this.specular = light.specular;
        this.constant = light.constant;
        this.linear = light.linear;
        this.quadratic = light.quadratic;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = specular;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public void setQuadratic(float quadratic) {
        this.quadratic = quadratic;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public float getConstant() {
        return constant;
    }

    public float getLinear() {
        return linear;
    }

    public float getQuadratic() {
        return quadratic;
    }

}
