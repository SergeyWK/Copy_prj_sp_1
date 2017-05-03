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
      return new InputStreamAndReaderService().inputDevice(inputStream);
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
        return new InputStreamAndReaderService().readDevice(reader);
    }
}








