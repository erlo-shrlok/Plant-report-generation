package com.common.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * rc_menu表的实体类映射
 *
 * 1、RcMenu implements the Serializable interface which allows its instances to
 * be serialized into a stream of bytes and deserialized back into objects again.
 *
 * 2、The child field is of the same type as the class itself, meaning that
 * an instance of(实例) RcMenu can have a list of child menus, forming(形成) a tree-like structure.
 *
 * 3、There are alse JsonIgnore annotations on the createTime and updateTime fields,
 *  which means that they will be excluded from JSON serialization and deserialization.
 *
 */
public class RcMenu implements Serializable{

    private static final long serialVersionUID = 4419340793159328904L;
    private String id;//menu_id

    private String code;

    private String pCode;

    private String pId;

    private String name;

    private String url;

    private Integer isMenu;

    private Integer level;

    private Integer sort;

    private Integer status;

    private String icon;
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    private List<RcMenu> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode == null ? null : pCode.trim();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<RcMenu> getChild() {
        return child;
    }

    public void setChild(List<RcMenu> child) {
        this.child = child;
    }
}