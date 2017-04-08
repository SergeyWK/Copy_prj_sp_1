package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

class RackServiceImpl implements RackService {

    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());


    public static final String LINE_MARKER = "\n";

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack != null) {
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
                LOGGER.log(Level.SEVERE, illegalArgumentException + ", Output stream can't be: " + outputStream);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            writeRack(rack, dataOutputStream);
            dataOutputStream.flush();
        }
    }

    private void writeRack(Rack rack, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(rack.getSize());
        dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());
        dataOutputStream.writeInt(rack.getAllDeviceAsArray().length);
        if (rack.getSize() > 0) {
            for (int i = 0; i <= rack.getSize() - 1; i++) {
                if (rack.getDevAtSlot(i) != null) {
                    dataOutputStream.writeInt(i);
                    writeDeviceRack(rack.getDevAtSlot(i), dataOutputStream);
                    writeSpecificDevice(rack.getDevAtSlot(i), dataOutputStream);
                }
            }
        }
    }

    private void writeDeviceRack(Device device, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(device.getClass().getName());
        dataOutputStream.writeInt(device.getIn());
        dataOutputStream.writeUTF(validObjectDevice(device.getType()));
        dataOutputStream.writeUTF(validObjectDevice(device.getModel()));
        dataOutputStream.writeUTF(validObjectDevice(device.getManufacturer()));
        dataOutputStream.writeLong(device.getProductionDate() == null ? -1 : device.getProductionDate().getTime());
    }

    private void writeSpecificDevice(Device device, DataOutputStream dataOutputStream) throws IOException {
        if (device instanceof Battery) {
            dataOutputStream.writeInt(((Battery) device).getChargeVolume());
        } else if (device instanceof Router && (Router.class.getName().equals(device.getClass().getName()))) {
            dataOutputStream.writeInt(((Router) device).getDataRate());
        } else if (device instanceof Switch) {
            dataOutputStream.writeInt(((Switch) device).getDataRate());
            dataOutputStream.writeInt(((Switch) device).getNumberOfPorts());
        } else if (device instanceof WifiRouter && (WifiRouter.class.getName().equals(device.getClass().getName()))) {
            dataOutputStream.writeInt(((WifiRouter) device).getDataRate());
            dataOutputStream.writeUTF(validObjectDevice(((WifiRouter) device).getSecurityProtocol()));
        }
    }

    private String validObjectDevice(String string) {
        if (string == null) {
            return LINE_MARKER;
        } else if (string.contains("\n")) {
            DeviceValidationException deviceValidationException = new DeviceValidationException();
            LOGGER.log(Level.SEVERE, deviceValidationException.getMessage(), deviceValidationException);
            throw deviceValidationException;
        } else {
            return string;
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
                    device = deviceInitialization(clazz);
                    if (device != null) {
                        readDevice(device, dataInput);
                        readSpecific(device, dataInput);
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

    private Rack rackInitialization(int size, Class deviceClass) {
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

    private Device deviceInitialization(Class deviceClass) {
        if (Battery.class.equals(deviceClass)) {
            return new Battery();
        } else if (Router.class.equals(deviceClass)) {
            return new Router();
        } else if (Switch.class.equals(deviceClass)) {
            return new Switch();
        } else if (WifiRouter.class.equals(deviceClass)) {
            return new WifiRouter();
        }
        ClassCastException classCastException = new ClassCastException();
        LOGGER.log(Level.SEVERE, classCastException + "The resulting class is not a device:" + deviceClass);
        throw classCastException;
    }

    private void readDevice(Device device, DataInputStream dataInput) throws IOException {
        int deviceIn = dataInput.readInt();
        String deviceType = readValue(dataInput.readUTF());
        String deviceModel = readValue(dataInput.readUTF());
        String deviceManufacturer = readValue(dataInput.readUTF());
        long deviceProductionDate = dataInput.readLong();
        Date date = deviceProductionDate == -1 ? null : new Date(deviceProductionDate);

        device.setIn(deviceIn);
        device.setType(deviceType);
        device.setModel(deviceModel);
        device.setManufacturer(deviceManufacturer);
        device.setProductionDate(date);
    }

    private void readSpecific(Device device, DataInputStream dataInput) throws IOException {
        if (device instanceof Battery) {
            ((Battery) device).setChargeVolume(dataInput.readInt());
        } else if (device instanceof Router && (Router.class.getName().equals(device.getClass().getName()))) {
            ((Router) device).setDataRate(dataInput.readInt());
        } else if (device instanceof Switch) {
            ((Switch) device).setDataRate(dataInput.readInt());
            ((Switch) device).setNumberOfPorts(dataInput.readInt());
        } else if (device instanceof WifiRouter && (WifiRouter.class.getName().equals(device.getClass().getName()))) {
            ((WifiRouter) device).setDataRate(dataInput.readInt());
            ((WifiRouter) device).setSecurityProtocol(readValue(dataInput.readUTF()));
        }
    }

    private String readValue(String value) {
        if (LINE_MARKER.equals(value)) {
            return null;
        }
        return value;
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
