package com.Project;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {

    public MyObjectOutputStream(OutputStream o) throws IOException {
        super(o);
    }

    @Override
    public void writeStreamHeader() throws IOException{

    }
}
