package com.common.system.entity;

import java.util.List;

public class Cla {
    private String claName;
    private List<Type> types;

    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Cla{" +
                "claName='" + claName + '\'' +
                ", types=" + types +
                '}';
    }
}
