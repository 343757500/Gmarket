package gmarket.itheima.cn.gmarket.base;

import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.IOUtils;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus on 2017/2/6.
 * 所有访问网络的基类
 */

public abstract class BaseProtocol<T> {
    /**取数据的实现步骤分析
     *  1. 优先从本地缓存取数据，假如缓存中没数据，或者数据失效，则返回null，从网络中取数据
     *  2. 从网络加载最新的数据，假如数据不为空，则把从网络获取的数据缓存到本地
     *  3. 解析从缓存或者网络取得的数据： json串， xml串 ， 转化成相应的java bean
     *  4. 返回取得的java bean
     */

    //1. 取得数据  index :页码    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public T getData(int index){
        String result=null;
        //1.1 优先从本地缓存取数据，假如缓存中没数据，或者数据失效，则返回null，从网络中取数据
        String cacheData=getDataFromLocal(index);
        if(!TextUtils.isEmpty(cacheData)){
           // Log.e("heima12","本地中获取");
            result=cacheData;
        }else{
            //1.2 从网络加载最新的数据，假如数据不为空，则把从网络获取的数据存放到本地
          //  Log.e("heima12","网络中获取");
            result=getDataFormNet(index);
        }
        //1.3 解析从缓存或者网络取得的数据： json串， xml串 ， 转化成相应的java bean
        T t = parseData(result);
        return t;
    }



    //从网络加载数据

    /*    1、网络请求路径分析  ： get请求

        1. Http标准的请求路径：
         - 开源中国服务端url  http://www.oschina.net:80/action/api/news_list?pageIndex=0&catalog=1&pageSize=20
        - 谷歌市场服务端 url： http://10.0.2.2:8080/GooglePlayServer/home?index=0&name=image.jpg
        2. 请求网络的相同部分
        - 主机名 ：http://10.0.2.2   http://www.oschina.net
                -  端口号  8080      80 端口
        3. 请求网络的不同部分：
                1. 请求页面不同
                   home  \ subject  \ detail  \ app   \ game
                2. 相同的请求页面，但是获取的数据的位置不同，页码是不同的
                pagerIndex\index 页码请求参数

                3. 请求参数不同
                name 、packageName
        */
    private String getDataFormNet(int index){

        //谷歌市场服务端采用http的get请求访问 ，把功能的url数据放在基类，把不同的url相关数据由子类来实现
        //http://10.0.2.2:8080/GooglePlayServer/home?index=0&name=image.jpg
        String url = ServerAPI.BASE_URL + getKey() + "?index=" + index + getParams();
        // 2. 采用okhttp访问网络
        //2.1 构建一个okhttpClient
        OkHttpClient okHttpClient=new OkHttpClient();
        //2.2 准备一个request请求
        Request request=new Request.Builder().url(url).build();
        //2.3 执行请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()) {

                String result = response.body().string();
                if (!TextUtils.isEmpty(result)) {
                    saveDataToLocal(index, result);
                    return result;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //把网络获取的最新数据存入本地缓存
    /**把网络获取的最新数据存入本地缓存
     *  1. 以什么形式缓存数据： sqlite、SharedPreferences、以file的形式存数据
     *     用file缓存数据
     *  2. 存放在哪里，文件名是什么
     *     - 存放位置
     *       1. 外部存储： sdcard
     *         /mnt/sdcard/
     *         - 外部存储的私有路径中
     *         /mnt/sdcard/android/data/包名/files   cache  等  taobao  ：图片
     *       2. 手机内部存储
     *         /data/data/包名/ files   cache  shared_refs  databases
     *     - 确定文件名
     *       1. url1 ： http://localhost:8080/GooglePlayServer/home?index=0&name=image.jpg
     *                 对应的文件名1： filename=md5.getString(" http://localhost:8080/GooglePlayServer/home?index=0 ")
     *                  对应的文件名2  filename=home?index=0&name=image.jpg
     *                                  = home0
     *                                  getKey()+index+getParams()
     *
     *
     *  3.存什么内容
     *      1. 第一行存数据的有效时间
     *     2. 从第二行开始存从网络中获取的最新的json字符串数据
     *  4. 数据的有效性怎么解决
     *      数据30分钟之内有效
     *        - 方案1 ：
     *          保存当前的时间+ 30分钟
     *           比如： 2017.2.6 9:00+30= 9:30
     *        - 方案2 ：
     *          取文件的最后修改时间，再与当前访问的时间比较 ，确定数据的有效性
     */
    private void saveDataToLocal(int index,String result) {
        Log.e("heima12","网络中获取");
        // CommonUtil.getContext().getExternalCacheDir() :外部存储的私有缓存路径
        File file=new File(CommonUtil.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),getKey()+index+getParams());
        BufferedWriter bufferedWriter=null;
        try {
            bufferedWriter=new BufferedWriter(new FileWriter(file));
            //现在的时间加上30分钟存进内部储存
            long invalidTime = System.currentTimeMillis() + 30 * 60 * 1000;
            //第一行存有效时间  30分钟之内有效
            bufferedWriter.write(invalidTime+"");
            bufferedWriter.newLine();
            //第二行存 网络获取的数据
            bufferedWriter.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(bufferedWriter);
        }


    }
    //从本地缓存获取数据
    private String getDataFromLocal(int index) {
        File file=new File(CommonUtil.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                getKey()+index+getParams());
        //判断文件是否存在
        if(file.exists()){
            Log.e("heima12","bendihuanchun");
            BufferedReader reader=null;
            try {
                reader=new BufferedReader(new FileReader(file));
                //读该数据是否有效,即读第一行数据
                long invalidTime= Long.valueOf(reader.readLine().trim().toString());
                if(System.currentTimeMillis()<invalidTime){
                    //数据有效，则开始读取缓存的json数据
                    StringBuilder sb=new StringBuilder();
                    String tempLine=null;
                    while ((tempLine=reader.readLine())!=null){
                        sb.append(tempLine);
                    }
                    return  sb.toString();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }

        return  null;
    }


    //解析从缓存或者网络获取的json数据,由于每一个功能片段模块的服务端json数据都有差异，不能在基类中实现json的解析，应该抽象出去，由子类来实现
    protected abstract T parseData(String result);



    ///取得请求的页面，由于每个功能片段的url访问的页面都不同，所以抽象出去，由子类来实现
    protected abstract String getKey();
    //取得请求参数，由于每个功能片段的请求参数都有差异，所以抽象出去，由子类来实现
    protected abstract String getParams();
}
