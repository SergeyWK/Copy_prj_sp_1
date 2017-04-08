package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceHelper {

    static protected Logger LOGGER = Logger.getLogger(ServiceHelper.class.getName());

    public static final String LINE_MARKER = "\n";

    public static void writeDevice(Device device, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(device.getClass().getName());
        dataOutputStream.writeInt(device.getIn());
        dataOutputStream.writeUTF(validObjectDevice(device.getType()));
        dataOutputStream.writeUTF(validObjectDevice(device.getModel()));
        dataOutputStream.writeUTF(validObjectDevice(device.getManufacturer()));
        dataOutputStream.writeLong(device.getProductionDate() == null ? -1 : device.getProductionDate().getTime());
    }

    public static void writeSpecificDevice(Device device, DataOutputStream dataOutputStream) throws IOException {
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

    public static String validObjectDevice(String string) {
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

    public static void readDevice(Device device, DataInputStream dataInput) throws IOException {
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

    public static void readSpecificDevice(Device device, DataInputStream dataInput) throws IOException {
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

    public static Device deviceInitialization(Class deviceClass) {
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

    public static Rack rackInitialization(int size, Class deviceClass) {
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

    public static String readValue(String value) {
        if (LINE_MARKER.equals(value)) {
            return null;
        }
        return value;
    }

}
