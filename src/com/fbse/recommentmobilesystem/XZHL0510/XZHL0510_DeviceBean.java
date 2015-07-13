package com.fbse.recommentmobilesystem.XZHL0510;

import java.util.List;

public class XZHL0510_DeviceBean {
	//是否成功
	private String success;
	//设备数组
	private List<XZHL0510_DeviceArray> device;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<XZHL0510_DeviceArray> getDevice() {
		return device;
	}
	public void setDevice(List<XZHL0510_DeviceArray> device) {
		this.device = device;
	}
}
