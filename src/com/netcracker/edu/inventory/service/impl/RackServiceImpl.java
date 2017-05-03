package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.impl.ServiceImpl;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class RackServiceImpl implements RackService {

    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());

    private static final String LINE_MARKER = "\n";

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack != null) {
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Output stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + outputStream, illegalArgumentException);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            new com.netcracker.edu.location.impl.ServiceImpl().outputLocation(rack.getLocation(), outputStream);
            dataOutputStream.writeInt(rack.getSize());
            dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());
            for (int i = 0; i < rack.getSize(); i++) {
                if (rack.getDevAtSlot(i) != null) {
                    dataOutputStream.writeUTF("");
                    new DeviceServiceImpl().outputDevice(rack.getDevAtSlot(i), dataOutputStream);
                } else {
                    dataOutputStream.writeUTF(LINE_MARKER);
                }
            }
            dataOutputStream.flush();
        }
    }

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
       return new InputStreamAndReaderService().inputRack(inputStream);
    }

    public void writeRack(Rack rack, Writer writer) throws IOException {
        if (rack != null) {
            if (writer == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Writer stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + writer, illegalArgumentException);
                throw illegalArgumentException;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            new com.netcracker.edu.location.impl.ServiceImpl().writeLocation(rack.getLocation(), writer);
            StringBuffer rackString = new StringBuffer(rack.getSize() + " ");
            rackString.append(rack.getTypeOfDevices().getName()).append(LINE_MARKER);
            bufferedWriter.write(rackString.toString());
            bufferedWriter.flush();
            for (int i = 0; i < rack.getSize(); i++) {
                if (rack.getDevAtSlot(i) != null) {
                    new DeviceServiceImpl().writeDevice(rack.getDevAtSlot(i), writer);
                } else {
                    bufferedWriter.write(LINE_MARKER);
                    bufferedWriter.flush();
                }
            }
        }
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
       return new InputStreamAndReaderService().readRack(reader);
    }
}
