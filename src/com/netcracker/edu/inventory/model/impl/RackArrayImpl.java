package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RackArrayImpl implements Rack {

    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

    protected Device[] arrayImpl;
    private final Class clazz;


        public RackArrayImpl(int size, Class clazz){
            if(clazz==null){
//todo exseption
            }
            if (size >= 0) {
                this.arrayImpl = new Device[size];
            } else {
                IllegalArgumentException illegalArgumentException =
                        new IllegalArgumentException("Rack size should not be negative");
                LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage()
                        + ", сurrent size is: " + size, illegalArgumentException);
                throw illegalArgumentException;
            }
            this.clazz=clazz;
    }


    public RackArrayImpl(int size) {
        this(size, Device.class);
    }

    public int getSize() {
        if (arrayImpl == null) {
            return 0;
        }
        return arrayImpl.length;
    }

    public int getFreeSize() {
        int freeSize = 0;
        for (int i = 0; i < arrayImpl.length; i++) {
            if (arrayImpl[i] == null) {
                freeSize++;
            }
        }
        return freeSize;
    }

    public Device getDevAtSlot(int index) {
        if (checkIndex(index) && arrayImpl[index] != null) {
            return arrayImpl[index];
        }
        return null;
    }

    public boolean insertDevToSlot(Device device, int index) {
        if(!clazz.isInstance(device)){
//          todo exseption
        }
        if (checkIndex(index) && (!(device != null && device.getIn() > 0))) {
            DeviceValidationException deviceValidationException =
                    new DeviceValidationException("Rack.insertDevToSlot", device);
            LOGGER.log(Level.SEVERE, deviceValidationException.getMessage() +
                    " device: " + deviceValidationException.getObject(), deviceValidationException);
            throw deviceValidationException;
        }
        if (arrayImpl[index] == null) {
            arrayImpl[index] = device;
            return true;
        }
        return false;
    }

    public Device removeDevFromSlot(int index) {
        if (checkIndex(index) && arrayImpl[index] != null) {
            Device removedDevice = arrayImpl[index];
            arrayImpl[index] = null;
            return removedDevice;
        }
        LOGGER.log(Level.WARNING, "Can not remove from empty slot: " + index);
        return null;

    }

    public Device getDevByIN(int in) {
        for (int i = 0; i < arrayImpl.length; i++) {
            if (arrayImpl[i] != null && in == arrayImpl[i].getIn()) {
                return arrayImpl[i];
            }
        }
        LOGGER.log(Level.WARNING, "Device with identification number " + in + " is absent in rack");
        return null;
    }

    private boolean checkIndex(int index) {
        if (!(index >= 0 && index < arrayImpl.length)) {
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException("Incorrect index  = " +
                    index + ", correct range: 0.." + (arrayImpl.length > 0 ? arrayImpl.length - 1 : 0));
            LOGGER.log(Level.SEVERE, indexOutOfBoundsException.getMessage(), indexOutOfBoundsException);
            throw indexOutOfBoundsException;
        }
        return true;
    }

    @Override
    public Class getTypeOfDevices() {
        return null;
    }

    @Override
    public Device[] getAllDeviceAsArray() {
        return new Device[0];
    }
}
