package gmarket.itheima.cn.gmarket.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/6.
 * /简单的列表适配器的基类
 */

//用泛型就不局限于只能是AppInfo
public abstract class SimpleListBaseAdapter<T> extends BaseAdapter {
    protected List<T> mList=new ArrayList<>();
    //对外一个一个设置list数据的方法
    public void setList(List<T> datas){
        mList=datas;
        //数据集发送改变，更新listview界面
        CommonUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    public List<T> getList(){
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
