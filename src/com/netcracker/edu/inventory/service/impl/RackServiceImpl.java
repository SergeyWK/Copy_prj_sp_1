package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class RackServiceImpl implements RackService {

    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());

    private static final String ERROR_MESSAGE = "An unfinished execution path.";

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack != null) {
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
                LOGGER.log(Level.SEVERE, illegalArgumentException + ", Output stream can't be: " + outputStream);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            writeRackData(rack, dataOutputStream);
            dataOutputStream.flush();
        }
    }

    private void writeRackData(Rack rack, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(rack.getSize());
        dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());
        dataOutputStream.writeInt(rack.getAllDeviceAsArray().length);
        for (int i = 0; i <= rack.getSize()-1; i++) {
            if (rack.getDevAtSlot(i) != null) {
                dataOutputStream.writeInt(i);
                new DeviceServiceImpl().outputDevice(rack.getDevAtSlot(i), dataOutputStream);
            } else {

            }
        }
    }

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
            LOGGER.log(Level.SEVERE, illegalArgumentException + ", Input stream can't be: " + inputStream);
            throw illegalArgumentException;
        }
        DataInputStream dataInput = new DataInputStream(inputStream);
        int rackSize = dataInput.readInt();
        String rackClass = dataInput.readUTF();
        int amountDevice = dataInput.readInt();
        Class clazzRack = Class.forName(rackClass);
        Rack rack = new RackArrayImpl(rackSize, clazzRack);
        String deviceClassName;
        Device device;
        int devIndex;
        for (int i = 0; i < amountDevice; i++) {
            devIndex = dataInput.readInt();
            deviceClassName = dataInput.readUTF();
            try {
                Class clazz = Class.forName(deviceClassName);
                device = new DeviceServiceImpl().deviceInitialization(clazz);
                if (device != null) {
                    new DeviceServiceImpl().readFieldsOfDevice(device, dataInput);
                    rack.insertDevToSlot(device, devIndex);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, e + deviceClassName);
                throw e;
            }
        }
        return rack;
    }

    public void writeRack(Rack rack, Writer writer) throws IOException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, ERROR_MESSAGE, notImplementedException);
        throw notImplementedException;
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, ERROR_MESSAGE, notImplementedException);
        throw notImplementedException;
    }
}
