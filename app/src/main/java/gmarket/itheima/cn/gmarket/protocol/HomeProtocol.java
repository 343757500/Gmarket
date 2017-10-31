package gmarket.itheima.cn.gmarket.protocol;

import com.google.gson.Gson;

import gmarket.itheima.cn.gmarket.base.BaseProtocol;
import gmarket.itheima.cn.gmarket.model.net.HomeJsonBean;

/**
 * Created by asus on 2017/2/6.
 */

public class HomeProtocol extends BaseProtocol<HomeJsonBean> {

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    protected HomeJsonBean parseData(String result) {
        Gson gson=new Gson();
        HomeJsonBean homeJsonBean = gson.fromJson(result, HomeJsonBean.class);
        return homeJsonBean;
    }

}
