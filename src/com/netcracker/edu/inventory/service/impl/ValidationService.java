package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.WifiRouter;

class ValidationService {

    static final String LINE_MARKER = "\n";
    static final String STRING_TOKEN = "|";


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
            return isValidDevice(device, LINE_MARKER, STRING_TOKEN);
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
            boolean validObject = (isValidField(deviceModel, marker, marker2))
                    && (isValidField(deviceManufacturer, marker, marker2))
                    && (isValidField(deviceType, marker, marker2)
                    && (isValidField(routerSecurityProtocol, marker, marker2)));
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

}
