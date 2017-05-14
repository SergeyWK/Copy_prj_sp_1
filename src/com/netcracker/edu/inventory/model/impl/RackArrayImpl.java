package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.service.impl.ServiceImpl;
import com.netcracker.edu.location.Location;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RackArrayImpl<T extends Device> implements Rack<T>, Serializable {

    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

    protected  T[] arrayImpl;
    private final Class<T> clazz;
    protected Location location;

    public RackArrayImpl(int size, Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type cannot be: " + clazz);
        }
        if (size >= 0) {
            this.arrayImpl = (T[]) new Device[size];
        } else {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Rack size should not be negative");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage()
                    + ", сurrent size is: " + size, illegalArgumentException);
            throw illegalArgumentException;
        }
        this.clazz = clazz;
    }


    public RackArrayImpl(int size) {
        this(size, (Class<T>) Device.class);
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

    public T getDevAtSlot(int index) {
        if (checkIndex(index) && arrayImpl[index] != null) {
            return arrayImpl[index];
        }
        return null;
    }

    public boolean insertDevToSlot(T device, int index) {
        if (checkIndex(index) && (!new ServiceImpl().isValidDeviceForInsertToRack(device))) {
            DeviceValidationException deviceValidationException =
                    new DeviceValidationException("Rack.insertDevToSlot", device);
            LOGGER.log(Level.SEVERE, deviceValidationException.getMessage() +
                    " device: " + deviceValidationException.getObject(), deviceValidationException);
            throw deviceValidationException;
        }
        if (!clazz.isInstance(device)) {
            throw new IllegalArgumentException(
                    "The type of the transmitted object is not compatible with the type, that the rack can store.");
        }
        if (arrayImpl[index] == null) {
            arrayImpl[index] = device;
            return true;
        }
        return false;
    }

    public T removeDevFromSlot(int index) {
        if (checkIndex(index) && arrayImpl[index] != null) {
            T removedDevice = arrayImpl[index];
            arrayImpl[index] = null;
            return removedDevice;
        }
        LOGGER.log(Level.WARNING, "Can not remove from empty slot: " + index);
        return null;

    }

    public T getDevByIN(int in) {
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

    public Class getTypeOfDevices() {
        return clazz;
    }

  /*  public Device[] getAllDeviceAsArray() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < arrayImpl.length; i++) {
            if (arrayImpl[i] != null) {
                list.add(arrayImpl[i]);
            }
        }
        return (Device[]) list.toArray(new Device[list.size()]);
    }*/

    public T[] getAllDeviceAsArray() {
        int device = 0;
        T[] devices = (T[]) new Device[arrayImpl.length - getFreeSize()];
        for (int i = 0; i < arrayImpl.length; i++) {
            if (arrayImpl[i] != null) {
                devices[device] = arrayImpl[i];
                device++;
                    }
        }
        return devices;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
