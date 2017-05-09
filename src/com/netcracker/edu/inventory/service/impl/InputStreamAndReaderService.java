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
        FillableEntity.Field[] fields = device.getAllFieldsToArray();
        for (int i = 0; i < fields.length; i++) {
            if (Integer.class.isAssignableFrom(fields[i].getType())) {
                fields[i].setValue(dataInput.readInt());
            }
            if (String.class.isAssignableFrom(fields[i].getType())) {
                String stringValue = dataInput.readUTF();
                fields[i].setValue(stringValue.equals(LINE_MARKER) ? null : stringValue);
            }
            if (Date.class.isAssignableFrom(fields[i].getType())) {
                Long date = dataInput.readLong();
                if (date != -1) {
                    fields[i].setValue(new Date(date));
                } else {
                    fields[i].setValue(null);
                }
            }
        }
        device.fillAllFields(fields);
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
        FillableEntity.Field fields[] = device.getAllFieldsToArray();
        for (int i = 0; i < fields.length; i++) {
            if (Integer.class.isAssignableFrom(fields[i].getType())) {
                fields[i].setValue(Integer.parseInt(deviceField.nextToken().trim()));
                if (i == 0) {
                    deviceField.nextToken(STRING_TOKEN);
                }
                System.out.println(fields[i].getValue());
            } else if (String.class.isAssignableFrom(fields[i].getType())) {
                String stringValue = deviceField.nextToken(STRING_TOKEN);
                fields[i].setValue(stringValue.equals(SPACE_SEPARATOR) ? null : stringValue.substring(1, stringValue.length() - 1));
                System.out.println(fields[i].getValue());
            } else if (Date.class.isAssignableFrom(fields[i].getType())) {
                String devDate = deviceField.nextToken(STRING_TOKEN);
                if (!devDate.equals(SPACE_SEPARATOR)) {
                    Long date = Long.parseLong(devDate.trim());
                    if (date != -1) {
                        fields[i].setValue(new Date(date));
                        System.out.println(fields[i].getValue());
                    } else {
                        fields[i].setValue(null);
                    }
                }
            }
        }
        device.fillAllFields(fields);
       /* device.setIn(Integer.parseInt(deviceField.nextToken()));
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
        }*/
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
