/**
 * 项目名称 ：信智互联
 * 类一览：
 * JsonUtil       共通类
 */
package com.fbse.recommentmobilesystem.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_LoginBean;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_LoginInfo;
import com.fbse.recommentmobilesystem.XZHL0220.XZHL0220_Wenjuan;
import com.fbse.recommentmobilesystem.XZHL0410.XZHL0410_TaskArray;
import com.fbse.recommentmobilesystem.XZHL0410.XZHL0410_TaskBean;
import com.fbse.recommentmobilesystem.XZHL0510.XZHL0510_DeviceArray;
import com.fbse.recommentmobilesystem.XZHL0510.XZHL0510_DeviceBean;
import com.fbse.recommentmobilesystem.XZHL0520.XZHL0520_RepairArray;
import com.fbse.recommentmobilesystem.XZHL0520.XZHL0520_RepairBean;
import com.fbse.recommentmobilesystem.XZHL0530.XZHL0530_RepairAddBean;
import com.fbse.recommentmobilesystem.XZHL0620.XZHL0620_MailInfoArray;
import com.fbse.recommentmobilesystem.XZHL0620.XZHL0620_MailInfoBean;
import com.fbse.recommentmobilesystem.XZHL0810.XZHL0810_GoodsItemBean;
import com.fbse.recommentmobilesystem.XZHL1810.XZHL1810_HotShowArray;
import com.fbse.recommentmobilesystem.XZHL1810.XZHL1810_HotShowBean;
import com.fbse.recommentmobilesystem.common.common_entity.MemberBean;
import com.fbse.recommentmobilesystem.common.common_entity.ServerError;

/**
 * <dl>
 * <dd>功能名：json解析
 * <dd>功能说明：将json解析成对应的字符串。
 * @version    V1.0    2014/06/05
 * @author     FBSE
 */
public class JsonUtil {

    private static String BT="zaq12wsx";

    // 成功标志
    private static String SUCCESS="success";

    // data标志
    private static String DATA="data";

    // 成功
    private static String ONE ="1";

    // 失败
    private static String ZERO ="0";

    // 返回值异常
    private static String CATCH ="2";

    /**
     * 拼接字符串
     * @param key key数组
     * @param value 值数组
     * @return json
     */
    public static String DataToJson(String[] key, String[] value) {
        JSONStringer sj = new JSONStringer();
        try {
            if (key.length > 0 && key != null) {
                sj.object();
                for (int i = 0; i < key.length; i++) {
                    sj.key(key[i]);
                    sj.value(value[i]);
                }
                sj.endObject();
            } else {
                System.out.println("参数为空");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sj.toString();
    }

    /**
     * json加密
     * @param serial 标志
     * @param json json串
     * @param token token值
     * @return md5值
     */
    public static String getSign(String serial, String json, String token) {
        return Encrypt_Util.md5(serial + json + BT + token);
    }

    /**
     * 登录加密
     * @param serial 标志
     * @param json json串
     * @return md5值
     */
    public static String getLogSign(String serial, String json) {
        return Encrypt_Util.md5(serial + json + BT);
    }

    /**
     * 字符串解析成json
     * @param serial 标志
     * @param data 数据
     * @param token token值
     * @param sign sign值
     * @return json
     */
    public static String DataToJson(String serial, String data, String token,
            String sign) {
        JSONStringer sj = new JSONStringer();
        try {
            sj.object();
            sj.key("serial");
            sj.value(serial);
            sj.key("data");
            sj.value(data);
            sj.key("token");
            sj.value(token);
            sj.key("sign");
            sj.value(sign);
            sj.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sj.toString();
    }

    /**
     * 登录画面json解析
     * @param json json串
     * @return 数据
     */
    public static XZHL0001_LoginBean JsonToLogin(String json) {
        XZHL0001_LoginBean login = null;
        try {
            JSONObject jb = new JSONObject(json);
            login = new XZHL0001_LoginBean();
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                login.setSuccess(jb.getString(SUCCESS));
                JSONObject data = jb.getJSONObject(DATA);
                login.setId(data.getString("id"));
                login.setUsername(data.getString("username"));
                login.setRole(data.getString("role"));
                login.setRealname(data.getString("realname"));
                login.setTel(data.getString("tel"));
                login.setStore(data.getString("store"));
                login.setShops(data.getString("shops"));
                login.setStore_face_no(data.getString("store_face_no"));
                XZHL0001_LoginInfo.SHOPID = data.getString("store_face_no");
                login.setToken(data.getString("token"));
            } else if (ZERO.equals(success)) {
                login.setSuccess(jb.getString(SUCCESS));
            } else {
                login.setSuccess(CATCH);
                System.out.println("返回值异常");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return login;
    }

    /**
     * 任务完成状况画面json解析
     * @param json json串
     * @return 数据
     */
    public static XZHL0410_TaskBean JsonToTask(String json) {
        XZHL0410_TaskBean task = null;
        try {
            JSONObject jb = new JSONObject(json);
            task = new XZHL0410_TaskBean();
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                task.setSuccess(jb.getString(SUCCESS));
                JSONObject data = jb.getJSONObject(DATA);
                task.setShop_point(data.getString("shop_point"));
                task.setMonth_point(data.getString("month_point"));
                task.setComplete_point(data.getString("complete_point"));
                task.setMonth_complete_point(data.getString("month_complete_point"));
                JSONArray taskArray = data.getJSONArray("task");
                List<XZHL0410_TaskArray> taskList = new ArrayList<XZHL0410_TaskArray>();
                for (int i = 0; i < taskArray.length(); i++) {
                    JSONObject taskStr = taskArray.getJSONObject(i);
                    XZHL0410_TaskArray ta = new XZHL0410_TaskArray();
                    ta.setName(taskStr.getString("name"));
                    ta.setRate(taskStr.getString("rate"));
                    ta.setSales(taskStr.getString("sales"));
                    ta.setPoint(taskStr.getString("point"));
                    taskList.add(ta);
                }
                task.setTask(taskList);
            } else if (ZERO.equals(success)) {
                task.setSuccess(jb.getString(SUCCESS));
            } else {
                task.setSuccess(CATCH);
                System.out.println("返回值异常");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return task;
    }

    /**
     * 加密
     * @param serial 标志
     * @param json 数据
     * @param token token值
     * @return md5
     */
    public static String getHaveToken(String serial, String json, String token) {

        return Encrypt_Util.md5(serial + json + BT + token);
    }

    /**
     * 设备画面json解析
     * @param json json串
     * @return 设备
     */
    public static XZHL0510_DeviceBean JsonToDevice(String json) {
        XZHL0510_DeviceBean device = null;
        try {
            JSONObject jb = new JSONObject(json);
            device = new XZHL0510_DeviceBean();
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                device.setSuccess(success);
                JSONArray deviceArray = jb.getJSONObject(DATA).getJSONArray("device");
                List<XZHL0510_DeviceArray> deviceList = new ArrayList<XZHL0510_DeviceArray>();
                for (int i = 0; i < deviceArray.length(); i++) {
                    JSONObject deviceStr = deviceArray.getJSONObject(i);
                    XZHL0510_DeviceArray da = new XZHL0510_DeviceArray();
                    da.setId(deviceStr.getString("id"));
                    da.setSerial(deviceStr.getString("serial"));
                    da.setName(deviceStr.getString("name"));
                    da.setModel(deviceStr.getString("model"));
                    da.setBrand(deviceStr.getString("brand"));
                    da.setOrgin_serial(deviceStr.getString("orgin_serial"));
                    da.setType(deviceStr.getString("type"));
                    da.setStore(deviceStr.getString("store"));
                    da.setShops(deviceStr.getString("shops"));
                    da.setDate(deviceStr.getString("date"));
                    da.setStatus(deviceStr.getString("status"));
                    deviceList.add(da);
                }
                device.setDevice(deviceList);
            } else if (ZERO.equals(success)) {
                device.setSuccess(jb.getString(SUCCESS));
            } else {
                device.setSuccess(CATCH);
                System.out.println("返回值异常");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return device;
    }

    /**
     * 报修记录画面json解析
     * @param json json串
     * @return 报修记录
     */
    public static XZHL0530_RepairAddBean JsonToRepairAdd(String json) {
        XZHL0530_RepairAddBean repairAdd = null;
        try {
            JSONObject jb = new JSONObject(json);
            repairAdd = new XZHL0530_RepairAddBean();
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                repairAdd.setSuccess(jb.getString(SUCCESS));
                JSONObject data = jb.getJSONObject(DATA);
                repairAdd.setRepair_no(data.getString("repair_no"));
            } else if (ZERO.equals(success)) {
                repairAdd.setSuccess(jb.getString(SUCCESS));
            } else {
                repairAdd.setSuccess(CATCH);
                System.out.println("返回值异常");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repairAdd;
    }

    /**
     * 设备报修画面json解析
     * @param json json串
     * @return 设备报修
     */
    public static XZHL0520_RepairBean JsonToRepair(String json) {
        XZHL0520_RepairBean repair = null;
        try {
            JSONObject jb = new JSONObject(json);
            repair = new XZHL0520_RepairBean();
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                repair.setSuccess(jb.getString(SUCCESS));
                JSONArray repairArray = jb.getJSONObject(DATA).getJSONArray(
                        "repair");
                List<XZHL0520_RepairArray> repairList = new ArrayList<XZHL0520_RepairArray>();
                for (int i = 0; i < repairArray.length(); i++) {
                    JSONObject repairStr = repairArray.getJSONObject(i);
                    XZHL0520_RepairArray ra = new XZHL0520_RepairArray();
                    ra.setId(repairStr.getString("id"));
                    ra.setRepair_no(repairStr.getString("repair_no"));
                    ra.setDevice_Id(repairStr.getString("device_Id"));
                    ra.setSerial(repairStr.getString("serial"));
                    ra.setName(repairStr.getString("device_name"));
                    ra.setOrgin_serial(repairStr.getString("orgin_serial"));
                    ra.setDate(repairStr.getString("date"));
                    ra.setStatus(repairStr.getString("status"));
                    ra.setReason(repairStr.getString("reason"));
                    ra.setResult(repairStr.getString("result"));
                    ra.setComment(repairStr.getString("comment"));
                    repairList.add(ra);
                }
                repair.setRepair(repairList);
            } else if (ZERO.equals(success)) {
                repair.setSuccess(jb.getString(SUCCESS));
            } else {
                repair.setSuccess(CATCH);
                System.out.println("返回值异常");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repair;
    }

    /**
     * json解析
     * @param json json串
     * @return 
     */
    public static ServerError errorJson(String json) {
        ServerError error = null;
        try {
            JSONObject jb = new JSONObject(json);
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                return error;
            }else if (ZERO.equals(success)) {
                error = new ServerError();
                JSONObject data = jb.getJSONObject(DATA);
                error.setErr_no(data.getString("err_no"));
                error.setErr_msg(data.getString("err_msg"));
                return error;
            }else if ("9".equals(success)) {
                error = new ServerError();
                error.setSuccess(jb.getString(SUCCESS));
                return error;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }
    
    /**
     * 商品信息查询json解析
     * @param json json串
     * @return 商品信息
     */
    public static List<XZHL0810_GoodsItemBean> JsonToGoodsList(String json) {
        List<XZHL0810_GoodsItemBean> goodsList = null;
        try {
            JSONObject jb = new JSONObject(json);
            String success = jb.getString(SUCCESS);
            if (ONE.equals(success)) {
                JSONArray goodsArray = jb.getJSONObject(DATA).getJSONArray("goods");
                goodsList = new ArrayList<XZHL0810_GoodsItemBean>();
                for (int i = 0; i < goodsArray.length(); i++) {
                    JSONObject goodsStr = goodsArray.getJSONObject(i);
                    XZHL0810_GoodsItemBean ra = new XZHL0810_GoodsItemBean();
                    ra.setId(goodsStr.getString("id"));
                    ra.setRealityPrice(goodsStr.getString("realityPrice"));
                    ra.setPrice(goodsStr.getString("price"));
                    ra.setGoodsNumber(goodsStr.getString("goodsNumber"));
                    ra.setStocks(goodsStr.getString("stocks"));
                    ra.setSupplier(goodsStr.getString("supplier"));
                    ra.setGoodsName(goodsStr.getString("goodsName"));
                    ra.setGoodsType(goodsStr.getString("goodsType"));
                    goodsList.add(ra);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    /**
     * 问卷内容json解析
     * @param json json串
     * @return 问卷内容
     */
    public static XZHL0220_Wenjuan jsonToWenjuan(String json){
        XZHL0220_Wenjuan wj = null;
        try {
            JSONObject jb = new JSONObject(json);
            wj = new XZHL0220_Wenjuan();
            wj.setSuccess(jb.getString(SUCCESS));
            wj.setId(jb.getJSONObject(DATA).getString("q_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wj;
    }

    /**
     * 会员增加、修改、查看json解析
     * @param json json串
     * @return 问卷内容
     */
    public static MemberBean jsonToMember(String json){
    	MemberBean member = null;
        try {
            JSONObject jb = new JSONObject(json);
            member = new MemberBean();
            member.setSuccess(jb.getString(SUCCESS));
            JSONObject data = new JSONObject(jb.getString(DATA));
            member.setId(data.getString("id"));
            member.setImage(data.getString("image"));
            member.setName(data.getString("name"));
            member.setType(data.getString("type"));
            member.setTel(data.getString("tel"));
            member.setSex(data.getString("sex"));
            member.setAddress(data.getString("address"));
            member.setRemarks(data.getString("remarks"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return member;
    }

    /**
    * 得到注册等信息中json的 success字符串
    * @param json json串
    * @return resultInfo 返回结果
    * @throws JSONException 
    */
    public static String successJSON(String json) {
        String resultInfo = null;
        try {
            // 解析返回的数据
            JSONObject jOB = new JSONObject(json);
            // 1代表成功，0代表失败
            resultInfo = jOB.getString(SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultInfo;
    }
    
    /**
     * 邮寄一览解析
     * @param json json串
     * @return 数据
     */
    public static XZHL0620_MailInfoBean JsonToMail(String json) {
        XZHL0620_MailInfoBean mailInfoBean = null;
        try {
            mailInfoBean = new XZHL0620_MailInfoBean();

            // 解析返回的数据
            JSONObject jOB = new JSONObject(json);

            // 1代表成功，0代表失败
            String resultInfo = jOB.getString(SUCCESS);
            if (ONE.equals(resultInfo)) {
                mailInfoBean.setSuccess(SUCCESS);
                JSONArray taskArray = jOB.getJSONObject(DATA).getJSONArray("post");
                List<XZHL0620_MailInfoArray> mailInfoArrays = new ArrayList<XZHL0620_MailInfoArray>();
                for (int i = 0; i < taskArray.length(); i++) {
                    JSONObject deviceStr = taskArray.getJSONObject(i);
                    XZHL0620_MailInfoArray mailInfo = new XZHL0620_MailInfoArray();
                    mailInfo.setId(deviceStr.getInt("id"));
                    mailInfo.setNumber(deviceStr.getString("number"));
                    mailInfo.setSenderName(deviceStr.getString("senderName"));
                    mailInfo.setSenderTel(deviceStr.getString("senderTel"));
                    mailInfo.setSenderAddress(deviceStr.getString("senderAddress"));
                    mailInfo.setSenderStreet(deviceStr.getString("senderStreet"));
                    mailInfo.setAddresseeName(deviceStr.getString("adresseeName"));
                    mailInfo.setAddresseeTel(deviceStr.getString("adresseeTel"));
                    mailInfo.setAddresseeAddress(deviceStr.getString("adresseeAddress"));
                    mailInfo.setAddresseeStreet(deviceStr.getString("adresseeStreet"));
                    mailInfo.setState(deviceStr.getInt("state"));
                    mailInfoArrays.add(mailInfo);
                }
                mailInfoBean.setMail(mailInfoArrays);
            } else if(ZERO.equals(resultInfo)){
                mailInfoBean.setSuccess(jOB.getString(SUCCESS));
            }else{
                System.out.println("返回值异常");
                mailInfoBean.setSuccess(CATCH);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mailInfoBean;
    }

    /**
     * 热区分布显示的解析json
     * @param json json串
     * @return hotShowBean
     */
    public static XZHL1810_HotShowBean JsonToHot(String json) {
        XZHL1810_HotShowBean hotShowBean = null;
        try {
            hotShowBean = new XZHL1810_HotShowBean();
            // 解析返回的数据
            JSONObject jOB = new JSONObject(json);
            // 1代表成功，0代表失败
            String resultInfo = jOB.getString(SUCCESS);
            if (ONE.equals(resultInfo)) {
                hotShowBean.setSuccess(SUCCESS);
                JSONArray taskArray = jOB.getJSONObject(DATA).getJSONArray("carema");
                List<XZHL1810_HotShowArray> hotShowArrays = new ArrayList<XZHL1810_HotShowArray>();
                for (int i = 0; i < taskArray.length(); i++) {
                    JSONObject deviceStr = taskArray.getJSONObject(i);
                    XZHL1810_HotShowArray hotShowArray = new XZHL1810_HotShowArray();
                    hotShowArray.setId(deviceStr.getInt("id"));
                    hotShowArray.setName(deviceStr.getString("name"));
                    hotShowArrays.add(hotShowArray);
                }
                hotShowBean.setHot(hotShowArrays);
            } else if(ZERO.equals(resultInfo)){
                hotShowBean.setSuccess(jOB.getString(SUCCESS));
            }else{
                System.out.println("返回值异常");
                hotShowBean.setSuccess(CATCH);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotShowBean;
    }
}
