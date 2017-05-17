package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FillableEntity;

import java.io.Serializable;
import java.util.*;
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

    public void fillAllFields(List<Field> fields) {
        if (fields.get(0).getType() != null) {
            if (Integer.class.isAssignableFrom(fields.get(0).getType())) {
                int in = (Integer) fields.get(0).getValue();
                if (in > 0) {
                    this.setIn(in);
                }
            }
            if (fields.get(1).getType() != null) {
                if (String.class.isAssignableFrom(fields.get(1).getType())) {
                    String type = (String) fields.get(1).getValue();
                    this.setType(type);
                }
            }
            if (fields.get(2).getType() != null) {
                if (String.class.isAssignableFrom(fields.get(2).getType())) {
                    String model = (String) fields.get(2).getValue();
                    this.setModel(model);
                }
            }
            if (fields.get(3).getType() != null) {
                if (String.class.isAssignableFrom(fields.get(3).getType())) {
                    String manufacturer = (String) fields.get(3).getValue();
                    this.setManufacturer(manufacturer);
                }
            }
            if (fields.get(4).getType() != null) {
                if (Date.class.isAssignableFrom(fields.get(4).getType())) {
                    Date productionDate = (Date) fields.get(4).getValue();
                    this.setProductionDate(productionDate);
                }
            }
        }

    }

    @Deprecated
    public Field[] getAllFieldsToArray() {
        List<Field> fieldsList = getAllFields();
        Field[] fields = new Field[fieldsList.size()];
        fieldsList.toArray(fields);
        return fields;
    }

    @Deprecated
    public void fillAllFields(Field[] fields) {
        List<Field> fieldsList = Arrays.asList(fields);
        fillAllFields(fieldsList);
    }
}


