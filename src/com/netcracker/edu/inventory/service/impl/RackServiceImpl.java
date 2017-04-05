package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

class RackServiceImpl implements RackService {

    static protected Logger LOGGER = Logger.getLogger(RackServiceImpl.class.getName());


    public static final String LINE_MARKER = "\n";

    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        if(rack != null) {
            if(outputStream == null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
                //todo log
                throw illegalArgumentException;
            }

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            writeRack(rack, dataOutputStream);;
            dataOutputStream.flush();
        }

    }

    private void writeRack(Rack rack, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(rack.getSize());
        dataOutputStream.writeUTF(rack.getTypeOfDevices().getName());
        dataOutputStream.writeInt(rack.getAllDeviceAsArray().length);
/*        if(rack.getAllDeviceAsArray().length > 0){
            writeDevice(rack.getAllDeviceAsArray(), dataOutputStream);
        }   */
        if(rack.getSize() > 0){
            for (int i = 0; i <= rack.getSize()-1; i++) {
                if (rack.getDevAtSlot(i) != null) {
                    dataOutputStream.writeInt(i);
                    writeDeviceRack(rack.getDevAtSlot(i), dataOutputStream);
                    writeSpecificDevice(rack.getDevAtSlot(i), dataOutputStream);
                }
            }
        }


    }

/*    private void writeDevice(Device[] devices, DataOutputStream dataOutputStream) throws IOException {
        for (int i = 0; i < devices.length; i++) {
            if (devices[i] != null) {
                writeDeviceRack(devices[i], dataOutputStream);
                writeSpecificDevice(devices[i], dataOutputStream);
            }
        }
    }*/

    private void writeDeviceRack(Device device, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(device.getClass().getName());
        dataOutputStream.writeInt(device.getIn());
        dataOutputStream.writeUTF(validObjectDevice(device.getType()));
        dataOutputStream.writeUTF(validObjectDevice(device.getModel()));
        dataOutputStream.writeUTF(validObjectDevice(device.getManufacturer()));
        dataOutputStream.writeLong(device.getProductionDate() == null ? -1 : device.getProductionDate().getTime());
    }

    private void writeSpecificDevice(Device device, DataOutputStream dataOutputStream) throws IOException {
        if(device instanceof Battery){
            dataOutputStream.writeInt(((Battery) device).getChargeVolume());
        } else if(device instanceof Router && (Router.class.getName().equals(device.getClass().getName()))){
            dataOutputStream.writeInt(((Router) device).getDataRate());
        } else if(device instanceof Switch){
            dataOutputStream.writeInt(((Switch) device).getDataRate());
            dataOutputStream.writeInt(((Switch) device).getNumberOfPorts());
        } else if(device instanceof WifiRouter && (WifiRouter.class.getName().equals(device.getClass().getName()))){
            dataOutputStream.writeInt(((WifiRouter) device).getDataRate());
            dataOutputStream.writeUTF(validObjectDevice(((WifiRouter) device).getSecurityProtocol()));
        }
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


    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        if(inputStream == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
            //todo log
            throw illegalArgumentException;
        }

        DataInputStream dataInput = new DataInputStream(inputStream);
        int rackSize = dataInput.readInt();
        String deviceClass = dataInput.readUTF();
        int amountDevice = dataInput.readInt();
        Rack rack = deviceInitialization(rackSize, deviceClass);
        if(rackSize > 0){
            String deviceClassName;
            Device device;
            int devIndex;
            for(int i =0; i< amountDevice; i++){;
                    devIndex = dataInput.readInt();
                    deviceClassName = dataInput.readUTF();
                    device = deviceInitialization(deviceClassName);
                    if(device != null) {
                        readDevice(device, dataInput);
                        readSpecific(device, dataInput);
                        rack.insertDevToSlot(device, devIndex);
                    }
            }
        }
        return rack;
    }

    private void readDevice(Device device, DataInputStream dataInput) throws IOException {
        int deviceIn = dataInput.readInt();
        String deviceType = readValue(dataInput.readUTF());
        String deviceModel = readValue(dataInput.readUTF());
        String deviceManufacturer = readValue(dataInput.readUTF());
        long  deviceProductionDate = dataInput.readLong();
        Date date = deviceProductionDate==-1 ? null : new Date(deviceProductionDate);

        device.setIn(deviceIn);
        device.setType(deviceType);
        device.setModel(deviceModel);
        device.setManufacturer(deviceManufacturer);
        device.setProductionDate(date);
    }
    private void readSpecific(Device device, DataInputStream dataInput) throws IOException {
        if(device instanceof Battery){
            ((Battery) device).setChargeVolume(dataInput.readInt());
        } else if(device instanceof Router && (Router.class.getName().equals(device.getClass().getName()))){
            ((Router) device).setDataRate(dataInput.readInt());
        } else if(device instanceof Switch){
            ((Switch) device).setDataRate(dataInput.readInt());
            ((Switch) device).setNumberOfPorts(dataInput.readInt());
        } else if(device instanceof WifiRouter && (WifiRouter.class.getName().equals(device.getClass().getName()))){
            ((WifiRouter) device).setDataRate(dataInput.readInt());
            ((WifiRouter) device).setSecurityProtocol(readValue(dataInput.readUTF()));
        }
    }

    private String readValue(String value){
        if(LINE_MARKER.equals(value)){
            return null;
        }
        return value;
    }


    public void writeRack(Rack rack, Writer writer) throws IOException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, notImplementedException + ", An unfinished execution path");
        throw notImplementedException;
    }


    public Rack readRack(Reader reader) throws IOException, ClassNotFoundException {
        NotImplementedException notImplementedException = new NotImplementedException();
        LOGGER.log(Level.SEVERE, notImplementedException + ", An unfinished execution path");
        throw notImplementedException;
    }

    private Rack deviceInitialization(int size, String deviceClass){
        if(Battery.class.getName().equals(deviceClass)){
            return new RackArrayImpl(size, Battery.class);
        } else if(Router.class.getName().equals(deviceClass)){
            return new RackArrayImpl(size, Router.class);
        } else if(Switch.class.getName().equals(deviceClass)){
            return new RackArrayImpl(size, Switch.class);
        } else if(WifiRouter.class.getName().equals(deviceClass)){
            return new RackArrayImpl(size, WifiRouter.class);
        } else if(Device.class.getName().equals(deviceClass)){
            return new RackArrayImpl(size, Device.class);
        }
        return null;
    }

    private Device deviceInitialization(String deviceClass){
        if(Battery.class.getName().equals(deviceClass)){
            return new Battery();
        } else if(Router.class.getName().equals(deviceClass)){
            return new Router();
        } else if(Switch.class.getName().equals(deviceClass)){
            return new Switch();
        } else if(WifiRouter.class.getName().equals(deviceClass)){
            return new WifiRouter();
        }
        return null;
    }
}
