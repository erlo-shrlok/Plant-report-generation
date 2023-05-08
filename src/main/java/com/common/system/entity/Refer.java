package com.common.system.entity;

public class Refer {
    private String referName;
    private String post;
    private String kind;

    public String getReferName() {
        return referName;
    }

    public void setReferName(String referName) {
        this.referName = referName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Refer{" +
                "referName='" + referName + '\'' +
                ", post='" + post + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
