package com.fbse.recommentmobilesystem.XZHL0130;

import java.io.Serializable;

import android.graphics.Bitmap;

public class XZHL0130_Bitmap implements Serializable{
/**
     * 
     */
    private static final long serialVersionUID = -1859646788653870195L;
private Bitmap bitmap;

/**
 * 获取bitmap
 * @return bitmap
 */
public Bitmap getBitmap() {
    return bitmap;
}

/**
 * 设置bitmap
 * @param bitmap bitmap 设置值
 */
public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
}

}
