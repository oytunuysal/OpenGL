package models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Oytun
 */
public class Material {

    //????
    private int diffuseMapLocation, specularMapLocation;
    private float shininess;

    public Material(int diffuseMapLocation, int specularMapLocation, float shininess) {
        this.diffuseMapLocation = diffuseMapLocation;
        this.specularMapLocation = specularMapLocation;
        this.shininess = shininess;
    }

    public int getDiffuseMapLocation() {
        return diffuseMapLocation;
    }

    public int getSpecularMapLocation() {
        return specularMapLocation;
    }

    public float getShininess() {
        return shininess;
    }
}
