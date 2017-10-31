package gmarket.itheima.cn.gmarket.protocol;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import gmarket.itheima.cn.gmarket.base.BaseProtocol;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.model.net.SubjectInfo;

/**
 * Created by asus on 2017/2/6.
 */

public class SubjuctProtocol extends BaseProtocol<List<SubjectInfo>> {

    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    protected List<SubjectInfo> parseData(String result) {
        if (!TextUtils.isEmpty(result)) {
            Gson gson = new Gson();
            SubjectInfo[] subjectInfos = gson.fromJson(result, SubjectInfo[].class);
            //把数组转化为集合
            List<SubjectInfo> subjectlist = Arrays.asList(subjectInfos);
            return subjectlist;
        }
        return null;
    }

}
