package com.netcracker.edu.inventory.exception;


import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceValidationException extends RuntimeException {
    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

            private Device object;

    public Device getObject() {
        return object;
    }

    public DeviceValidationException() {
    }

    public DeviceValidationException(String message) {
        super(message);
    }

    public DeviceValidationException(String operation, Device object) {
    }
}
