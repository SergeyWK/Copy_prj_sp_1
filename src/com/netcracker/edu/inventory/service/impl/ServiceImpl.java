package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.service.ConnectionService;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;


public class ServiceImpl implements Service {

    @Deprecated
    public void sortByIN(Device[] devices) {
        new DeviceServiceImpl().sortByIN(devices);
    }

    @Deprecated
    public void sortByProductionDate(Device[] devices) {
        new DeviceServiceImpl().sortByProductionDate(devices);
    }

    @Deprecated
    public void filtrateByType(Device[] devices, String type) {
        new DeviceServiceImpl().filtrateByType(devices, type);
    }

    @Deprecated
    public void filtrateByManufacturer(Device[] devices, String manufacturer) {
        new DeviceServiceImpl().filtrateByManufacturer(devices, manufacturer);
    }

    @Deprecated
    public void filtrateByModel(Device[] devices, String model) {
        new DeviceServiceImpl().filtrateByModel(devices, model);
    }

    @Deprecated
    public boolean isValidDeviceForInsertToRack(Device device) {
        return new DeviceServiceImpl().isValidDeviceForInsertToRack(device);
    }


    public DeviceService getDeviceService() {
        return new DeviceServiceImpl();
    }


    public RackService getRackService() {
        return new RackServiceImpl();
    }

    @Override
    public ConnectionService getConnectionService() {
        return new ConnectionServiceImpl() {
        };
    }

}





