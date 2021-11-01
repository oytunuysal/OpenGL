package models;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.res.ModelReader;
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
public class ModelLoader {

    public static ArrayList<Model> loadModel(String sourceName) {

        ArrayList<Vector3f> vertexPositions = new ArrayList<>();
        ArrayList<Vector3f> vertexTexturePosition = new ArrayList<>();
        ArrayList<Vector3f> vertexNormals = new ArrayList<>();

        ArrayList<Vector3f> vao = new ArrayList<>();
        ArrayList<Model> models = new ArrayList<>();
        ArrayList<ArrayList<Vector3f>> vaos = new ArrayList<>();
        ArrayList<Integer> vertecesCounts = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        //load the file
//        String modelFile = ModelReader.readFile(name);
        InputStream modelStream = ModelReader.class.getResourceAsStream(sourceName);
        Scanner scanner = new Scanner(modelStream);
        //parsing process
        String nextLine;
        int vertecesCount = 0;
        float normalizationValue = Float.NEGATIVE_INFINITY;
        float x, y, z;
        boolean flag = false;
        String name;
        //scanner.next(pattern);
        while (scanner.hasNext()) {
            x = 0;
            y = 0;
            z = 0;

            nextLine = scanner.nextLine();
//            System.out.println(nextLine);
            Scanner scanner1 = new Scanner(nextLine);
            if (nextLine.startsWith("v ")) {
                scanner1.next();
                x = scanner1.nextFloat();
                y = scanner1.nextFloat();
                z = scanner1.nextFloat();

                if (Float.compare(normalizationValue, Math.abs(x)) < 0) {
                    normalizationValue = Math.abs(x);
                }
                if (Float.compare(normalizationValue, Math.abs(y)) < 0) {
                    normalizationValue = Math.abs(y);
                }
                if (Float.compare(normalizationValue, Math.abs(z)) < 0) {
                    normalizationValue = Math.abs(z);
                }
                vertexPositions.add(new Vector3f(x, y, z));
            } else if (nextLine.startsWith("vn ")) {
                scanner1.next();
                x = scanner1.nextFloat();
                y = scanner1.nextFloat();
                try {

                    z = scanner1.nextFloat();
                } catch (Exception e) {
                }
                vertexNormals.add(new Vector3f(x, y, z));
            } else if (nextLine.startsWith("vt ")) {
                scanner1.next();
                x = scanner1.nextFloat();
                y = scanner1.nextFloat();
                try {
                    z = scanner1.nextFloat();
                } catch (Exception e) {
                }
                vertexTexturePosition.add(new Vector3f(x, y, z));
            } else if (nextLine.startsWith("f ")) {
                nextLine = nextLine.replaceAll("/", " ");
                scanner1 = new Scanner(nextLine);
                scanner1.next();
                Vector3f positionVector1 = vertexPositions.get(scanner1.nextInt() - 1);
                vao.add(new Vector3f(positionVector1.x, positionVector1.y, positionVector1.z));
                Vector3f vtPosition1 = vertexTexturePosition.get(scanner1.nextInt() - 1);
                vao.add(vtPosition1);
                Vector3f vnVector1 = vertexNormals.get(scanner1.nextInt() - 1);
                vao.add(vnVector1);

                Vector3f positionVector2 = vertexPositions.get(scanner1.nextInt() - 1);
                vao.add(new Vector3f(positionVector2.x, positionVector2.y, positionVector2.z));
                Vector3f vtPosition2 = vertexTexturePosition.get(scanner1.nextInt() - 1);
                vao.add(vtPosition2);
                Vector3f vnVector2 = vertexNormals.get(scanner1.nextInt() - 1);
                vao.add(vnVector2);

                Vector3f positionVector3 = vertexPositions.get(scanner1.nextInt() - 1);
                vao.add(new Vector3f(positionVector3.x, positionVector3.y, positionVector3.z));
                Vector3f vtPosition3 = vertexTexturePosition.get(scanner1.nextInt() - 1);
                vao.add(vtPosition3);
                Vector3f vnVector3 = vertexNormals.get(scanner1.nextInt() - 1);
                vao.add(vnVector3);
                vertecesCount += 3;

                try {
                    Vector3f positionVector4 = vertexPositions.get(scanner1.nextInt() - 1);
                    vao.add(new Vector3f(positionVector4.x, positionVector4.y, positionVector4.z));
                    vao.add(vertexTexturePosition.get(scanner1.nextInt() - 1));
                    vao.add(vertexNormals.get(scanner1.nextInt() - 1));

                    vao.add(new Vector3f(positionVector1.x, positionVector1.y, positionVector1.z));
                    vao.add(vtPosition1);
                    vao.add(vnVector1);
                    vao.add(new Vector3f(positionVector3.x, positionVector3.y, positionVector3.z));
                    vao.add(vtPosition3);
                    vao.add(vnVector3);
                    vertecesCount += 3;
                } catch (Exception e) {
                }
            } else if (nextLine.startsWith("usemtl ")) {
                scanner1.next();
                name = scanner1.next();
                names.add(name);
                if (vao.size() > 0) {
//                    models.add(new Model(name, vao, vertecesCount));
                    vaos.add(vao);
                    vertecesCounts.add(vertecesCount);
                    vao = new ArrayList<>();
                    vertecesCount = 0;
                }
            }
        }

        if (vao.size() > 0) {
//            models.add(new Model(name, vao, vertecesCount));
            vaos.add(vao);
            vertecesCounts.add(vertecesCount);
        }
        int counter = 0;
        for (ArrayList<Vector3f> aVao : vaos) {
            for (int i = 0; i < aVao.size(); i += 3) {
                //do it for all models
                aVao.get(i).x = aVao.get(i).x / normalizationValue;
                aVao.get(i).y = aVao.get(i).y / normalizationValue;
                aVao.get(i).z = aVao.get(i).z / normalizationValue;
            }
            models.add(new Model(names.get(counter), aVao, vertecesCounts.get(counter)));
            counter++;
        }

        return models;
    }
}
