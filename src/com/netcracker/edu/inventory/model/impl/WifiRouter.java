package com.netcracker.edu.inventory.model.impl;


import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;

public class WifiRouter extends Router {

    protected String securityProtocol;
    protected String technologyVersion;
    protected Connection wirelessConnection;
    protected ConnectorType wirePortType;
    protected Connection wireConnection;

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public String getTechnologyVersion() {
        return technologyVersion;
    }

    public Connection getWirelessConnection() {
        return wirelessConnection;
    }

    public void setWirelessConnection(Connection wirelessConnection) {
        this.wirelessConnection = wirelessConnection;
    }

    public ConnectorType getWirePortType() {
        return wirePortType;
    }

    public Connection getWireConnection() {
        return wireConnection;
    }

    public void setWireConnection(Connection wireConnection) {
        this.wireConnection = wireConnection;
    }

    public void fillAllFields(Field[] fields) {
        Field superFields[] = new Field[fields.length - 1];
        System.arraycopy(fields, 0, superFields, 0, fields.length - 1);
        super.fillAllFields(superFields);
        if (fields[fields.length - 1].getType() != null) {
            if (String.class.isAssignableFrom(fields[fields.length - 1].getType())) {
                String securityProtocol = (String) fields[fields.length - 1].getValue();
                this.setSecurityProtocol(securityProtocol);
            }
        }
    }

    public Field[] getAllFieldsToArray() {
        int superFieldsLength = super.getAllFieldsToArray().length;
        Field fields[] = new Field[superFieldsLength + 1];
        System.arraycopy(super.getAllFieldsToArray(), 0, fields, 0, superFieldsLength);
        fields[superFieldsLength] = new Field(String.class, this.getSecurityProtocol());
        return fields;
    }

}
