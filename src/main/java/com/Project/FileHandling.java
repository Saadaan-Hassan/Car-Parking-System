package com.Project;

import java.io.*;
import java.util.ArrayList;

public class FileHandling {

    /*=================================== Write To File ===================================*/

    //Generic Function to write all types of objects in the provided file.
    public static <T> void writeToFile(String fileName, T t) {
        File file = new File(fileName);
        FileOutputStream fos;

        try {
            if (!file.exists()) file.createNewFile();

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
    //======================================================================================//

    /*=================================== Read From File ===================================*/

    //Generic Function to read all the objects of any type from the provided file and return them as an ArrayList.
    public static <T> ArrayList<T> readFromFile(String fileName) {
        File file = new File(fileName);

        ArrayList<T> arr = new ArrayList<>();
        Object obj;

        try {
//            InputStream is = FileHandling.class.getResourceAsStream(fileName);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois;

            if (fis.available() > 0)
                ois = new ObjectInputStream(fis);
            else
                return arr;

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
        return arr;
    }

}
