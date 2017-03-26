package com.netcracker.edu.inventory.exception;


import com.netcracker.edu.inventory.model.Device;

public class DeviceValidationException extends RuntimeException {
        Device object;

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
