package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;

public class RackArrayImpl implements Rack {

    protected Device[] arrayImpl;

    public RackArrayImpl(int size) {
        this.arrayImpl = new Device[size];
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
        if (index >= 0 && index < arrayImpl.length) {
            if (arrayImpl[index] != null) {
                return arrayImpl[index];
            } else {
                if (arrayImpl[index] == null) {
                    System.err.println("Device not found, slot is empty " + ": Slot = " + index);
                }
                return null;
            }
        } else {
            if (index < 0 || index > arrayImpl.length) {
                System.err.println("Device not found, slot does not exist" + ": Slot = " + index);
            }
            return null;
        }
    }

    public boolean insertDevToSlot(Device device, int index) {
        if (index >= 0 && index < arrayImpl.length) {
            if (arrayImpl[index] == null && device != null && device.getIn() > 0) {
                arrayImpl[index] = device;
                return true;
            } else if (device == null) {
                System.err.println("Device cant't be: " + device);
            } else if (device.getIn() <= 0) {
                System.err.println("Can't insert, incorrect IN (<=0) " + ": Slot = " + index);
            } else if (arrayImpl[index] != null) {
                System.err.println("Can't insert, slot is full " + ": Slot = " + index);
            }
            return false;
        } else {
            System.err.println("Can't insert, slot does not exist " + ": Slot = " + index);
            return false;
        }
    }

    public Device removeDevFromSlot(int index) {
        if (index >= 0 && index < arrayImpl.length) {
            Device removedDevice = arrayImpl[index];
            arrayImpl[index] = null;
            return removedDevice;
        }
        return null;
    }

    public Device getDevByIN(int in) {
        for (int i = 0; i < arrayImpl.length; i++) {
            if (arrayImpl[i] != null && in == arrayImpl[i].getIn()) {
                return arrayImpl[i];
            }
        }
        System.err.println("Device with identification number " + in + " is absent in rack");
        return null;
    }
}
