/*  XZHL0140_MemberBean.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                                                                                                                                                       */
/*  画面ＩＤ     ：Common                                                                                                   */
/*  画面名     ：会员信息修改                                                                                                                                                                                                                               */
/*  实现功能 ：显示会员信息测试数据实体类                                                                                                                                                                                                   */
/*                                                                                                                    */
/*  变更历史                                                                                                                                                                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                                                                                                                                       */
/*      1   2014/05/19   V01L01      FBSE)李鑫      新規作成                                                                                                                                                   */
/*      2   2014/05/22   V01L01      FBSE)张宁       Serializable增加 ，common抽出                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.common.common_entity;

import java.io.Serializable;

/**
 *  会员情报修改画面后台显示数据的测试数据实体类
 * 
 *  显示会员信息测试数据实体类
 */
public class MemberBean implements Serializable{

    @Override
    public String toString() {

        return "MemberBean [success=" + success + ", id=" + id + ", image=" + image + ", name=" + name + ", type="
                + type + ", tel=" + tel + ", sex=" + sex + ", birthday=" + birthday + ", address=" + address
                + ", remarks=" + remarks + "]";
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // success
    private String success;

    // id
    private String id;

    // 照片url
    private String image;

    // 姓名
    private String name;

    // 类型
    private String type;

    // 手机
    private String tel;

    // 性别
    private String sex;

    // 生日
    private String birthday;

    // 地址
    private String address;

    // 街道备注
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
