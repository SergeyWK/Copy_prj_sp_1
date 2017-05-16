package com.netcracker.edu.inventory.model;

import java.util.List;

/**
 * Created by makovetskyi on 01.11.16.
 */
public interface FillableEntity {

    @Deprecated
    void fillAllFields(Field[] fields);

    @Deprecated
    Field[] getAllFieldsToArray();

    void fillAllFields(List<Field> fields);

    List<Field> getAllFields();

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
