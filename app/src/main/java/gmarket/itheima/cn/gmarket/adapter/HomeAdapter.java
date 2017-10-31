package gmarket.itheima.cn.gmarket.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.base.GMBaseListAdapter;
import gmarket.itheima.cn.gmarket.holder.HomeHolder;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/5.
 */

public class HomeAdapter extends GMBaseListAdapter<AppInfo> {
    @Override
    protected BaseHolder getViewHolder(ViewGroup parent) {
       // HomeHolder homeHolder=;
        return new HomeHolder(parent);
    }

   /* //把getView（）方法公共能实现的操作，在基类中实现
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder=null;
        if (convertView==null){
            viewHoder=new ViewHoder();

            //convertView = CommonUtil.inflate(R.layout.item_home);
            convertView= CommonUtil.getInflater().inflate(R.layout.item_home,parent,false);
            viewHoder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHoder);
        }else{
            viewHoder= (ViewHoder) convertView.getTag();
        }

        //取得当前列表条目要装配的数据
        AppInfo appInfo = mList.get(position);
        viewHoder.mTextView.setText(appInfo.getName());

        return convertView;
    }

    public static class ViewHoder{
        TextView mTextView;
    }*/
}
