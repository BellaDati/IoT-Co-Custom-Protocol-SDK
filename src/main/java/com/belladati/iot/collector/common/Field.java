package com.belladati.iot.collector.common;

public class Field {

    private String label;
    private FieldType type;

    public Field(String label, FieldType type) {
        this.label = label;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public FieldType getType() {
        return type;
    }
}
