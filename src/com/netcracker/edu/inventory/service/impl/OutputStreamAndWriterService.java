package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FillableEntity;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;

import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputStreamAndWriterService {

    static protected Logger LOGGER = Logger.getLogger(OutputStreamAndWriterService.class.getName());

    static final String LINE_MARKER = "\n";
    static final String STRING_TOKEN = "|";
    static final String SPACE_SEPARATOR = " ";


    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (device != null) {
            if (!new ValidationService().isValidDeviceForOutputToStream(device)) {
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
            FillableEntity.Field fields[] = device.getAllFieldsToArray();
            for (FillableEntity.Field field : fields) {
                if (field.getType() != null) {
                    if (Integer.class.isAssignableFrom(field.getType())) {
                        dataOutputStream.writeInt((Integer) field.getValue());
                    } else if (String.class.isAssignableFrom(field.getType())) {
                        dataOutputStream.writeUTF(validObjectDevice((String) field.getValue()));
                    } else if (Date.class.isAssignableFrom(field.getType())) {
                        dataOutputStream.writeLong(field.getValue() == null ? -1 : ((Date) field.getValue()).getTime());
                    }
                }
            }
        /*    dataOutputStream.writeUTF(device.getClass().getName());
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
            }*/
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

    public void writeDevice(Device device, Writer writer) throws IOException {
        if (device != null) {
            if (!new ValidationService().isValidDeviceForWriteToStream(device)) {
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

    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if (rack != null) {
            if (outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Output stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + outputStream, illegalArgumentException);
                throw illegalArgumentException;
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            new com.netcracker.edu.location.impl.ServiceImpl().outputLocation(rack.getLocation(), outputStream);
            dataOutputStream.writeInt(rack.getSize());
            dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());
            for (int i = 0; i < rack.getSize(); i++) {
                if (rack.getDevAtSlot(i) != null) {
                    dataOutputStream.writeUTF("");
                    outputDevice(rack.getDevAtSlot(i), dataOutputStream);
                } else {
                    dataOutputStream.writeUTF(LINE_MARKER);
                }
            }
            dataOutputStream.flush();
        }
    }

    public void writeRack(Rack rack, Writer writer) throws IOException {
        if (rack != null) {
            if (writer == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Writer stream can't be: ");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + writer, illegalArgumentException);
                throw illegalArgumentException;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            new com.netcracker.edu.location.impl.ServiceImpl().writeLocation(rack.getLocation(), writer);
            StringBuffer rackString = new StringBuffer(rack.getSize() + " ");
            rackString.append(rack.getTypeOfDevices().getName()).append(LINE_MARKER);
            bufferedWriter.write(rackString.toString());
            bufferedWriter.flush();
            for (int i = 0; i < rack.getSize(); i++) {
                if (rack.getDevAtSlot(i) != null) {
                    writeDevice(rack.getDevAtSlot(i), writer);
                } else {
                    bufferedWriter.write(LINE_MARKER);
                    bufferedWriter.flush();
                }
            }
        }
    }
}
