package com.netcracker.edu.inventory.model;

/**
 * Created by oleksandr on 01.11.16.
 */
public interface FillableEntity {

    void fillAllFields(Field[] fields);

    Field[] getAllFieldsToArray();

    class Field {

        Class type;
        Object value;

        public Field(Class type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

}
