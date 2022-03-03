package rendering;

public class Material {

//    private int diffuseMapLocation, specularMapLocation;
    private String diffuseTextureName, specularTextureName;
    private float shininess;

//    public Material(int diffuseMapLocation, int specularMapLocation, float shininess) {
//        this.diffuseMapLocation = diffuseMapLocation;
//        this.specularMapLocation = specularMapLocation;
//        this.shininess = shininess;
//    }
    public Material(String diffuse, String specular, float shininess) {
        this.diffuseTextureName = diffuse;
        this.specularTextureName = specular;
        this.shininess = shininess;
    }

    public String getDiffuseTextureName() {
        return diffuseTextureName;
    }

    public String getSpecularTextureName() {
        return specularTextureName;
    }

    public float getShininess() {
        return shininess;
    }
}
