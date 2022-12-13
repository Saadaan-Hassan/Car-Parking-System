package com.Project;

import java.io.*;
import java.util.ArrayList;

public class FileHandling {
    static FileOutputStream fos = null;

    public static <T> void writeToFile(String fileName, T t) {
        File file = new File(fileName);

        try {
            if (file.length() == 0) {
                fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(t);
                oos.close();
            } else {
                fos = new FileOutputStream(file, true);
                MyObjectOutputStream oos = new MyObjectOutputStream(fos);
                oos.writeObject(t);
                oos.close();
            }

            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void appendToFile(String fileName, T t) {
        File file = new File(fileName);

        try {
            fos = new FileOutputStream(file, true);

            if (file.length() == 0) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(t);
                oos.close();
            } else {
                MyObjectOutputStream oos = new MyObjectOutputStream(fos);
                oos.writeObject(t);
                oos.close();
            }

            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> ArrayList<T> readFromFile(String fileName) {
        File file = new File(fileName);
        ArrayList<T> arr = new ArrayList<>();
        Object obj;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while ((fis.available() > 0)) {
                obj = ois.readObject();
                arr.add((T) obj);
            }

            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("In file handling java file");
        System.out.println(arr);
        System.out.println("============================");
        return arr;
    }
}
