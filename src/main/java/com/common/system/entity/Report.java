package com.common.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("report")
public class Report {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String remark; //备注

    @TableField("p_id")
    private Integer pId; //项目编号

    private String name; //被测系统名

    private String level; //系统等级

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
