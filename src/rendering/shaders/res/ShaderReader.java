package rendering.shaders.res;

import java.io.InputStream;
import java.util.Scanner;

public class ShaderReader {

    public static String readFile(String fileName) {
        InputStream shaderStream = ShaderReader.class.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(shaderStream);
        String data = new String();
        while (scanner.hasNextLine()) {
            data += scanner.nextLine() + "\n";
        }
        scanner.close();
        return data;
    }
}
