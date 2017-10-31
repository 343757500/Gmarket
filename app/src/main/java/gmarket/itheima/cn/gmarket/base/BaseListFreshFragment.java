package gmarket.itheima.cn.gmarket.base;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.adapter.AppAdapter;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.protocol.AppProtocol;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/5.
 */

public abstract class BaseListFreshFragment<T> extends BaseFragment {
    private List<T> datas=new ArrayList<>();

    //private AppAdapter mAppAdapter;
    private GMBaseListAdapter<T> adapter;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private BaseProtocol<List<T>> protocol;


    //1. 创建本片段的成功界面方法
    @Override
    public View onSubCreateSuccessView() {
       // ListView listView =new ListView(mContext);
        //这是有下拉刷新和加载更多的页面
        View view = CommonUtil.inflate(getLayoutResId());
        //CommonPullToRefresh 框架
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.test_list_view_frame);
       ListView listView = (ListView)view.findViewById(R.id.test_list_view);

        //准备适配器,设置适配器
        adapter = getAdapter();
        listView.setAdapter(adapter);

        //1.3 对ListView设置下拉刷新的监听
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Toast.makeText(mContext,"下拉刷新",Toast.LENGTH_SHORT).show();
                if(mContentPage!=null){
                    //加载网络中的数据
                    mContentPage.loadDataOrRefresh();
                }
            }
        });

        //1.4 对listView设置加载更多方法
        mPtrClassicFrameLayout.setLoadMoreEnable(true);
        mPtrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {


                CommonUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                        if(mContentPage!=null){
                            mContentPage.loadDataOrRefresh();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        });



        /***************与数据相关**********/
        setupData();


        return view;
    }
    //设置数据
    private void setupData(){
        if(adapter!=null){
            adapter.setList(datas);
        }
    }



    //2.加载本片段数据的方法
    @Override
    protected ResultState onSubLoad() {
        //在网络中获取AppProtocol数据
        protocol= getProtocol();

        /**依据刷新的类型分别处理数据
         * 1. 下拉刷新 ： 集合的数据应该是最新的数据，且是最多20条，index 索引号应该是 0
         * 2. 加载更多 ： 在当前的数据集合中再添加加载更多获取到的数据 ， 索引号要逐步增加
         */

        //如果不是加载更多那就是下拉刷新,20条以后的数据全部清除恢复最原始
        if(!mPtrClassicFrameLayout.isLoadingMore()){
            //目前是下拉刷新操作
            datas.clear();//清除集合
        }

        // 2.1 分离数据，取得要展示的数据
        //index ：页面 ，默认为0 ，取第0个页面的数据

        // 2.1 分离数据，取得要展示的数据
        //index=0(homelist0) ,20(homelist2),40(homelist3),60(homelist1) ,80(homelist2),100
        //datas.size()/20%3无限循环  所以拼命叠加
        List<T> lists = protocol.getData(datas.size());
        if(lists!=null) {
           // List<AppInfo> appInfos = appinfos.getList();
            //这样就是把网络获取到的20条数据加在datas集合中,这样才会有数据刷新出来
            datas.addAll(lists);


            CommonUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setList(datas);
                   // adapter.notifyDataSetChanged();
                   //下拉刷新结束 ，隐藏下拉刷新的界面
                    mPtrClassicFrameLayout.refreshComplete();
                    //加载更多完成，隐藏加载更多界面
                    mPtrClassicFrameLayout.loadMoreComplete(true);
                }
            });
        }

        if(datas==null){
            return ResultState.LOAD_ERROR;
        }else{
            int size = datas.size();
            if(size>0){
                return ResultState.LOAD_SUCCESS;
            }else{
                return ResultState.LOAD_EMPTY;
            }
        }

    }

    //由于父类中不能决定子类界面的适配器，所以抽象出去，由子类来实现
    public abstract GMBaseListAdapter<T> getAdapter() ;

    //取得成功界面的xml布局资源id, 钩子方法 ，hook方法，把子类的方法钩处理，在模板设计模式中常用
    public int getLayoutResId() {
        return R.layout.layout_listview_refresh;
    }


    //由于父类中不能决定子类如何访问网络，所以抽象出去，由子类来实现
    protected abstract BaseProtocol<List<T>> getProtocol();
}
