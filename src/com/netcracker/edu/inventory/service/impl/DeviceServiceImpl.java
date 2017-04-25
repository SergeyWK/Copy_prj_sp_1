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
    static final String STR_MARKER = "|";
    static final String STR_MARKER_2 = " ";

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
            return isValidDevice(device, LINE_MARKER, LINE_MARKER);
        }
        return false;
    }

    public boolean isValidDeviceForWriteToStream(Device device) {
        if (device != null) {
            return isValidDevice(device, LINE_MARKER, STR_MARKER);
        }
        return false;
    }

    private boolean isValidDevice(Device device, String marker, String marker2) {
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
            boolean validObject = (isValidField(deviceModel, marker, marker2)) && (isValidField(deviceManufacturer, marker, marker2))
                    && (isValidField(deviceType, marker, marker2) && (isValidField(routerSecurityProtocol, marker, marker2)));
            return validObject;
        }
        return false;
    }


    private boolean isValidField(String field, String marker1, String marker2) {
        if (field == null) {
            return true;
        }
        return !(field.contains(marker1) || field.contains(marker2));
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
            devString.append(device.getIn()).append(STR_MARKER_2 + STR_MARKER);
            devString.append(appendDeviceFields(device.getType()));
            devString.append(appendDeviceFields(device.getModel()));
            devString.append(appendDeviceFields(device.getManufacturer()));
            devString.append(String.valueOf(device.getProductionDate() == null ? STR_MARKER_2 + (-1) : STR_MARKER_2 +
                    device.getProductionDate().getTime()) + STR_MARKER_2 + STR_MARKER);
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
            System.out.println(devString.toString());
            devString.append(LINE_MARKER);
            bufferedWriter.write(devString.toString());
            bufferedWriter.flush();
        }
    }

    public String appendDeviceFields(String type) {
        if (type == null) {
            return STR_MARKER_2 + STR_MARKER;
        } else {
            return STR_MARKER_2 + type + STR_MARKER_2 + STR_MARKER;
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
                readFieldsOfDevice(device, deviceFields);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, deviceClassName, e);
            throw e;
        }
        return device;
    }

    public void readFieldsOfDevice(Device device, String deviceFields) throws IOException {
        StringTokenizer deviceField = new StringTokenizer(deviceFields, " |");
        device.setIn(Integer.parseInt(deviceField.nextToken()));
        deviceField.nextToken(STR_MARKER);
        String devType = deviceField.nextToken(STR_MARKER);
        device.setType(devType.equals(" ") ? null : devType.substring(1, devType.length() - 1));
        String devModel = deviceField.nextToken(STR_MARKER);
        device.setModel(devModel.equals(" ") ? null : devModel.substring(1, devModel.length() - 1));
        String devManufacturer = deviceField.nextToken(STR_MARKER);
        device.setManufacturer(devManufacturer.equals(" ") ? null : devManufacturer.substring(1, devManufacturer.length() - 1));
        String devDate = deviceField.nextToken(STR_MARKER);
        if (!devDate.equals(" ")) {
            Long date = Long.parseLong(devDate.trim());
            if (date != -1) {
                device.setProductionDate(new Date(date));
            } else {
                device.setProductionDate(null);
            }
        }
        if (Battery.class.isAssignableFrom(device.getClass())) {
            String devChargeVolume = deviceField.nextToken(STR_MARKER);
            int value = Integer.parseInt(devChargeVolume.trim());
            ((Battery) device).setChargeVolume(value);
        }
        if (Router.class.isAssignableFrom(device.getClass())) {
            String devDataRate = deviceField.nextToken(STR_MARKER);
            int value = Integer.parseInt(devDataRate.trim());
            ((Router) device).setDataRate(value);
        }
        if (Switch.class.isAssignableFrom(device.getClass())) {
            String devNumOfPorts = deviceField.nextToken(STR_MARKER);
            int value = Integer.parseInt(devNumOfPorts.trim());
            ((Switch) device).setNumberOfPorts(value);
        }
        if (WifiRouter.class.isAssignableFrom(device.getClass())) {
            String devSecProtocol = deviceField.nextToken(STR_MARKER);
            //   WifiRouter wifiDevice = (WifiRouter) device;
            if (!devSecProtocol.equals(" ")) {
                // String value = devSecProtocol.trim();
                ((WifiRouter) device).setSecurityProtocol(devSecProtocol.replaceAll("[\\s]{3}"," "));
                System.out.println(devSecProtocol);
                System.out.println(devSecProtocol.toCharArray());
                System.out.println("WTF");
            } else {
                ((WifiRouter) device).setSecurityProtocol(null);
            }
        }


//        if (device instanceof Battery) {
//            ((Battery) device).setChargeVolume(dataInput.readInt());
//        }
//        if (device instanceof Router) {
//            if (device instanceof Switch) {
//                ((Switch) device).setNumberOfPorts(dataInput.readInt());
//            }
//            if (device instanceof WifiRouter) {
//                ((WifiRouter) device).setSecurityProtocol(readValue(dataInput.readUTF()));
//            }
//            ((Router) device).setDataRate(dataInput.readInt());
//        }
    }

    private String readStringFromBuffer(Reader reader) throws IOException {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            c = ((char) reader.read());
            if (c == LINE_MARKER.charAt(0) || c == 0xFFFF) break;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

}








