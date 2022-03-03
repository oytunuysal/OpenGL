package rendering.models.res;

import java.io.InputStream;
import java.util.Scanner;

public class ModelReader {

    public static String readFile(String fileName) {
        InputStream modelStream = ModelReader.class.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(modelStream);
        String data = new String();
        while (scanner.hasNextLine()) {
            data += scanner.nextLine() + "\n";
        }
        scanner.close();
        return data;
    }
}
