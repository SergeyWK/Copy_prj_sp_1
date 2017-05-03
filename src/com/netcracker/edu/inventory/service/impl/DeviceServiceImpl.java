package com.netcracker.edu.inventory.service.impl;


import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

class DeviceServiceImpl implements DeviceService {

    static protected Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

    static final String LINE_MARKER = "\n";
    static final String STRING_TOKEN = "|";
    static final String SPACE_SEPARATOR = " ";

    public void sortByIN(Device[] devices) {
        new SortAndFiltrateService().sortByIN(devices);
    }

    public void sortByProductionDate(Device[] devices) {
        new SortAndFiltrateService().sortByProductionDate(devices);
    }

    public void filtrateByType(Device[] devices, String type) {
        new SortAndFiltrateService().filtrateByType(devices, type);
    }

    public void filtrateByManufacturer(Device[] devices, String manufacturer) {
        new SortAndFiltrateService().filtrateByManufacturer(devices, manufacturer);
    }

    public void filtrateByModel(Device[] devices, String model) {
        new SortAndFiltrateService().filtrateByModel(devices, model);
    }

    public boolean isValidDeviceForInsertToRack(Device device) {
        return new ValidationService().isValidDeviceForInsertToRack(device);
    }

    public boolean isValidDeviceForOutputToStream(Device device) {
       return  new ValidationService().isValidDeviceForOutputToStream(device);
    }

    public boolean isValidDeviceForWriteToStream(Device device) {
       return new ValidationService().isValidDeviceForWriteToStream(device);
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
        if (device != null) {
            if (!isValidDeviceForWriteToStream(device)) {
                DeviceValidationException deviceValidationException =
                        new DeviceValidationException("DeviceService.writeDevice.");
                LOGGER.log(Level.SEVERE, deviceValidationException.getMessage() + device, deviceValidationException);
                throw deviceValidationException;
            }
            if (writer == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Writer stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + writer, illegalArgumentException);
                throw illegalArgumentException;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            StringBuffer devString = new StringBuffer(device.getClass().getName() + LINE_MARKER);
            devString.append(device.getIn()).append(SPACE_SEPARATOR + STRING_TOKEN);
            devString.append(appendDeviceFields(device.getType()));
            devString.append(appendDeviceFields(device.getModel()));
            devString.append(appendDeviceFields(device.getManufacturer()));
            devString.append(String.valueOf(device.getProductionDate() == null ? SPACE_SEPARATOR + (-1) : SPACE_SEPARATOR +
                    device.getProductionDate().getTime()) + SPACE_SEPARATOR + STRING_TOKEN);
            if (Battery.class.isAssignableFrom(device.getClass())) {
                devString.append(appendDeviceFields(String.valueOf((((Battery) device)).getChargeVolume())));
            }
            if (Router.class.isAssignableFrom((device.getClass()))) {
                devString.append(appendDeviceFields(String.valueOf(((Router) device).getDataRate())));
            }
            if (Switch.class.isAssignableFrom(device.getClass())) {
                devString.append(appendDeviceFields(String.valueOf((((Switch) device)).getNumberOfPorts())));
            }
            if (WifiRouter.class.isAssignableFrom(device.getClass())) {
                WifiRouter wifiDevice = (WifiRouter) device;
                devString.append(appendDeviceFields(((wifiDevice.getSecurityProtocol()))));
            }
            devString.append(LINE_MARKER);
            bufferedWriter.write(devString.toString());
            bufferedWriter.flush();
        }
    }

    public String appendDeviceFields(String type) {
        if (type == null) {
            return SPACE_SEPARATOR + STRING_TOKEN;
        } else {
            return SPACE_SEPARATOR + type + SPACE_SEPARATOR + STRING_TOKEN;
        }
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

}








