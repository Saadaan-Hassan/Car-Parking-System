package com.Project;

import java.io.*;
import java.util.ArrayList;

public class FileHandling{

    public static void writeToFile(String file, Object obj){
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.close();
            fos.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static <T> ArrayList<T> readFromFile(String file) {
        ArrayList<T> arr = new ArrayList<>();
        T t;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while ((t = (T)ois.readObject()) != null)
                arr.add(t);

            ois.close();
            fis.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
            return arr;

    }
}
