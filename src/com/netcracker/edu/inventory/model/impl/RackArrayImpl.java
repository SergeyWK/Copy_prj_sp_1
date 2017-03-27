package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RackArrayImpl implements Rack {
    static protected Logger LOGGER = Logger.getLogger(RackArrayImpl.class.getName());

    protected Device[] arrayImpl;

    public RackArrayImpl(int size) {
        try {
            if (size >= 0) {
                this.arrayImpl = new Device[size];
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.INFO, "Rack size should not be negative :" + " " + e);
        }
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
   /*     if (index >= 0 && index < arrayImpl.length) {
            if (arrayImpl[index] != null) {
                return arrayImpl[index];
            } else {
                if (arrayImpl[index] == null) {
                    LOGGER.log(Level.INFO, "Device not found, slot is empty 11");
                    System.err.println("Device not found, slot is empty " + ": Slot = " + index);
                }
                return null;
            }
        } else {
            if (index < 0 || index > arrayImpl.length) {
                LOGGER.log(Level.INFO, "Device not found, ssssssssssss");
                System.err.println("Device not found, slot does not exist" + ": Slot = " + index);
            }
            return null;
        }*/
        if (index < 0 || index >= arrayImpl.length) {
            LOGGER.log(Level.INFO, "Incorrect index, corect range: 0.." + (arrayImpl.length-1));
            throw new IndexOutOfBoundsException();
        } else if(arrayImpl[index] != null){
            return arrayImpl[index];
        }
        return null;
    }

    public boolean insertDevToSlot(Device device, int index) {
         /*      if (index >= 0 && index < arrayImpl.length) {
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
        }*/


      if (!(index >= 0 && index < arrayImpl.length)) {
          LOGGER.log(Level.INFO, "Incorrect index, corect range: 0.." + (arrayImpl.length-1));
          throw new IndexOutOfBoundsException();
      } else if(!(device != null && device.getIn() > 0)){
          LOGGER.log(Level.INFO, "DeviceValidationException");
          throw new DeviceValidationException();
      }
      if(arrayImpl[index] == null){
          arrayImpl[index] = device;
          return true;
      }
   return false;
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
