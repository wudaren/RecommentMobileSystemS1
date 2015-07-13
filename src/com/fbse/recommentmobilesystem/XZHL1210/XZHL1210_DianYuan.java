/*  XZHL1210_DianYuan.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                             */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1210                                                                                           */
/*  画面名     ：店员一览                                                                                             */
/*  实现功能 ：对店员一览功能的实体类。                                                                               */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1210;

/**
 * 封装店员信息的实体类
 */
public class XZHL1210_DianYuan {

    // Id
    private String id;

    // 姓名
    private String name;

    // 登录名
    private String loginName;

    // 角色
    private String role;

    // 店铺
    private String store;

    // 哪些店铺
    private String shops;

    // 电话
    private String tel;

    /**
     * 获取id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     * @param id id 设置值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name
     * @param name name 设置值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取loginName
     * @return loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置loginName
     * @param loginName loginName 设置值
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取role
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置role
     * @param role role 设置值
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取store
     * @return store
     */
    public String getStore() {
        return store;
    }

    /**
     * 设置store
     * @param store store 设置值
     */
    public void setStore(String store) {
        this.store = store;
    }

    /**
     * 获取shops
     * @return shops
     */
    public String getShops() {
        return shops;
    }

    /**
     * 设置shops
     * @param shops shops 设置值
     */
    public void setShops(String shops) {
        this.shops = shops;
    }

    /**
     * 获取tel
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置tel
     * @param tel tel 设置值
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

}
