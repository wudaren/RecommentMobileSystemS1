package com.fbse.recommentmobilesystem.XZHL0220;

import java.util.ArrayList;
import java.util.List;

import com.fbse.recommentmobilesystem.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 自定义问卷题目ListView的Adapter、
 * 
 * @author F0717
 * 
 */

public class XZHL0220_QuestionAdapter extends BaseAdapter {
    class ViewHolder {
        private TextView title;
        private ListView listItem;

    }

    private List<XZHL0220_Choice> questions;
    private LayoutInflater inflater;
    private Context context;
    private int index;

    // 重写构造器，初始化相应的参数
    public XZHL0220_QuestionAdapter(final Context context, List<XZHL0220_Choice> questions, final ListView detail,
            int index) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.index = index;
        this.setQuestions(questions);
    }

    // 定义当ListView里的数据改变时更新界面的方法
    public void changeData(List<XZHL0220_Choice> questions, int index) {
        this.setQuestions(questions);
        this.setIndex(index);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public XZHL0220_Choice getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getItemKey(int position) {
        return getItem(position).getKey();
    }

    // 轮询Item条目给相应的控件附上数据
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.xzhl0220_singlequestion, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_title_0220);
            holder.listItem = (ListView) convertView.findViewById(R.id.singlelist_0220);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        XZHL0220_Choice choice = getItem(position);
        List<String> options = choice.getOptions();
        // 给每个Item的题目的标题赋值
        holder.title.setText(choice.getTitle());
        if (getItemKey(position).equals("single")) {
            // 当是单选题时填充对应的单选题的布局
            XZHL0220_ListItemAdapter la = new XZHL0220_ListItemAdapter(context, options, holder.listItem, position,
                    index);
            holder.listItem.setAdapter(la);
            XZHL0220_Utils.setListViewHeightBasedOnChildren(holder.listItem);
        } else if (getItemKey(position).equals("multiple")) {
            // 当是多选题时填充对应的多选题布局
            XZHL0220_ListItemMultipleAdapter la = new XZHL0220_ListItemMultipleAdapter(context, options,
                    holder.listItem, position, index);
            holder.listItem.setAdapter(la);
            XZHL0220_Utils.setListViewHeightBasedOnChildren(holder.listItem);
        } else if (getItemKey(position).equals("blank")) {
            // 当是填空题时填充对应的填空题布局
            XZHL0220_ListItemBlankAdapter la = new XZHL0220_ListItemBlankAdapter(context, options, holder.listItem,
                    position, index);
            holder.listItem.setAdapter(la);
            XZHL0220_Utils.setListViewHeightBasedOnChildren(holder.listItem);
        }
        return convertView;
    }

    // 改变传入List数据时改变Adapter的数据
    public void setQuestions(List<XZHL0220_Choice> list) {
        if (list != null) {
            this.questions = list;
        } else {
            this.questions = new ArrayList<XZHL0220_Choice>();
        }

    }

    public void setIndex(int index) {
        this.index = index;
    }

}
