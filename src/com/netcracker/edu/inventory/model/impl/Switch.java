package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

public class Switch extends Router implements Device {

    protected int numberOfPorts;

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }
}
