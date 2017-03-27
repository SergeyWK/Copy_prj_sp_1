package com.netcracker.edu.inventory.exception;


import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;

import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceValidationException extends RuntimeException {
    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

    private static final String MESSAGE = "Test message";
            private Device object;

    public DeviceValidationException(Throwable cause, Device object) {
        super(cause);
        this.object = object;
    }

    public DeviceValidationException(String operation, Device object) {

    }

    public Device getObject() {
        return object;
    }

    public DeviceValidationException() {
        super(MESSAGE);
    }
}
