package com.netcracker.edu.inventory.exception;


import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;
import java.util.logging.Logger;

public class DeviceValidationException extends RuntimeException {
    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

    private static final String MESSAGE = "Device is not valid for operation";
    private Device object;

   /* public DeviceValidationException(Throwable cause, Device object) {
        super(cause);
        this.object = object;
    }*/


    public DeviceValidationException() {
        super(MESSAGE);
    }

    public DeviceValidationException(String message) {
        super(message);
    }

    public DeviceValidationException(String operation, Device object) {
        super((operation != null ? operation : "") + ". " + MESSAGE);
        this.object = object;
    }

    public Device getObject() {
        return object;
    }


    }
