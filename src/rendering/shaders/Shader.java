package rendering.shaders;

import java.util.ArrayList;
import java.util.Scanner;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.shaders.res.ShaderReader;

public class Shader {

    private int shaderLocation;

    public Shader(int shaderType, String sourceFileName) {
        shaderLocation = GL20.glCreateShader(shaderType);
        String shaderSource = ShaderReader.readFile(sourceFileName);
        GL20.glShaderSource(shaderLocation, shaderSource);
        GL20.glCompileShader(shaderLocation);
        checkCompileStatus(shaderLocation, shaderSource);
    }

    public int getShaderLocation() {
        return shaderLocation;
    }

    public int shaderLocation() {
        return shaderLocation;
    }

    private static void checkCompileStatus(int shaderId, String source) {
        int shaderCompileStatus = GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS);
        if (shaderCompileStatus == GL11.GL_TRUE) {
            String msg = String.format("shader %s compiled.", shaderId);
            System.out.println(msg);
        } else {
            Scanner scan = new Scanner(source);
            StringBuilder formatSource = new StringBuilder();
            int lineCount = 1;
            String lineTemplate = "(%s)\t";
            String lineBreak = "\n";
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                formatSource.append(String.format(lineTemplate, lineCount));
                formatSource.append(line);
                formatSource.append(lineBreak);

                lineCount++;
            }

            int logLength = GL20.glGetShaderi(shaderId, GL20.GL_INFO_LOG_LENGTH);
            String error = GL20.glGetShaderInfoLog(shaderId, logLength);
            String errorMsg = String.format("Shader %s was not compiled correctly:\n%s\n%s", shaderId, error, formatSource);

            System.err.println(errorMsg);
            System.exit(-1);
        }
    }
}
