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

}
