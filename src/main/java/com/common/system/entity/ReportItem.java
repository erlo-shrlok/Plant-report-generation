package com.common.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 测评项表
 */
@TableName("report_item")
public class ReportItem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("c_id")
    private Integer cId;// 指标项
    private String post;// 结果记录
    private String kind;//符合情况
    @TableField("r_id")
    private Integer rId;// 所属报告

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    @Override
    public String toString() {
        return "ReportItem{" +
                "id=" + id +
                ", cId=" + cId +
                ", post='" + post + '\'' +
                ", kind='" + kind + '\'' +
                ", rId=" + rId +
                '}';
    }
}
