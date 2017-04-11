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
        // if (rack.getSize() > 0) {
        for (int i = 0; i <= rack.getSize() - 1; i++) {
            if (rack.getDevAtSlot(i) != null) {
                dataOutputStream.writeInt(i);
                new DeviceServiceImpl().outputDevice(rack.getDevAtSlot(i), dataOutputStream);
                //ServiceImplHelper.writeFieldsOfDevice(rack.getDevAtSlot(i), dataOutputStream);
                //ServiceImplHelper.writeSpecificFieldsOfDevice(rack.getDevAtSlot(i), dataOutputStream);
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
        Rack rack = rackInitialization(rackSize, clazzRack);
        if (rackSize > 0) {
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
        }
        return rack;
    }

    public Rack rackInitialization(int size, Class deviceClass) {
        if (Battery.class.equals(deviceClass)) {
            return new RackArrayImpl(size, Battery.class);
        } else if (Router.class.equals(deviceClass)) {
            return new RackArrayImpl(size, Router.class);
        } else if (Switch.class.equals(deviceClass)) {
            return new RackArrayImpl(size, Switch.class);
        } else if (WifiRouter.class.equals(deviceClass)) {
            return new RackArrayImpl(size, WifiRouter.class);
        } else if (Device.class.equals(deviceClass)) {
            return new RackArrayImpl(size, Device.class);
        }
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
