package com.common.system.entity;

import java.util.List;

public class Type {
    private String typeName;
    private List<Refer> refers;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Refer> getRefers() {
        return refers;
    }

    public void setRefers(List<Refer> refers) {
        this.refers = refers;
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeName='" + typeName + '\'' +
                ", refers=" + refers +
                '}';
    }
}
