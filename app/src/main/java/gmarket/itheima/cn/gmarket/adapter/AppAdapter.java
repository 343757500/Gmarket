package gmarket.itheima.cn.gmarket.adapter;

import android.view.ViewGroup;

import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.base.GMBaseListAdapter;
import gmarket.itheima.cn.gmarket.holder.AppHolder;
import gmarket.itheima.cn.gmarket.holder.HomeHolder;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;

/**
 * Created by asus on 2017/2/5.
 */

public class AppAdapter extends GMBaseListAdapter<AppInfo> {
    @Override
    protected BaseHolder getViewHolder(ViewGroup parent) {
       // HomeHolder homeHolder=;
        return new AppHolder(parent);
    }


}
