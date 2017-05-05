package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractDevice implements Device, Serializable {
    static protected Logger LOGGER = Logger.getLogger(AbstractDevice.class.getName());

    protected int in;
    protected String type;
    protected String manufacturer;
    protected String model;
    protected Date productionDate;

    public int getIn() {
        return in;
    }


   /*  Version for PZ3
   public void setIn(int in) {
        if (in > 0 && this.in == 0) {
            this.in = in;
        } else {
            System.err.println("Incorrect value:" + in + " Value must be > 0");
        }
    }*/

    public void setIn(int in) {
        if (in < 0) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("IN can not be negative");
            LOGGER.log(Level.SEVERE, illegalArgumentException.getMessage() + ", in = " + in, illegalArgumentException);
            throw illegalArgumentException;
        }
        if (this.in == 0) {
            this.in = in;
        } else {
            LOGGER.log(Level.WARNING, "Inventory number can not be reset, " + "number is alredy = " + this.in);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public void fillAllFields(Field[] fields) {
    }

    public Field[] getAllFieldsToArray() {
        return new Field[0];
    }
}
