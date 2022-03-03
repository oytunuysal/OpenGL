package rendering.shaders;

import rendering.models.Model;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public abstract class ShaderProgram {

    protected int shaderProgramLocation;

    public void use() {
        GL20.glUseProgram(shaderProgramLocation);
    }

    public void specifyVertexAttribute(Model model, String attributeName, int size,
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
