package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FillableEntity;
import com.netcracker.edu.inventory.model.Rack;



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
            FillableEntity.Field[] fields = device.getAllFieldsToArray();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType() != null) {
                    if (Integer.class.isAssignableFrom(fields[i].getType())) {
                        dataOutputStream.writeInt((Integer) fields[i].getValue());
                    } else if (String.class.isAssignableFrom(fields[i].getType())) {
                        dataOutputStream.writeUTF(fields[i].getValue() == null ? LINE_MARKER : ((String) fields[i].getValue()));
                    } else if (Date.class.isAssignableFrom(fields[i].getType())) {
                        dataOutputStream.writeLong(fields[i].getValue() == null ? -1 : ((Date) fields[i].getValue()).getTime());
                    }
                }
            }
            dataOutputStream.flush();
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
            FillableEntity.Field fields[] = device.getAllFieldsToArray();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType() != null) {
                    if (Integer.class.isAssignableFrom(fields[i].getType())) {
                        if (i == 0) {
                            devString.append(fields[i].getValue()).append(SPACE_SEPARATOR + STRING_TOKEN);
                        } else {
                            devString.append(SPACE_SEPARATOR).append(fields[i].getValue()).append(SPACE_SEPARATOR + STRING_TOKEN);
                        }
                    } else if (String.class.isAssignableFrom(fields[i].getType())) {
                        devString.append(appendDeviceFields((String) fields[i].getValue()));
                    } else if (Date.class.isAssignableFrom(fields[i].getType())) {
                        devString.append(String.valueOf(fields[i].getValue() == null ? SPACE_SEPARATOR + (-1) : SPACE_SEPARATOR +
                                ((Date) fields[i].getValue()).getTime()) + SPACE_SEPARATOR + STRING_TOKEN);
                    }
                }
            }
            devString.append(LINE_MARKER);
            bufferedWriter.write(devString.toString());
            System.out.println(devString);
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
