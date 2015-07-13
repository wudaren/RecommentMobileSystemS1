package com.fbse.recommentmobilesystem.XZHL0410;

import java.util.List;

public class XZHL0410_TaskBean {
	//是否成功
	private String success;
	//门店总指标
	private String shop_point;
	//门店月度指标
	private String month_point;
	//完成的指标
	private String complete_point;
	//月度已完成指标
	private String month_complete_point;
	//任务数组
	private List<XZHL0410_TaskArray> task;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getShop_point() {
		return shop_point;
	}
	public void setShop_point(String shopPoint) {
		shop_point = shopPoint;
	}
	public String getMonth_point() {
		return month_point;
	}
	public void setMonth_point(String monthPoint) {
		month_point = monthPoint;
	}
	public String getComplete_point() {
		return complete_point;
	}
	public void setComplete_point(String completePoint) {
		complete_point = completePoint;
	}
	public String getMonth_complete_point() {
		return month_complete_point;
	}
	public void setMonth_complete_point(String monthCompletePoint) {
		month_complete_point = monthCompletePoint;
	}
	public List<XZHL0410_TaskArray> getTask() {
		return task;
	}
	public void setTask(List<XZHL0410_TaskArray> task) {
		this.task = task;
	}
}
