package com.fbse.recommentmobilesystem.XZHL0520;

import java.io.Serializable;

/**
 * XZHL0520_RepairArray
 * @author gaozhenchuan 
 * 报修记录基类
 * 创建日期（2014-05-14）
 * 修改者，修改日期（YYYY-MM-DD），修改内容
 */
public class XZHL0520_RepairArray implements Serializable{
    /**
     * XZHL0520_RepairArray的标识
     */
    private static final long serialVersionUID = 379910717507318066L;
    private String id;
    private String repair_no;
    private String device_Id;
    private String device_name;
    private String serial;
    private String name;
    private String orgin_serial;
    private String date;
    private String status;
    private String reason;
    private String result;
    private String comment;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRepair_no() {
        return repair_no;
    }
    public void setRepair_no(String repairNo) {
        repair_no = repairNo;
    }
    public String getDevice_Id() {
        return device_Id;
    }
    public void setDevice_Id(String deviceId) {
        device_Id = deviceId;
    }
    public String getDevice_name() {
        return device_name;
    }
    public void setDevice_name(String deviceName) {
        device_name = deviceName;
    }
    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrgin_serial() {
        return orgin_serial;
    }
    public void setOrgin_serial(String orginSerial) {
        orgin_serial = orginSerial;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
