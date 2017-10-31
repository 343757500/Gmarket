package gmarket.itheima.cn.gmarket.protocol;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import gmarket.itheima.cn.gmarket.base.BaseProtocol;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;

/**
 * Created by asus on 2017/2/6.
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {

    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    protected List<String> parseData(String result) {
        if (!TextUtils.isEmpty(result)) {
            Gson gson = new Gson();
            String[] strings = gson.fromJson(result, String[].class);
            //把数组转化为集合
            List<String> string = Arrays.asList(strings);
            return string;
        }
        return null;
    }

}
