package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.RackService;

import java.io.*;

class RackServiceImpl implements RackService {
    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {

    }

    @Override
    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void writeRack(Rack rack, Writer writer) throws IOException {

    }

    @Override
    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        return null;
    }
}
