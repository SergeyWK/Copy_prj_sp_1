package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

public class Router extends AbstractDevice implements Device {

    protected int dataRate;

    public int getDataRate() {
        return dataRate;
    }

    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
    }

    public void fillAllFields(Field[] fields) {
        super.fillAllFields(fields);
        if (fields[fields.length - 1].getType() != null) {
            if (Integer.class.isAssignableFrom(fields[fields.length - 1].getType())) {
                int dataRate = (Integer) fields[fields.length - 1].getValue();
                this.setDataRate(dataRate);
            }
        }
    }

    public Field[] getAllFieldsToArray() {
        int superFieldsLength = super.getAllFieldsToArray().length;
        Field fields[] = new Field[superFieldsLength + 1];
        System.arraycopy(super.getAllFieldsToArray(), 0, fields, 0, superFieldsLength);
        fields[superFieldsLength] = new Field(Integer.class, this.getDataRate());
        return fields;
    }

}
