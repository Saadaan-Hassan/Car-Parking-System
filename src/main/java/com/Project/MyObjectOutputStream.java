package com.Project;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/* Using this class to write serialized objects in append mode
* This class is indirectly used by FileHandling writeToFile() function */

public class MyObjectOutputStream extends ObjectOutputStream {

    public MyObjectOutputStream(OutputStream o) throws IOException {
        super(o);
    }

    @Override
    public void writeStreamHeader() {

    }
}
