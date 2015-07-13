package com.fbse.recommentmobilesystem.XZHL0510;

import java.io.Serializable;

public class XZHL0510_DeviceArray implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    //设备标识
	private String id;
	//设备序列号
	private String serial;
	//设备原始型号
	private String name;
	//设备型号
	private String model;
	//设备名称
	private String brand;
	//投放时间
	private String orgin_serial;
	//品牌
	private String type;
	//设备类型
	private String store;
	//店铺
	private String shops;
	//商家
	private String date;
	//设备状态 1：正常 2：维修中 
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOrgin_serial() {
		return orgin_serial;
	}
	public void setOrgin_serial(String orginSerial) {
		orgin_serial = orginSerial;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getShops() {
		return shops;
	}
	public void setShops(String shops) {
		this.shops = shops;
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
}
