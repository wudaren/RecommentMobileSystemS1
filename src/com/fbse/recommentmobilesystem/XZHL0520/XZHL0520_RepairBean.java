/**
 * XZHL0520_RepairBean.java 
 * XZHL0520_RepairBean
 * 版本信息 V1.0
 * 创建日期（2014-05-14）
 */
package com.fbse.recommentmobilesystem.XZHL0520;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * XZHL0520_RepairBean
 * @author gaozhenchuan 
 * 报修记录列表
 * 创建日期（2014-05-14）
 * 修改者，修改日期（YYYY-MM-DD），修改内容
 */
public class XZHL0520_RepairBean implements Serializable{

    /**
     * XZHL0520_RepairBean的标识
     */
    private static final long serialVersionUID = -78870591477909560L;
    // 是否成功返回报修记录
    private String success = "";
    // 报修记录列表
    private List<XZHL0520_RepairArray> repair = new ArrayList<XZHL0520_RepairArray>();

    /**
     * 获取是否成功返回
     * @return String success 返回是否成功
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置是否成功
     * @param String success 设置是否成功
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * 获取修记录列表
     * @return String success 返回修记录列表
     */
    public List<XZHL0520_RepairArray> getRepair() {
        return repair;
    }

    /**
     * 设置报修记录列表
     * @param String success 设置报修记录列表
     */
    public void setRepair(List<XZHL0520_RepairArray> repair) {
        this.repair = repair;
    }
}
