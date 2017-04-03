package com.netcracker.edu.inventory.service.impl;


import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;


import java.io.*;
import java.util.Date;

class DeviceServiceImpl implements DeviceService {

    public static final String LINE_MARKER = "\n";

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
        if(device != null){
            String deviceModel = device.getModel();
            String deviceManufacturer = device.getManufacturer();
            String deviceType = device.getType();
            //todo хуйня
            String deviceSecurityProtocol = ((WifiRouter) device).getSecurityProtocol();
            //todo хуйня
            if((validStringIsNullOrEmpty(deviceModel) && !deviceModel.contains("\n"))
                    || (validStringIsNullOrEmpty(deviceManufacturer) && !deviceManufacturer.contains("\n"))
                    || (validStringIsNullOrEmpty(deviceType) && !deviceType.contains("\n"))
                    || (validStringIsNullOrEmpty(deviceSecurityProtocol) && !deviceSecurityProtocol.contains("\n"))){
                return  true;
            }
            /*if((validStringIsNullOrEmpty(deviceModel) || validStringIsNullOrEmpty(deviceManufacturer)
                    || validStringIsNullOrEmpty(deviceType) || validStringIsNullOrEmpty(deviceSecurityProtocol)) && !(deviceModel.contains("\n")
                    || deviceManufacturer.contains("\n")
                    || deviceType.contains("\n")
                    || deviceSecurityProtocol.contains("\n"))){
                return  true;
            }*/
            return  false;
        }
        return false;
    }


    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if(device != null) {
            if(outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
                //todo log
                throw illegalArgumentException;
            }
            outputStream  = new FileOutputStream("out.bin");
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(device.getClass().getName());
            dataOutputStream.writeInt(device.getIn());
            dataOutputStream.writeUTF(validObjectDevice(device.getType()));
            dataOutputStream.writeUTF(validObjectDevice(device.getModel()));
            dataOutputStream.writeUTF(validObjectDevice(device.getManufacturer()));
            dataOutputStream.writeLong(device.getProductionDate() == null ? -1 : device.getProductionDate().getTime());
            dataOutputStream.flush();
        }
    }


    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        if(inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
            //todo log
            throw illegalArgumentException;
        }
        inputStream = new FileInputStream("out.bin");
        byte[] bytes = new byte[100];
        inputStream.read(bytes);
        for (byte b : bytes) {
            System.out.print(" " + b);
        }
        inputStream.close();
        return null;
    }


    public void writeDevice(Device device, Writer writer) throws IOException {

    }


    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return null;
    }

    private boolean validStringIsNullOrEmpty (String string) {
        if (string != null && string.length() > 0) {
           return  true;
        }
        return false;
    }

    private String validObjectDevice (String string) {
        if(string == null) {
            return LINE_MARKER;
        } else if(string.contains("\n")){
            DeviceValidationException deviceValidationException = new DeviceValidationException();
            //todo log
            throw deviceValidationException;
        } else {
            return string;
        }
    }
}
