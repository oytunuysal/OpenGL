
import org.lwjgl.opengl.GL15;
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
public abstract class ShaderProgram {

    public int shaderProgramLocation;

    public void use() {
        GL20.glUseProgram(shaderProgramLocation);
    }

    public void specifyVertexAttribute(WorldModel model, String attributeName, int size,
            int type, boolean normalized, int stride, long bufferOffset) {
        //specifyAttributes?
        GL30.glBindVertexArray(model.getVao());
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getVbo());

        int attrib = GL20.glGetAttribLocation(shaderProgramLocation, attributeName);
        GL20.glEnableVertexAttribArray(attrib);
        GL20.glVertexAttribPointer(attrib, size, type, normalized, stride, bufferOffset);
    }

    public int getUniformLocation(String variable) {
        return GL20.glGetUniformLocation(shaderProgramLocation, variable);
    }

    public void updateUniform(Matrix4f matrix) {
    }
}
