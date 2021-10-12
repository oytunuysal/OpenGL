
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oytun
 */
public class SceneShaderProgram extends ShaderProgram {

    private final Shader sceneVertexShader, sceneFragmentShader;
    private final int model, view, proj, uniformInverseTransposedModel;
    private final int lightPosition, lightAmbient, lightDiffuse, lightSpecular,
            lightConstant, lightLinear, lightQuadratic,
            materialDiffuse, materialSpecular, materialShininess;
    private final int viewPos;

    public SceneShaderProgram() {
        shaderProgramLocation = GL20.glCreateProgram();
        sceneVertexShader = new Shader(GL20.GL_VERTEX_SHADER,
                "scene_vertex_shader.vert");

        // Create and compile the fragment shader
        sceneFragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER,
                "scene_fragment_shader.frag");
        GL20.glAttachShader(shaderProgramLocation, sceneVertexShader.getShaderLocation());
        GL20.glAttachShader(shaderProgramLocation, sceneFragmentShader.getShaderLocation());
        GL30.glBindFragDataLocation(shaderProgramLocation, 0, "outColor");
        GL20.glLinkProgram(shaderProgramLocation);

        //Vertex Shader Uniforms
        model = getUniformLocation("model");
        view = getUniformLocation("view");
        proj = getUniformLocation("proj");
        uniformInverseTransposedModel = getUniformLocation("inverseTransposedModel");

        //Fragment Shader Uniforms
        viewPos = getUniformLocation("viewPos");

        lightPosition = getUniformLocation("light.position");
        lightAmbient = getUniformLocation("light.ambient");
        lightDiffuse = getUniformLocation("light.diffuse");
        lightSpecular = getUniformLocation("light.specular");
        lightConstant = getUniformLocation("light.constant");
        lightLinear = getUniformLocation("light.linear");
        lightQuadratic = getUniformLocation("light.quadratic");
        materialDiffuse = getUniformLocation("material.diffuse");
        materialSpecular = getUniformLocation("material.specular");
        materialShininess = getUniformLocation("material.shininess");
    }

    public void specifySceneVertexAttribute(WorldModel model) {
        specifyVertexAttribute(model, "position", 3, GL11.GL_FLOAT, true, Float.BYTES * 11, 0);
        specifyVertexAttribute(model, "color", 3, GL11.GL_FLOAT, false, Float.BYTES * 11, Float.BYTES * 3);
        specifyVertexAttribute(model, "texcoord", 2, GL11.GL_FLOAT, false, Float.BYTES * 11, Float.BYTES * 6);
        specifyVertexAttribute(model, "normalVector", 3, GL11.GL_FLOAT, false, Float.BYTES * 11, Float.BYTES * 8);
    }

    public int getShaderProgramLocation() {
        return shaderProgramLocation;
    }

    public Shader getSceneVertexShader() {
        return sceneVertexShader;
    }

    public Shader getSceneFragmentShader() {
        return sceneFragmentShader;
    }

    public void setModelMatrix(FloatBuffer matrix) {
        GL20.glUniformMatrix4(model, false, matrix);
        normalVector(matrix);
    }

    public void setViewMatrix(FloatBuffer matrix) {
        GL20.glUniformMatrix4(view, false, matrix);
        GL20.glUniform3f(viewPos, -matrix.get(12), -matrix.get(13), -matrix.get(14));
    }

    public void setProjMatrix(FloatBuffer matrix) {
        GL20.glUniformMatrix4(proj, false, matrix);
    }

    public void normalVector(FloatBuffer model) {
        FloatBuffer ITbuffer = BufferUtils.createFloatBuffer(16);
        Matrix4f inverseTransposedModel = new Matrix4f();
        inverseTransposedModel.load(model);
        inverseTransposedModel.invert();
        inverseTransposedModel.transpose();
        inverseTransposedModel.store(ITbuffer);
        ITbuffer.flip();
        GL20.glUniformMatrix4(uniformInverseTransposedModel, false, ITbuffer);
    }

    public void setLight(Light light) {
        GL20.glUniform3f(lightPosition, light.getPosition().x,
                light.getPosition().y, light.getPosition().z);
        GL20.glUniform3f(lightAmbient, light.getAmbient().x,
                light.getAmbient().y, light.getAmbient().z);
        GL20.glUniform3f(lightDiffuse, light.getDiffuse().x,
                light.getDiffuse().y, light.getDiffuse().z);
        GL20.glUniform3f(lightSpecular, light.getSpecular().x,
                light.getSpecular().y, light.getSpecular().z);

        GL20.glUniform1f(lightConstant, light.getConstant());
        GL20.glUniform1f(lightLinear, light.getLinear());
        GL20.glUniform1f(lightQuadratic, light.getQuadratic());
    }

    public void setPointLights(Light light, int index) {
        GL20.glUniform3f(getUniformLocation("pointLights[" + index + "].position"),
                light.getPosition().x, light.getPosition().y, light.getPosition().z);
        GL20.glUniform3f(getUniformLocation("pointLights[" + index + "].ambient"), light.getAmbient().x,
                light.getAmbient().y, light.getAmbient().z);
        GL20.glUniform3f(getUniformLocation("pointLights[" + index + "].diffuse"), light.getDiffuse().x,
                light.getDiffuse().y, light.getDiffuse().z);
        GL20.glUniform3f(getUniformLocation("pointLights[" + index + "].specular"), light.getSpecular().x,
                light.getSpecular().y, light.getSpecular().z);

        GL20.glUniform1f(getUniformLocation("pointLights[" + index + "].constant"), light.getConstant());
        GL20.glUniform1f(getUniformLocation("pointLights[" + index + "].linear"), light.getLinear());
        GL20.glUniform1f(getUniformLocation("pointLights[" + index + "].quadratic"), light.getQuadratic());
    }

    public void setMaterial(Material material) {
        GL20.glUniform1i(materialDiffuse, material.getDiffuseMapLocation());
        GL20.glUniform1i(materialSpecular, material.getSpecularMapLocation());
        GL20.glUniform1f(materialShininess, material.getShininess());
    }

}
