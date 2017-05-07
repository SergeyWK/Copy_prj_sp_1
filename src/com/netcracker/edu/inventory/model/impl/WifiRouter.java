package com.netcracker.edu.inventory.model.impl;


public class WifiRouter extends Router {

    protected String securityProtocol;

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
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
