package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FillableEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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

    public List<Field> getAllFields() {
        List<Field> fieldList = new ArrayList<Field>();
        fieldList.add(new Field(Integer.class, this.getIn()));
        fieldList.add(new Field(String.class, this.getType()));
        fieldList.add(new Field(String.class, this.getModel()));
        fieldList.add(new Field(String.class, this.getManufacturer()));
        fieldList.add(new Field(Date.class, this.getProductionDate()));
        return fieldList;
    }

    @Deprecated
    public Field[] getAllFieldsToArray() {
        /*Field fields[] = new Field[5];
        fields[0] = new Field(Integer.class, this.getIn());
        fields[1] = new Field(String.class, this.getType());
        fields[2] = new Field(String.class, this.getModel());
        fields[3] = new Field(String.class, this.getManufacturer());
        fields[4] = new Field(Date.class, this.getProductionDate());*/
        List<Field> fieldsList = getAllFields();
        Field[] fields = new Field[fieldsList.size()];
        fieldsList.toArray(fields);
        return fields;
    }

    public void fillAllFields(List<Field> fields) {



    }

    @Deprecated
    public void fillAllFields(Field[] fields) {
        if (fields[0].getType() != null) {
            if (Integer.class.isAssignableFrom(fields[0].getType())) {
                int in = (Integer) fields[0].getValue();
                if (in > 0) {
                    this.setIn(in);
                }
            }
        }
        if (fields[1].getType() != null) {
            if (String.class.isAssignableFrom(fields[1].getType())) {
                String type = (String) fields[1].getValue();
                this.setType(type);
            }
        }
        if (fields[2].getType() != null) {
            if (String.class.isAssignableFrom(fields[2].getType())) {
                String model = (String) fields[2].getValue();
                this.setModel(model);
            }
        }
        if (fields[3].getType() != null) {
            if (String.class.isAssignableFrom(fields[3].getType())) {
                String manufacturer = (String) fields[3].getValue();
                this.setManufacturer(manufacturer);
            }
        }
        if (fields[4].getType() != null) {
            if (Date.class.isAssignableFrom(fields[4].getType())) {
                Date productionDate = (Date) fields[4].getValue();
                this.setProductionDate(productionDate);
            }
        }
    }
}


