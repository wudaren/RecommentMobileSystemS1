/**
 * XZHL0520_ListViewAdapter.java 
 * XZHL0520_ListViewAdapter
 * 版本信息 V1.0
 * 创建日期（2014-05-14）
 */
package com.fbse.recommentmobilesystem.XZHL0520;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

/**
 * XZHL0520_ListViewAdapter
 * @author gaozhenchuan 
 * 报修记录适配器
 * 创建日期（2014-05-14）
 * 修改者，修改日期（YYYY-MM-DD），修改内容
 */
public class XZHL0520_ListViewAdapter extends BaseAdapter{

    // 活动上下文
    private Context context;

    // 设备记录列表
    private List<XZHL0520_RepairArray> list;

    // 管理Adapter中的元素
    private ViewHolder viewHolder;

    // 记录触发事件
    private XZHL0520_ListItemClickHelp callback;

    /**
     * 空构造方法
     */
    public XZHL0520_ListViewAdapter(){
    }

    /**
     * 构造方法
     * @param Context context Activity上下文
     * @param List<XZHL0520_RepairArray> list 报修记录基类
     * @param XZHL0520_ListItemClickHelp callback 记录触发事件
     */
    public XZHL0520_ListViewAdapter(Context context,List<XZHL0520_RepairArray> list,XZHL0520_ListItemClickHelp callback){
        this.context  = context;
        this.list = list;
        this.callback = callback;
    }

    /**
     * 获取适配器初始化的总行数
     * @return int 返回页面初始化的总行数
     */
    @Override
    public int getCount() {
        return this.list !=null?this.list.size():0;
    }

    /**
     * 获取当前行数的list值
     * @return Object 获取当前行数的list值
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * 获取选项的列表Id
     * @return long 获取选项的列表Id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据报修记录初始化界面
     * @return View 初始化报修记录界面
     */
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context,R.layout.xzhl0520_record_listview, null);
        }
        viewHolder = new ViewHolder();
        // 界面实例化
        viewHolder.sequence = (TextView) convertView.findViewById(R.id.tv_sequence_xzhl0520);
        viewHolder.pepairDate = (TextView) convertView.findViewById(R.id.tv_pepairDate_xzhl0520);
        viewHolder.stateRepair = (TextView) convertView.findViewById(R.id.tv_stateRepair_xzhl0520);
        viewHolder.operate = (Button) convertView.findViewById(R.id.btn_operate_xzhl0520);
        viewHolder.operate.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        int i=position+1;
        viewHolder.sequence.setText(""+i);
        viewHolder.pepairDate.setText(list.get(position).getDate().substring(0, 10));
        if(list.get(position).getStatus().equals("1")){
            viewHolder.stateRepair.setText("处理中");
        }else{
            viewHolder.stateRepair.setText("处理结束");
        }
        
        viewHolder.operate.setText("查看详细");
        convertView.setTag(viewHolder);
        final View view = convertView;
        final int p = position;
        final int one = viewHolder.operate.getId();
        viewHolder.operate.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                callback.onClick(view, parent, p, one);
            }
            
        });
        
        return convertView;
    }

    /**
     * ViewHolder
     * @author gaozhenchuan 
     * 报修记录适配器封装显示控件
     * 创建日期（2014-05-14）
     * 修改者，修改日期（YYYY-MM-DD），修改内容
     */
    public static class ViewHolder{
        TextView sequence,pepairDate,stateRepair;
        Button operate;
    }

}
