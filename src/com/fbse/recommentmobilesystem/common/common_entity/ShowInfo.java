package com.fbse.recommentmobilesystem.common.common_entity;

import java.io.Serializable;
import java.util.Arrays;

public class ShowInfo implements Serializable {
    private static final long serialVersionUID = 4940599943744685628L;
    private String VipName;
    private String CardTypeName;
    private String Mobile;
    private String ImageUrl;
    private String[] ConTrendInfor;
    private String[] PromoInfor;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getVipName() {
        return VipName;
    }

    public void setVipName(String vipName) {
        VipName = vipName;
    }

    public String getCardTypeName() {
        return CardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        CardTypeName = cardTypeName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String[] getConTrendInfor() {
        return ConTrendInfor;
    }

    public void setConTrendInfor(String[] conTrendInfor) {
        ConTrendInfor = conTrendInfor;
    }

    @Override
    public String toString() {
        return "ShowInfo [VipName=" + VipName + ", CardTypeName="
                + CardTypeName + ", Mobile=" + Mobile + ", ImageUrl="
                + ImageUrl + ", ConTrendInfor="
                + Arrays.toString(ConTrendInfor) + ", PromoInfor="
                + Arrays.toString(PromoInfor) + "]";
    }

    public String[] getPromoInfor() {
        return PromoInfor;
    }

    public void setPromoInfor(String[] promoInfor) {
        PromoInfor = promoInfor;
    }
}
