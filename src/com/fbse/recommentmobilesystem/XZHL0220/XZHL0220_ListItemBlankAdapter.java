package com.fbse.recommentmobilesystem.XZHL0220;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_Constants;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 自定义填空题ListView的Adapter
 * 
 * @author F0717
 * 
 */
public class XZHL0220_ListItemBlankAdapter extends BaseAdapter {
    class ViewHolder {
        private EditText et;
    }

    LayoutInflater inflater;
    List<String> options;
    int Itemposition;
    int index;
    Context context;
    CharSequence temp;

    // 重写构造器初始化参数
    public XZHL0220_ListItemBlankAdapter(Context context, List<String> options, ListView listView, int position,
            int index) {
        this.inflater = LayoutInflater.from(context);
        setOptions(options);
        this.Itemposition = position;
        this.index = index;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 轮询Item条目给相应的控件附上数据
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.xzhl0220_blank, null);
            holder = new ViewHolder();
            holder.et = (EditText) convertView.findViewById(R.id.et_edittext_0220);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 遍历保存数据的Map集合,找到当前填空题所对应的数据并赋值
        if (XZHL0220_MapData.datas.get(index).containsKey(Itemposition)) {
            HashMap<Integer, String> blankInfo = XZHL0220_MapData.datas.get(index).get(Itemposition);
            holder.et.setText(blankInfo.get(0));
        }
        final EditText et = holder.et;
        // 给当前编辑框注册监听器
        holder.et.addTextChangedListener(new TextWatcher() {
            @SuppressLint("UseSparseArrays")
            // 重写在text改变后的事件方法
            @Override
            public void afterTextChanged(Editable s) {
                if (temp.length() > 30) {
                    XZHL0210_Utils.showToast2(context, XZHL0210_Constants.YICHAOCHANG, Toast.LENGTH_SHORT);
                    int length = et.getText().length();
                    s.delete(30, length);
                    et.setText(s);
                    et.setSelection(et.getText().length());
                }
                String str = et.getText().toString();
                HashMap<Integer, String> blankInfo = new HashMap<Integer, String>();
                blankInfo.put(position, str);
                XZHL0220_MapData.datas.get(index).put(Itemposition, blankInfo);
                XZHL0220_MapData.answers.put(index * 5 + Itemposition, str);
                Log.v("test", XZHL0220_MapData.datas.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 重写当Text正在编辑时的事件方法
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
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
