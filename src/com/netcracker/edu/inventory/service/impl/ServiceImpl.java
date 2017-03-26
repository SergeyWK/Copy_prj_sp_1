package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.AbstractDevice;
import com.netcracker.edu.inventory.service.Service;

import java.util.Date;

public class ServiceImpl implements Service {

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
}