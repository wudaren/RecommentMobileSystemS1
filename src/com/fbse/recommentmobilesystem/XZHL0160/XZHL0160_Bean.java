/*  XZHL0160_XZHL0160_Bean.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                        */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL0160                                                                                               */
/*  画面名 ：会员详细信息显示                                                                                         */
/*  实现功能 ：查询会员详细数据的数据bean。                                                                           */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/18   V01L01      FBSE)胡郑毅      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/

package com.fbse.recommentmobilesystem.XZHL0160;

/**
 * 会员详细信息数据bean类
 * 查询会员详细信息数据bean
 */
public class XZHL0160_Bean {

    // 会员照片
    private String memberPhoto;

    // 会员姓名
    private String memberName;

    // 会员类型
    private String memberType;

    // 会员电话
    private String memberTel;

    // 会员性别
    private String memberSex;

    // 会员生日
    private String memberBirthday;

    // 会员地址
    private String memberAddress;

    // 会员备注
    private String memberRemark;

    /**
     * 获得会员照片
     * @return 会员照片
     */
    public String getMemberPhoto() {

        return memberPhoto;
    }

    /**
     * 设置会员照片
     * @param memberPhoto 会员照片
     */
    public void setMemberPhoto(String memberPhoto) {

        this.memberPhoto = memberPhoto;
    }

    /**
     * 获得会员姓名
     * @return 会员姓名
     */
    public String getMemberName() {

        return memberName;
    }

    /**
     * 设置会员姓名
     * @param memberName 会员姓名
     */
    public void setMemberName(String memberName) {

        this.memberName = memberName;
    }

    /**
     * 获得会员类型
     * @return 会员类型
     */
    public String getMemberType() {

        return memberType;
    }

    /**
     * 设置会员类型
     * @param memberType 会员类型
     */
    public void setMemberType(String memberType) {

        this.memberType = memberType;
    }

    /**
     * 获得会员电话
     * @return 会员电话
     */
    public String getMemberTel() {

        return memberTel;
    }

    /**
     * 设置会员电话
     * @param memberTel 会员电话
     */
    public void setMemberTel(String memberTel) {

        this.memberTel = memberTel;
    }

    /**
     * 获得会员性别
     * @return 会员性别
     */
    public String getMemberSex() {

        return memberSex;
    }

    /**
     * 设置会员性别
     * @param memberSex 会员性别
     */
    public void setMemberSex(String memberSex) {

        this.memberSex = memberSex;
    }

    /**
     * 获得会员生日
     * @return 会员生日
     */
    public String getMemberBirthday() {

        return memberBirthday;
    }

    /**
     * 设置会员生日
     * @param memberBirthday 会员生日
     */
    public void setMemberBirthday(String memberBirthday) {

        this.memberBirthday = memberBirthday;
    }

    /**
     * 获得会员住址
     * @return 会员住址
     */
    public String getMemberAddress() {

        return memberAddress;
    }

    /**
     * 设置会员住址
     * @param memberAddress 会员住址
     */
    public void setMemberAddress(String memberAddress) {

        this.memberAddress = memberAddress;
    }

    /**
     * 获得会员备注
     * @return 会员备注
     */
    public String getMemberRemark() {

        return memberRemark;
    }

    /**
     * 设置会员备注
     * @param memberRemark 会员备注
     */
    public void setMemberRemark(String memberRemark) {

        this.memberRemark = memberRemark;
    }
}
