/*  XZHL1810_HotShowArray.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO., LTD 2012                         */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ ：XZHL1810                                                                                                    */
/*  画面名   ：热区分布显示                                                                                                     */
/*  实现功能 ：进行热区显示。                                                                                                     */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/06/03   V01L01      FBSE)张海静      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1810;

import java.io.Serializable;

/**
 * 热区分布显示类的item 完成所有设备的显示
 */
public class XZHL1810_HotShowArray implements Serializable {

    // vsesion ID
    private static final long serialVersionUID = 1L;

    // id
    private int id;

    // 名称
    private String name;

    /**
     * 序号的get方法
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 名称的get方法
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * id的get方法
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 名称的set方法
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * serialVersionUID的get方法
     * @return serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
