package com.fbse.recommentmobilesystem.XZHL0220;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fbse.recommentmobilesystem.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 自定义多选题的ListView的Adapter
 * 
 * @author F0717
 * 
 */
public class XZHL0220_ListItemMultipleAdapter extends BaseAdapter {
    class ViewHolder {
        private CheckBox ck;
    }

    LayoutInflater inflater;
    List<String> options;
    Context context;
    int Itemposition;
    int index;
    List<CheckBox> checkBoxes;

    // 重写构造器初始化参数
    public XZHL0220_ListItemMultipleAdapter(Context context, List<String> options, ListView listView, int Itemposition,
            int index) {
        this.inflater = LayoutInflater.from(context);
        setOptions(options);
        this.context = context;
        this.index = index;
        this.Itemposition = Itemposition;
        this.checkBoxes = new ArrayList<CheckBox>();
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public String getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 轮询Item条目给相应的控件附上数据
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.xzhl0220_checkbox, null);
            holder = new ViewHolder();
            holder.ck = (CheckBox) convertView.findViewById(R.id.cb_checkbox_0220);
            checkBoxes.add(holder.ck);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String info = getItem(position);
        holder.ck.setText(info);
        holder.ck.setTag(position);
        Log.v("test", XZHL0220_MapData.datas.toString());
        // 遍历保存数据的Map集合找到该多选按钮对应的数据并还原该多选按钮的状态
        if (XZHL0220_MapData.datas.get(index).containsKey(Itemposition)) {
            HashMap<Integer, String> selected = XZHL0220_MapData.datas.get(index).get(Itemposition);
            Log.v("selected", selected.toString());
            Set<Integer> set = selected.keySet();
            Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                if (position == it.next()) {
                    holder.ck.setChecked(true);
                } else {
                }
            }
            if (position == 0) {
                if (!XZHL0220_MapData.datas.get(index).get(Itemposition).containsKey(position)) {
                    holder.ck.setChecked(false);
                } else {
                    holder.ck.setChecked(true);
                }
            }
        }
        // 给Item条目注册监听器
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) parent.getRootView().findViewById(R.id.et_edittext_0220);
                if (et != null) {
                    InputMethodManager imm = (InputMethodManager) context
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                }
                CheckBox ck = (CheckBox) v.findViewById(R.id.cb_checkbox_0220);
                // 实现一组多选按钮的多选效果
                if (ck.isChecked()) {
                    ck.setChecked(false);
                    XZHL0220_MapCheckBox.multipleData.get(index).remove(position);
                    Log.v("selected", XZHL0220_MapCheckBox.multipleData.get(index).toString());
                    XZHL0220_MapData.putMultipleData(index, Itemposition, XZHL0220_MapCheckBox.multipleData.get(index));
                    if (XZHL0220_MapData.answers.containsKey(index * 5 + Itemposition)) {
                        String temp1 = XZHL0220_MapData.answers.get(index * 5 + Itemposition);
                        char[] c = temp1.toCharArray();
                        String temp2 = "";
                        for (int i = 0; i < c.length; i++) {
                            char h = c[i];
                            if (h == ('A' + position)) {
                                continue;
                            }
                            temp2 += c[i];
                        }
                        XZHL0220_MapData.answers.put(index * 5 + Itemposition, temp2);
                    }
                } else {
                    // 实现一组多选按钮的多选效果
                    ck.setChecked(true);
                    XZHL0220_MapCheckBox.multipleData.get(index).put(position, position + "");
                    Log.v("selected", XZHL0220_MapCheckBox.multipleData.get(index).toString());
                    XZHL0220_MapData.putMultipleData(index, Itemposition, XZHL0220_MapCheckBox.multipleData.get(index));
                    if (XZHL0220_MapData.answers.containsKey(index * 5 + Itemposition)) {
                        char c = (char) ('A' + position);
                        String temp = XZHL0220_MapData.answers.get(index * 5 + Itemposition);
                        temp = temp + c;
                        XZHL0220_MapData.answers.put(index * 5 + Itemposition, temp);
                    } else {
                        char c = (char) ('A' + position);
                        XZHL0220_MapData.answers.put(index * 5 + Itemposition, c + "");
                    }
                    Log.v("test", XZHL0220_MapData.datas.toString());
                }

            }
        });
        return convertView;
    }

    public void setOptions(List<String> options) {
        if (options != null) {
            this.options = options;
        } else {
            this.options = new ArrayList<String>();
        }
    }

}
