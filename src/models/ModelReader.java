/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Oytun
 */
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
