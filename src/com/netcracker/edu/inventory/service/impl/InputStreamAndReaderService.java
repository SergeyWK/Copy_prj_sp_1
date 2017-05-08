package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FillableEntity;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.impl.ServiceImpl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputStreamAndReaderService {

    static protected Logger LOGGER = Logger.getLogger(InputStreamAndReaderService.class.getName());

    static final String LINE_MARKER = "\n";
    static final String STRING_TOKEN = "|";
    static final String SPACE_SEPARATOR = " ";

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Input stream can't be: ");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + inputStream, illegalArgumentException);
            throw illegalArgumentException;
        }
        DataInputStream dataInput = new DataInputStream(inputStream);
        String deviceClassName = dataInput.readUTF();
        Device device;
        try {
            Class clazz = Class.forName(deviceClassName);
            device = deviceInitialization(clazz);
            if (device != null) {
                readFieldsOfDevice(device, dataInput);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found", e);
            throw e;
        }
        return device;
    }

    public Device deviceInitialization(Class deviceClass) {
        if (Battery.class.equals(deviceClass)) {
            return new Battery();
        } else if (Router.class.equals(deviceClass)) {
            return new Router();
        } else if (Switch.class.equals(deviceClass)) {
            return new Switch();
        } else if (WifiRouter.class.equals(deviceClass)) {
            return new WifiRouter();
        }
        ClassCastException classCastException = new ClassCastException("The resulting class is not a device: ");
        LOGGER.log(Level.SEVERE, classCastException.getMessage() + deviceClass, classCastException);
        throw classCastException;
    }

    public void readFieldsOfDevice(Device device, DataInputStream dataInput) throws IOException {
        FillableEntity.Field[] fields = new FillableEntity.Field[device.getAllFieldsToArray().length];
        fields[0] = new FillableEntity.Field(Integer.class, dataInput.readInt());
        fields[1] = new FillableEntity.Field(String.class, readValue(dataInput.readUTF()));
        fields[2] = new FillableEntity.Field(String.class, readValue(dataInput.readUTF()));
        fields[3] = new FillableEntity.Field(String.class, readValue(dataInput.readUTF()));
        long deviceProductionDate = dataInput.readLong();
        Date date = deviceProductionDate == -1 ? null : new Date(deviceProductionDate);
        fields[4] = new FillableEntity.Field(Date.class, date);
        if (device instanceof Battery) {
            fields[5] = new FillableEntity.Field(Integer.class, dataInput.readInt());
        }
        if (device instanceof Router) {
            fields[5] = new FillableEntity.Field(Integer.class, dataInput.readInt());
            if (device instanceof Switch) {
                fields[6] = new FillableEntity.Field(Integer.class, dataInput.readInt());
            }
            if (device instanceof WifiRouter) {
                fields[6] = new FillableEntity.Field(String.class, dataInput.readUTF());
            }
        }
        device.fillAllFields(fields);
    }

  /*  public void readFieldsOfDevice(Device device, DataInputStream dataInput) throws IOException {
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
        if (device instanceof Battery) {
            ((Battery) device).setChargeVolume(dataInput.readInt());
        }
        if (device instanceof Router) {
            ((Router) device).setDataRate(dataInput.readInt());
            if (device instanceof Switch) {
                ((Switch) device).setNumberOfPorts(dataInput.readInt());
            }
            if (device instanceof WifiRouter) {
                ((WifiRouter) device).setSecurityProtocol(readValue(dataInput.readUTF()));
            }

        }
    }*/

    public String readValue(String value) {
        if (LINE_MARKER.equals(value)) {
            return null;
        }
        return value;
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Reader stream can't be: ");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + reader, illegalArgumentException);
            throw illegalArgumentException;
        }
        String deviceClassName = readStringFromBuffer(reader);
        String deviceFields = readStringFromBuffer(reader);
        Device device;
        try {
            Class clazzDevice = Class.forName(deviceClassName);
            device = deviceInitialization(clazzDevice);
            if (device != null) {
                readStringFieldsOfDevice(device, deviceFields);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found", e);
            throw e;
        }
        return device;
    }

    public void readStringFieldsOfDevice(Device device, String deviceFields) throws IOException {
        StringTokenizer deviceField = new StringTokenizer(deviceFields, " |");
        device.setIn(Integer.parseInt(deviceField.nextToken()));
        deviceField.nextToken(STRING_TOKEN);
        String devType = deviceField.nextToken(STRING_TOKEN);
        device.setType(devType.equals(SPACE_SEPARATOR) ? null : devType.substring(1, devType.length() - 1));
        String devModel = deviceField.nextToken(STRING_TOKEN);
        device.setModel(devModel.equals(SPACE_SEPARATOR) ? null : devModel.substring(1, devModel.length() - 1));
        String devManufacturer = deviceField.nextToken(STRING_TOKEN);
        device.setManufacturer(devManufacturer.equals(SPACE_SEPARATOR) ? null : devManufacturer.substring(1, devManufacturer.length() - 1));
        String devDate = deviceField.nextToken(STRING_TOKEN);
        if (!devDate.equals(SPACE_SEPARATOR)) {
            Long date = Long.parseLong(devDate.trim());
            if (date != -1) {
                device.setProductionDate(new Date(date));
            } else {
                device.setProductionDate(null);
            }
        }
        if (Battery.class.isAssignableFrom(device.getClass())) {
            String devChargeVolume = deviceField.nextToken(STRING_TOKEN);
            int value = Integer.parseInt(devChargeVolume.trim());
            ((Battery) device).setChargeVolume(value);
        }
        if (Router.class.isAssignableFrom(device.getClass())) {
            String devDataRate = deviceField.nextToken(STRING_TOKEN);
            int value = Integer.parseInt(devDataRate.trim());
            ((Router) device).setDataRate(value);
        }
        if (Switch.class.isAssignableFrom(device.getClass())) {
            String devNumOfPorts = deviceField.nextToken(STRING_TOKEN);
            int value = Integer.parseInt(devNumOfPorts.trim());
            ((Switch) device).setNumberOfPorts(value);
        }
        if (WifiRouter.class.isAssignableFrom(device.getClass())) {
            String devSecProtocol = deviceField.nextToken(STRING_TOKEN);
            if (!devSecProtocol.equals(SPACE_SEPARATOR)) {
                ((WifiRouter) device).setSecurityProtocol(devSecProtocol.replaceAll("[\\s]{3}", " "));
            } else {
                ((WifiRouter) device).setSecurityProtocol(null);
            }
        }
    }

    public String readStringFromBuffer(Reader reader) throws IOException {
        char signRead;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            signRead = ((char) reader.read());
            if (signRead == LINE_MARKER.charAt(0) || signRead == 0xFFFF) break;
            stringBuilder.append(signRead);
        }
        return stringBuilder.toString();
    }

    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Input stream can't be: ");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + inputStream, illegalArgumentException);
            throw illegalArgumentException;
        }
        Location location = new com.netcracker.edu.location.impl.ServiceImpl().inputLocation(inputStream);
        DataInputStream dataInput = new DataInputStream(inputStream);
        int rackSize = dataInput.readInt();
        String rackClass = dataInput.readUTF();
        Rack rack;
        try {
            Class clazzRack = Class.forName(rackClass);
            rack = new RackArrayImpl(rackSize, clazzRack);
            rack.setLocation(location);
            String deviceClassName;
            Device device;
            String deviceNull;
            for (int i = 0; i < rackSize; i++) {
                deviceNull = dataInput.readUTF();
                if (!deviceNull.equals(LINE_MARKER)) {
                    deviceClassName = dataInput.readUTF();
                    Class clazzDevice = Class.forName(deviceClassName);
                    device = deviceInitialization(clazzDevice);
                    if (device != null) {
                        readFieldsOfDevice(device, dataInput);
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

    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        if (reader == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Reader stream can't be: ");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + reader, illegalArgumentException);
            throw illegalArgumentException;
        }
        Location location = new ServiceImpl().readLocation(reader);
        String rackOfDevice = readStringFromBuffer(reader);
        String[] arrayRack = rackOfDevice.split(" ");
        int rackSize = Integer.parseInt(arrayRack[0]);
        String rackClass = arrayRack[1];
        Rack rack;
        try {
            Class clazzRack = Class.forName(rackClass);
            rack = new RackArrayImpl(rackSize, clazzRack);
            rack.setLocation(location);
            String deviceClassName;
            String deviceFields;
            Device device;
            for (int i = 0; i < rackSize; i++) {
                deviceClassName = readStringFromBuffer(reader);
                if (!deviceClassName.equals("")) {
                    deviceFields = readStringFromBuffer(reader);
                    Class clazzDevice = Class.forName(deviceClassName);
                    device = deviceInitialization(clazzDevice);
                    if (device != null) {
                        readStringFieldsOfDevice(device, deviceFields);
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
