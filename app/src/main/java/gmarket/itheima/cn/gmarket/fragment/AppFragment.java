package gmarket.itheima.cn.gmarket.fragment;


import java.util.List;


import gmarket.itheima.cn.gmarket.adapter.AppAdapter;
import gmarket.itheima.cn.gmarket.base.BaseListFreshFragment;
import gmarket.itheima.cn.gmarket.base.BaseProtocol;
import gmarket.itheima.cn.gmarket.base.GMBaseListAdapter;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.protocol.AppProtocol;

/**
 * Created by asus on 2017/2/5.
 */

public class AppFragment extends BaseListFreshFragment<AppInfo> {


    @Override
    public GMBaseListAdapter getAdapter() {
        AppAdapter appAdapter=new AppAdapter();
        return appAdapter;
    }

    @Override
    protected BaseProtocol<List<AppInfo>> getProtocol() {
        AppProtocol appProtocol=new AppProtocol();
        return appProtocol;
    }
}
