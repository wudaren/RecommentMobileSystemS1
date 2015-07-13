/*  XZHL1210_Constants.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                            */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1210                                                                                           */
/*  画面名     ：店员一览                                                                                             */
/*  实现功能 ：对店员信息进行一览的工具类。                                                                           */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1210;

import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;

import android.content.Context;

/**
 * 店员一览的常量类
 */
public class XZHL1210_Constants {

    // 静态的店员一览请求数据的地址
    public static final String HTTP = "";

    // 上一个画面传递过来的参数
    static String store = "";

    // 删除成功的提示信息
    static String deletesuccess="";

    // 删除失败的提示信息
    static String deletefail="";

    // 删除异常的提示信息
    static String yichang="";

    // 从上一个画面穿过来的值的名字
    static String store_tag="store";

    // 空字符串常量
    public static final String DEFAULT="";

    // 删除店员的接口地址
    public static final String DELETEADDRESS="user/delete";

    // 请求店员信息的地址
    public static final String GETADDRESS="listUser";

    // 请求数据的接口的端口名称
    public static final String PORTNAME="UserPort";

    // 对话框的提示警告消息
    static String warninfo="";

    // 确定按钮字面量
    static String sure="";

    // 取消按钮的字面量
    static String cancle="";

    // 服务器返回的数据中data的字面量
    public static final String DATA="data";

    // 服务器返回的数据中success的字面量
    public static final String SUCCESS="success";

    // 服务器返回的数据中id的字面量
    public static final String ID="id";

    // 服务器返回的数据中username的字面量
    public static final String USERNAME="username";

    // 服务器返回的数据中realname的字面量
    public static final String REALNAME="realname";

    // 服务器返回的数据中role的字面量
    public static final String ROLE="role";

    // 服务器返回的数据中tel的字面量
    public static final String TEL="tel";

    // 服务器返回的数据中shops的字面量
    public static final String SHOPS="shops";

    // 服务器返回的数据中store的字面量
    public static final String JSONSTORE="store";

    // 服务器返回的数据无店员信息的提示消息
    static String zanwu = "";

    // 登录者的权限
    static String denglurole="";

    // 登录者的用户名
    static String dengluname="";
    /**
     * 从Msg常量类中读取出信息
     * @param context 传递过来的一个上下文参数
     */
    public static void initStrings(Context context){

        // Log开始
        LogUtil.logStart();
        XZHL1210_Constants.deletesuccess=MessageUtil.getMessage(context, Msg.I021, new String[]{"店员"});
        XZHL1210_Constants.deletefail=MessageUtil.getMessage(context, Msg.E0025, new String[]{"店员"});
        XZHL1210_Constants.yichang=MessageUtil.getMessage(context, Msg.E0002);
        XZHL1210_Constants.sure=MessageUtil.getMessage(context, Msg.I012);
        XZHL1210_Constants.cancle=MessageUtil.getMessage(context, Msg.I005);
        XZHL1210_Constants.warninfo=MessageUtil.getMessage(context, Msg.Q015, new String[]{"该店员"});
        XZHL1210_Constants.zanwu=MessageUtil.getMessage(context, Msg.I002, new String[]{"店员"});

        // Log结束
        LogUtil.logEnd();
    }

}
