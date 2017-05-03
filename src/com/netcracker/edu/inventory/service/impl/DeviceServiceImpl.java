package com.netcracker.edu.inventory.service.impl;


import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.*;

class DeviceServiceImpl implements DeviceService {

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
       new OutputStreamAndWriterService().outputDevice(device, outputStream);
    }

    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
      return new InputStreamAndReaderService().inputDevice(inputStream);
    }

    public void writeDevice(Device device, Writer writer) throws IOException {
       new OutputStreamAndWriterService().writeDevice(device, writer);
    }

    public Device readDevice(Reader reader) throws IOException, ClassNotFoundException {
        return new InputStreamAndReaderService().readDevice(reader);
    }
}







