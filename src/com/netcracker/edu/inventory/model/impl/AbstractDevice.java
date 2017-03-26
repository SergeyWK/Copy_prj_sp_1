package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

import java.util.Date;
import java.util.logging.Level;



public abstract class AbstractDevice implements Device {


    protected int in;
    protected String type;
    protected String manufacturer;
    protected String model;
    protected Date productionDate;

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
       try { in = 0;
       in > 0;  this.in == 0;
           this.in = in;
       } catch (IllegalArgumentException e){
           System.out.println("yuehe"+e);
            LOGGER.log(Level.INFO, "IN can not be negative"+e);
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

}
