/**
 * 数据库表questionnaire的对应类
 */
package com.fbse.recommentmobilesystem.XZHL0210;

public class XZHL0210_Questioniare {
    // 问卷Id
    private String wenjuanId;

    // 问卷名称
    private String wenjuanName;

    // 问卷积分
    private String wenjuanPoint;

    // 任务回数
    private String renwuCount;

    // 删除标志位
    private String delFlg;

    // 更新者
    private String updateUser;

    // 更新日
    private String updateTime;

    // 问卷已回答回数
    private String wanchengCount;

    /**
     * get set 方法
     */

    public String getWenjuanId() {
        return wenjuanId;
    }

    public void setWenjuanId(String wenjuanId) {
        this.wenjuanId = wenjuanId;
    }

    public String getWenjuanName() {
        return wenjuanName;
    }

    public void setWenjuanName(String wenjuanName) {
        this.wenjuanName = wenjuanName;
    }

    public String getWenjuanPoint() {
        return wenjuanPoint;
    }

    public void setWenjuanPoint(String wenjuanPoint) {
        this.wenjuanPoint = wenjuanPoint;
    }

    public String getRenwuCount() {
        return renwuCount;
    }

    public void setRenwuCount(String renwuCount) {
        this.renwuCount = renwuCount;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWanchengCount() {
        return wanchengCount;
    }

    public void setWanchengCount(String wanchengCount) {
        this.wanchengCount = wanchengCount;
    }

    @Override
    public String toString() {
        return "XZHL0210 [delFlg=" + delFlg + ", renwuCount=" + renwuCount + ", updateTime=" + updateTime
                + ", updateUser=" + updateUser + ", wanchengCount=" + wanchengCount + ", wenjuanId=" + wenjuanId
                + ", wenjuanName=" + wenjuanName + ", wenjuanPoint=" + wenjuanPoint + "]";
    }

}
