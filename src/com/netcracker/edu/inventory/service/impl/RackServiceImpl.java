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

    private static final String LINE_MARKER = "\n";
    static final String STR_MARKER = "|";
    static final String STR_MARKER_2 = " ";

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack != null) {
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Output stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + outputStream, illegalArgumentException);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
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
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Input stream can't be: ");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + inputStream, illegalArgumentException);
            throw illegalArgumentException;
        }
        DataInputStream dataInput = new DataInputStream(inputStream);
        int rackSize = dataInput.readInt();
        String rackClass = dataInput.readUTF();
        Rack rack;
        try {
            Class clazzRack = Class.forName(rackClass);
            rack = new RackArrayImpl(rackSize, clazzRack);
            String deviceClassName;
            Device device;
            String deviceNull;
            for (int i = 0; i < rackSize; i++) {
                deviceNull = dataInput.readUTF();
                if (!deviceNull.equals(LINE_MARKER)) {
                    deviceClassName = dataInput.readUTF();
                    Class clazzDevice = Class.forName(deviceClassName);
                    device = new DeviceServiceImpl().deviceInitialization(clazzDevice);
                    if (device != null) {
                        new DeviceServiceImpl().readFieldsOfDevice(device, dataInput);
                        rack.insertDevToSlot(device, i);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found", e);
            throw e;
        }
        return rack;
    }

    public void writeRack(Rack rack, Writer writer) throws IOException {
        if (rack != null) {
            if (writer == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Writer stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + writer, illegalArgumentException);
                throw illegalArgumentException;
            }

            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            StringBuffer rackString = new StringBuffer(rack.getSize() + STR_MARKER_2);
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
            System.out.println(rackString);
        }
    }

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Input stream can't be: ");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + reader, illegalArgumentException);
            throw illegalArgumentException;
        }
        String rackOfDevice = new DeviceServiceImpl().readStringFromBuffer(reader);
        String[] arrayRack = rackOfDevice.split(" ");
        int rackSize = Integer.parseInt(arrayRack[0]);
        String rackClass = arrayRack[1];
        Rack rack;
        try {
            Class clazzRack = Class.forName(rackClass);
            rack = new RackArrayImpl(rackSize, clazzRack);
            String deviceClassName;
            String deviceFields;
            Device device;
            for (int i = 0; i < rackSize; i++) {
                deviceClassName = new DeviceServiceImpl().readStringFromBuffer(reader);
                if (!deviceClassName.equals("")) {
                    deviceFields = new DeviceServiceImpl().readStringFromBuffer(reader);
                    Class clazzDevice = Class.forName(deviceClassName);
                    device = new DeviceServiceImpl().deviceInitialization(clazzDevice);
                    if (device != null) {
                        new DeviceServiceImpl().readStringFieldsOfDevice(device, deviceFields);
                        rack.insertDevToSlot(device, i);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found", e);
            throw e;
        }
        return rack;
    }
}
