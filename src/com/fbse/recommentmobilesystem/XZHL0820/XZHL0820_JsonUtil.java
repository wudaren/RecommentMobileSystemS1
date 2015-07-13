/*  XZHL0820_JsonUtil.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                              */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ     ：XZHL0820                                                                                                */
/*  画面名     ：商品录入                                                                                                     */
/*  实现功能 ：显示商品录入 解析类                                                                                                  */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/22   V01L01      FBSE)高振川      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0820;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.common_entity.Category;
import com.fbse.recommentmobilesystem.common.common_entity.CategoryList;

/**
 *  商品录入解析类
 *
 *  完成商品录入
 */
public class XZHL0820_JsonUtil {

    // 获取返回值信息成功
    private static final String SUCCESS = "1";

    // 获取返回值信息失败
    private static final String FAILED = "0";

    // 获取返回值信息的其他状态
    private static final String OTHER = "2";
    /**
     * 解析返回JSON
     * @param json 出入JSON字符串
     * @return result
     */
    public static String json2Result(String json) {
        LogUtil.logStart();
        StringBuilder result = new StringBuilder();
        try {
            JSONObject user = new JSONObject(json);
            result.append(user.getString("result"));
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
        return result.toString();
    }

    /**
     * 解析返回JSON
     * @param json 出入JSON字符串
     * @return Category
     */
    public static Category jsonToCategory(String json) {
        LogUtil.logStart();
        Category category = new Category();
        List<CategoryList> list = null;
        try {
            JSONObject jb = new JSONObject(json);
            String success = jb.getString("success");
            if (SUCCESS.equals(success)) {
                JSONObject data = jb.getJSONObject("data");
                category.setCount(data.getString("count"));
                JSONArray categoryArray = data.getJSONArray(
                        "category");
                list = new ArrayList<CategoryList>();
                for (int i = 0; i < categoryArray.length(); i++) {
                    JSONObject categoryStr = categoryArray.getJSONObject(i);
                    CategoryList categoryList = new CategoryList();
                    categoryList.setId(categoryStr.getString("id"));
                    categoryList.setName(categoryStr.getString("name"));
                    categoryList.setOrder(categoryStr.getString("order"));
                    list.add(categoryList);
                }
                category.setObj(list);
            } else if (FAILED.equals(success)) {
                category.setSuccess(jb.getString("success"));
            } else {
                category.setSuccess(OTHER);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
        return category;
    }

    /**
     * 解析返回JSON
     * @param json 出入JSON字符串
     * @return Category
     */
    public static Category jsonToCategoryList(String json) {
        LogUtil.logStart();
        Category category = null;
        try {
            JSONObject jb = new JSONObject(json);
            category = new Category();
            String success = jb.getString("success");
            if (SUCCESS.equals(success)) {
                category.setSuccess(jb.getString("success"));
                JSONObject categoryBean = jb.getJSONObject("data");
                JSONArray categoryList = jb.getJSONObject("data").getJSONArray(
                        "supplier");
                List<CategoryList> array = new ArrayList<CategoryList>();
                for (int i = 0; i < categoryList.length(); i++) {
                    JSONObject repairStr = categoryList.getJSONObject(i);
                    CategoryList list = new CategoryList();
                    list.setId(repairStr.getString("id"));
                    list.setOrder(repairStr.getString("order"));
                    list.setName(repairStr.getString("name"));
                    list.setId(categoryBean.getString("count"));
                    array.add(list);
                }
                category.setObj(array);
            } else if (FAILED.equals(success)) {
                category.setSuccess(jb.getString("success"));
            } else {
                category.setSuccess(OTHER);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
        return category;
    }

    /**
     * 检测金额正则表达式
     * @param String passWord 出入字符串金额
     * @return 检测金额是否符合正则表达式规范的布尔值
     */
    public static boolean checkMoney(String passWord) { 
        String regex = "^[0-9]{0,}+(\\,[0-9]{3})*+(\\.[0-9]{0,2})?"; 
        Pattern p = Pattern.compile(regex); 
        Matcher m = p.matcher(passWord); 
        return m.matches();
    }

    /**
     * 检测库存正则表达式
     * @param String passWord 出入字符串库存
     * @return 检测库存是否符合正则表达式规范的布尔值
     */
    public static boolean checkStacks(String stacks) { 
        String regex = "^[0-9]{0,}"; 
        Pattern p = Pattern.compile(regex); 
        Matcher m = p.matcher(stacks); 
        return m.matches();
    }
}
