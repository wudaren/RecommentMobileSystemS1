package com.fbse.recommentmobilesystem.XZHL0220;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XZHL0220_MapData {
    public static List<HashMap<Integer, HashMap<Integer, String>>> datas;

    @SuppressLint("UseSparseArrays")
    public XZHL0220_MapData(int mapNumber) {
        datas = new ArrayList<HashMap<Integer, HashMap<Integer, String>>>();
        for (int i = 0; i < mapNumber; i++) {
            HashMap<Integer, HashMap<Integer, String>> data = new HashMap<Integer, HashMap<Integer, String>>();
            datas.add(data);
        }

    }

    public static List<HashMap<Integer, HashMap<Integer, String>>> getDatas() {
        return datas;
    }

    public static void setDatas(List<HashMap<Integer, HashMap<Integer, String>>> datas) {
        XZHL0220_MapData.datas = datas;
    }

    public static void putMultipleData(int index, int position, HashMap<Integer, String> map) {
        datas.get(index).put(position, map);
    }

    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer, String> answers = new HashMap<Integer, String>();

}
