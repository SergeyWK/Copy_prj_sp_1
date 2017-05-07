package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

public class Battery extends AbstractDevice implements Device {

    protected int chargeVolume;

    public int getChargeVolume() {
        return chargeVolume;
    }

    public void setChargeVolume(int chargeVolume) {
        this.chargeVolume = chargeVolume;
    }

    public void fillAllFields(Field[] fields) {
        super.fillAllFields(fields);
        if (fields[fields.length - 1].getType() != null) {
            if (Integer.class.isAssignableFrom(fields[fields.length - 1].getType())) {
                int chargeVolume = (Integer) fields[fields.length - 1].getValue();
                this.setChargeVolume(chargeVolume);
            }
        }
    }

    public Field[] getAllFieldsToArray() {
        int superFieldsLength = super.getAllFieldsToArray().length;
        Field fields[] = new Field[superFieldsLength + 1];
        System.arraycopy(super.getAllFieldsToArray(), 0, fields, 0, superFieldsLength);
        fields[superFieldsLength] = new Field(Integer.class, this.getChargeVolume());
        return fields;
    }

}
