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

    static final String LINE_MARKER = "\n";
    static final String LINE_MARKER_2 = "|";
    private static final String ERROR_MESSAGE = "An unfinished execution path.";

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
            return isValidDevice(device, LINE_MARKER);
        }
        return false;
    }

    public boolean isValidDeviceForWriteToStream(Device device) {
        if (device != null) {
            return isValidDevice(device, LINE_MARKER_2);
        }
        return false;
    }

    private boolean isValidDevice(Device device, String marker) {
        if (device != null) {
            String deviceModel = device.getModel();
            String deviceManufacturer = device.getManufacturer();
            String deviceType = device.getType();
            String routerSecurityProtocol = null;
            if (device instanceof Router) {
                if (device instanceof WifiRouter) {
                    routerSecurityProtocol = ((WifiRouter) device).getSecurityProtocol();
                }
            }
            boolean validObject = (isValidField(deviceModel, marker)) && (isValidField(deviceManufacturer, marker))
                    && (isValidField(deviceType, marker) && (isValidField(routerSecurityProtocol, marker)));
            return validObject;
        }
        return false;
    }

    private boolean isValidField(String field, String marker) {
        if (field == null) {
            return true;
        }
        return !field.contains(marker);
    }

    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device != null) {
            if (!isValidDeviceForOutputToStream(device)) {
                DeviceValidationException deviceValidationException =
                        new DeviceValidationException("DeviceService.outputDevice.");
                LOGGER.log(Level.SEVERE, deviceValidationException.getMessage() + device, deviceValidationException);
                throw deviceValidationException;
            }
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Output stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + outputStream, illegalArgumentException);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(device.getClass().getName());
            dataOutputStream.writeInt(device.getIn());
            dataOutputStream.writeUTF(validObjectDevice(device.getType()));
            dataOutputStream.writeUTF(validObjectDevice(device.getModel()));
            dataOutputStream.writeUTF(validObjectDevice(device.getManufacturer()));
            dataOutputStream.writeLong(device.getProductionDate() == null ? -1 : device.getProductionDate().getTime());
            if (device instanceof Battery) {
                dataOutputStream.writeInt(((Battery) device).getChargeVolume());
            }
            if (device instanceof Router) {
                if (device instanceof Switch) {
                    dataOutputStream.writeInt(((Switch) device).getNumberOfPorts());
                }
                if (device instanceof WifiRouter) {
                    dataOutputStream.writeUTF(validObjectDevice(((WifiRouter) device).getSecurityProtocol()));
                }
                dataOutputStream.writeInt(((Router) device).getDataRate());
            }
            dataOutputStream.flush();
        }
    }

    public String validObjectDevice(String type) {
        if (type == null) {
            return LINE_MARKER;
        } else {
            return type;
        }
    }

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
            LOGGER.log(Level.SEVERE, deviceClassName, e);
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
            if (device instanceof Switch) {
                ((Switch) device).setNumberOfPorts(dataInput.readInt());
            }
            if (device instanceof WifiRouter) {
                ((WifiRouter) device).setSecurityProtocol(readValue(dataInput.readUTF()));
            }
            ((Router) device).setDataRate(dataInput.readInt());
        }
    }

    public String readValue(String value) {
        if (LINE_MARKER.equals(value)) {
            return null;
        }
        return value;
    }

    public void writeDevice(Device device, Writer writer) throws IOException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, ERROR_MESSAGE, notImplementedException);
        throw notImplementedException;
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, ERROR_MESSAGE, notImplementedException);
        throw notImplementedException;
    }

}
