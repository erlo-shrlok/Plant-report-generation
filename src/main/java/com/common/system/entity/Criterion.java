package com.common.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.beans.Transient;

public class Criterion {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String type;// 安全子类
    private String refer;// 测评指标
    @TableField("class")
    private String cla;// 安全大类

    @TableField(exist = false) // 用于在查询时临时存储关联的 ReportItem 对象
    private ReportItem reportItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public ReportItem getReportItem() {
        return reportItem;
    }

    public void setReportItem(ReportItem reportItem) {
        this.reportItem = reportItem;
    }

    @Override
    public String toString() {
        return "Criterion{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", refer='" + refer + '\'' +
                ", cla='" + cla + '\'' +
                '}';
    }
}
