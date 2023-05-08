package com.common.system.vo;

import com.common.system.entity.Cla;

import java.util.List;

/**
 * 报告生成需要的数据
 */
public class ReportVO {
    private Integer rId;// 记录报告的id
    private String name;// 报告名称
    private String createTime;//项目创建时间
    private String customer;// 客户公司
    private String system;// 被测系统
    private String level;//系统等级
    private String address;// 客户地址
    private String cname;// 客户名字
    private String phone;// 客户手机号
    private String email;// 客户邮箱
    private List<Cla> clas;// 安全大类

    public List<Cla> getClas() {
        return clas;
    }

    @Override
    public String toString() {
        return "ReportVO{" +
                "rId=" + rId +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", customer='" + customer + '\'' +
                ", system='" + system + '\'' +
                ", level='" + level + '\'' +
                ", address='" + address + '\'' +
                ", cname='" + cname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", clas=" + clas +
                '}';
    }

    public void setClas(List<Cla> clas) {
        this.clas = clas;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
