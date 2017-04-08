package com.netcracker.edu.inventory.service.impl;


import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class DeviceServiceImpl implements DeviceService {

    static protected Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

    public void sortByIN(Device[] devices) {
        for (int i = 0; i < devices.length; i++) {
            for (int j = 1 + i; j < devices.length; j++) {
                if ((devices[i] != null && devices[j] != null
                        && ((devices[j].getIn() != 0
                        && devices[j].getIn() < devices[i].getIn())
                        || (devices[i].getIn() == 0)))
                        || (devices[i] == null)) {
                    Device deviceMemory = devices[i];
                    devices[i] = devices[j];
                    devices[j] = deviceMemory;
                }
            }
        }
    }

    public void sortByProductionDate(Device[] devices) {
        for (int i = 0; i < devices.length; i++) {
            for (int j = 1 + i; j < devices.length; j++) {
                if ((devices[i] != null && devices[j] != null
                        && ((devices[j].getProductionDate() != null
                        && isAfter(devices[j].getProductionDate(), devices[i].getProductionDate()))
                        || (devices[i].getProductionDate() == null)))
                        || (devices[i] == null)) {
                    Device deviceMemory = devices[i];
                    devices[i] = devices[j];
                    devices[j] = deviceMemory;
                }
            }
        }
    }

    private boolean isAfter(Date date1, Date date2) {
        if (date2 == null) {
            return false;
        }
        return date1.compareTo(date2) < 0;
    }

    public void filtrateByType(Device[] devices, String type) {
        for (int i = 0; i < devices.length; i++) {
            if (devices[i] != null) {
                if ((type == null && null != devices[i].getType())
                        || (type != null && !type.equals(devices[i].getType()))) {
                    devices[i] = null;
                }
            }
        }
    }

    public void filtrateByManufacturer(Device[] devices, String manufacturer) {
        for (int i = 0; i < devices.length; i++) {
            if (devices[i] != null) {
                if ((manufacturer == null && null != devices[i].getManufacturer())
                        || (manufacturer != null && !manufacturer.equals(devices[i].getManufacturer()))) {
                    devices[i] = null;
                }
            }
        }
    }

    public void filtrateByModel(Device[] devices, String model) {
        for (int i = 0; i < devices.length; i++) {
            if (devices[i] != null) {
                if ((model == null && null != devices[i].getModel())
                        || (model != null && !model.equals(devices[i].getModel()))) {
                    devices[i] = null;
                }
            }
        }
    }

    public boolean isValidDeviceForInsertToRack(Device device) {
        if ((!(device != null && device.getIn() > 0))) {
            return false;
        }
        return true;
    }


    public boolean isValidDeviceForOutputToStream(Device device) {
        if (device != null) {
            String deviceModel = device.getModel();
            String deviceManufacturer = device.getManufacturer();
            String deviceType = device.getType();
            String routerSecurityProtocol = ((WifiRouter) device).getSecurityProtocol();
            boolean validObject = (isValidField(deviceModel)) && (isValidField(deviceManufacturer))
                    && (isValidField(deviceType) && (isValidField(routerSecurityProtocol)));

            return validObject;
        }
        return false;
    }

    private boolean isValidField(String field) {
        if (field == null) {
            return true;
        }
        return !field.contains("\n");
    }

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device != null) {
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
                LOGGER.log(Level.SEVERE, illegalArgumentException + ", Output stream can't be: " + outputStream);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            ServiceHelper.writeDevice(device, dataOutputStream);
            ServiceHelper.writeSpecificDevice(device, dataOutputStream);
            dataOutputStream.flush();
        }
    }

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
            LOGGER.log(Level.SEVERE, illegalArgumentException + ", Input stream can't be: " + inputStream);
            throw illegalArgumentException;
        }
        DataInputStream dataInput = new DataInputStream(inputStream);
        String deviceClassName = dataInput.readUTF();
        Device device;
        try {
            Class clazz = Class.forName(deviceClassName);
            device = ServiceHelper.deviceInitialization(clazz);
            if (device != null) {
                ServiceHelper.readDevice(device, dataInput);
                ServiceHelper.readSpecificDevice(device, dataInput);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e + deviceClassName);
            throw e;
        }
        return device;
    }

    public void writeDevice(Device device, Writer writer) throws IOException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, notImplementedException + ", An unfinished execution path");
        throw notImplementedException;
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, notImplementedException + ", An unfinished execution path");
        throw notImplementedException;
    }
}
