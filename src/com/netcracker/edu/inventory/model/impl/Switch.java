package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;

import java.util.List;

public class Switch extends Router implements Device {

    protected int numberOfPorts;
    protected ConnectorType portsType;
    protected List<Connection> portsConnections;

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    public ConnectorType getPortsType() {
        return portsType;
    }

    public Connection getPortConnection(int portNumber) {
        return null;
    }
    public void setPortConnection(Connection connection, int portNumber) {
    }

    public List<Connection> getAllPortConnections(){
        return null;
    }

    public void fillAllFields(Field[] fields) {
        Field superFields[] = new Field[fields.length - 1];
        System.arraycopy(fields, 0, superFields, 0, fields.length - 1);
        super.fillAllFields(superFields);
        if (fields[fields.length - 1].getType() != null) {
            if (Integer.class.isAssignableFrom(fields[fields.length - 1].getType())) {
                int numberOfPorts = (Integer) fields[fields.length - 1].getValue();
                this.setNumberOfPorts(numberOfPorts);
            }
        }
    }

    public Field[] getAllFieldsToArray() {
        int superFieldsLength = super.getAllFieldsToArray().length;
        Field fields[] = new Field[superFieldsLength + 1];
        System.arraycopy(super.getAllFieldsToArray(), 0, fields, 0, superFieldsLength);
        fields[superFieldsLength] = new Field(Integer.class, this.getNumberOfPorts());
        return fields;
    }
}
