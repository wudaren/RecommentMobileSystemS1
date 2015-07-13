package com.fbse.recommentmobilesystem.XZHL0220;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fbse.recommentmobilesystem.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

/**
 * 自定义的单项选择题的Adapter
 * 
 * @author F0717
 * 
 */
public class XZHL0220_ListItemAdapter extends BaseAdapter {
    class ViewHolder {
        private RadioButton rb;
    }

    LayoutInflater inflater;
    List<String> options;
    int Itemposition;
    Context context;
    HashMap<Integer, Integer> selected;
    int index;
    List<RadioButton> btns;

    // 重载构造器初始化参数
    public XZHL0220_ListItemAdapter(Context context, List<String> options, ListView listView, int position, int index) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.options = options;
        setOptions(options);
        this.Itemposition = position;
        this.index = index;
        this.btns = new ArrayList<RadioButton>();
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

    // 轮询Item条目循环给相应控件赋值
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.xzhl0220_radiobutton, null);
            holder = new ViewHolder();
            holder.rb = (RadioButton) convertView.findViewById(R.id.rb_radiobutton_0220);
            btns.add(holder.rb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String info = getItem(position);
        holder.rb.setText(info);
        // 遍历保存数据的Map集合,还原该RadioButton的用户操作状态
        if (XZHL0220_MapData.datas.get(index).containsKey(Itemposition)) {
            HashMap<Integer, String> rb = XZHL0220_MapData.datas.get(index).get(Itemposition);
            if (rb.containsKey(position)) {
                holder.rb.setChecked(true);
            } else {
                holder.rb.setChecked(false);
            }
        }
        // 给Item条目注册监听器
        convertView.setOnClickListener(new OnClickListener() {
            // 给多个单选按钮实现单选的效果
            @SuppressLint("UseSparseArrays")
            @Override
            public void onClick(View v) {
                EditText et = (EditText) parent.getRootView().findViewById(R.id.et_edittext_0220);
                if (et != null) {
                    InputMethodManager imm = (InputMethodManager) context
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                }
                for (int i = 0; i < btns.size(); i++) {
                    btns.get(i).setChecked(false);
                }
                RadioButton rb = (RadioButton) v.findViewById(R.id.rb_radiobutton_0220);
                rb.setChecked(true);
                // 保存该单选按钮的状态
                HashMap<Integer, String> radioInfo = new HashMap<Integer, String>();
                radioInfo.put(position, position + "");
                XZHL0220_MapData.datas.get(index).put(Itemposition, radioInfo);
                char a = (char) ('A' + position);
                XZHL0220_MapData.answers.put(index * 5 + Itemposition, a + "");
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
