package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.RackService;

import java.io.*;

class RackServiceImpl implements RackService {

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
       new OutputStreamAndWriterService().outputRack(rack, outputStream);
    }

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
       return new InputStreamAndReaderService().inputRack(inputStream);
    }

    public void writeRack(Rack rack, Writer writer) throws IOException {
       new OutputStreamAndWriterService().writeRack(rack, writer);
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
       return new InputStreamAndReaderService().readRack(reader);
    }
}
