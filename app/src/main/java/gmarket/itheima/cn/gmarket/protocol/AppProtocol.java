package gmarket.itheima.cn.gmarket.protocol;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gmarket.itheima.cn.gmarket.base.BaseProtocol;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.model.net.HomeJsonBean;

/**
 * Created by asus on 2017/2/6.
 */

public class AppProtocol extends BaseProtocol<List<AppInfo>> {

    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    protected List<AppInfo> parseData(String result) {
        Gson gson=new Gson();
       // HomeJsonBean homeJsonBean = gson.fromJson(result, HomeJsonBean.class);
        AppInfo[] appInfos = gson.fromJson(result, AppInfo[].class);
        //把数组转化为集合
        List<AppInfo> applist = Arrays.asList(appInfos);
        return applist;
    }

}
