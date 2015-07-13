package com.fbse.recommentmobilesystem.XZHL0220;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("UseSparseArrays")
public class XZHL0220_MapCheckBox {
    public static List<HashMap<Integer, String>> multipleData;

    public XZHL0220_MapCheckBox(int rows) {
        multipleData = new ArrayList<HashMap<Integer, String>>();
        for (int i = 0; i < rows; i++) {
            HashMap<Integer, String> data = new HashMap<Integer, String>();
            multipleData.add(data);
        }
    }
}
