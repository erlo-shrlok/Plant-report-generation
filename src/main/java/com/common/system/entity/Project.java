package com.common.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("project")
public class Project {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("u_id")
    private Integer uId; //处理人员

    @TableField(exist = false)
    private String uName;// 处理人员姓名

    private String name; //项目名

    private String customer; //客户公司名

    private String address; //客户地址

    private String cname; //客户名

    private String phone; //客户手机号

    private String email; //客户邮箱

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private String createTime;

    @TableField("is_deleted")
    private String isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Integer getuId() {
        return uId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", uId=" + uId +
                ", name='" + name + '\'' +
                ", customer='" + customer + '\'' +
                ", address='" + address + '\'' +
                ", cname='" + cname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                '}';
    }
}
