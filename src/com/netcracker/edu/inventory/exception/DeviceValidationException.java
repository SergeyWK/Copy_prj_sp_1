package com.netcracker.edu.inventory.exception;


import com.netcracker.edu.inventory.model.Device;


public class DeviceValidationException extends RuntimeException {

    private static final String MESSAGE = "Device is not valid for operation.";
    private Device object;


    public DeviceValidationException() {
        super(MESSAGE);
    }

    public DeviceValidationException(String operation) {
        super(MESSAGE + " " + (operation != null ? operation : " "));
    }

    public DeviceValidationException(String operation, Device object) {
        this(operation);
        this.object = object;
    }

    public Device getObject() {
        return object;
    }
}
