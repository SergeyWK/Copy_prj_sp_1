package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.RackService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class RackServiceImpl implements RackService {

    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {

    }


    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        return null;
    }


    public void writeRack(Rack rack, Writer writer) throws IOException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, notImplementedException + ", An unfinished execution path");
        throw notImplementedException;
    }


    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, notImplementedException + ", An unfinished execution path");
        throw notImplementedException;
    }
}
