package gmarket.itheima.cn.gmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.activity.DetailActivity;
import gmarket.itheima.cn.gmarket.adapter.HomeAdapter;
import gmarket.itheima.cn.gmarket.base.BaseFragment;

import gmarket.itheima.cn.gmarket.holder.HomeHeaderHolder;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.model.net.HomeJsonBean;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.protocol.HomeProtocol;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/5.
 */

public class HomeFragment extends BaseFragment {
    private List<AppInfo> datas=new ArrayList<>();
    private HomeAdapter mHomeAdapter;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private List<String> picture=new ArrayList<>();
    private HomeHeaderHolder mHomeHeaderHolder;
    private ListView mListView;



    //1. 创建本片段的成功界面方法
    @Override
    public View onSubCreateSuccessView() {
       // ListView listView =new ListView(mContext);
        //这是有下拉刷新和加载更多的页面
        View view = CommonUtil.inflate(R.layout.layout_listview_refresh);
        //CommonPullToRefresh 框架
        mListView = (ListView)view.findViewById(R.id.test_list_view);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.test_list_view_frame);


        //准备适配器,设置适配器
        mHomeAdapter = new HomeAdapter();
        mListView.setAdapter(mHomeAdapter);

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
                Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                if(mContentPage!=null){
                    mContentPage.loadDataOrRefresh();
                   // mHomeAdapter.notifyDataSetChanged();
                }
            }
        });

        //轮播图添加到头部
        if(mListView.getHeaderViewsCount()==0){
            mHomeHeaderHolder = new HomeHeaderHolder(mListView);
            mListView.addHeaderView(mHomeHeaderHolder.getRootView());
            mHomeHeaderHolder.bindData(picture);
        }

        //1.6 对列表条目设置点击监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //取得当前条目的数据 ,因为ListView添加了头部，所以position会在集合数据中+1
                int dataPostion = position - mListView.getHeaderViewsCount();
                AppInfo appInfo = datas.get(dataPostion);
                Intent intent=new Intent(CommonUtil.getContext(),DetailActivity.class);
                //附加对象数据 ,对应要传递对象，需要序列化
                intent.putExtra("appInfo",appInfo);

                startActivity(intent);
            }
        });



        /***************与数据相关**********/
        setupData();


        return view;
    }
    //设置数据
    private void setupData(){
        if(mHomeAdapter!=null){
            mHomeAdapter.setList(datas);

        }
    }






    //2.加载本片段数据的方法
    @Override
    protected ResultState onSubLoad() {
        //在网络中获取HomeProtocol数据
        HomeProtocol homeProtocol=new HomeProtocol();



        /**依据刷新的类型分别处理数据
         * 1. 下拉刷新 ： 集合的数据应该是最新的数据，且是最多20条，index 索引号应该是 0
         * 2. 加载更多 ： 在当前的数据集合中再添加加载更多获取到的数据 ， 索引号要逐步增加
         */

        // 2.1 分离数据，取得要展示的数据
        //index ：页面 ，默认为0 ，取第0个页面的数据

        // 2.1 分离数据，取得要展示的数据
        //index=0(homelist0) ,20(homelist2),40(homelist3),60(homelist1) ,80(homelist2),100
        //datas.size()/20%3无限循环  所以拼命叠加
        HomeJsonBean homeJsonBean =  homeProtocol.getData(datas.size());
        if(homeJsonBean!=null) {

            //如果不是加载更多那就是下拉刷新,20条以后的数据全部清除恢复最原始

            List<AppInfo> appInfos = homeJsonBean.getList();

            if(!mPtrClassicFrameLayout.isLoadingMore()){
                //目前是下拉刷新操作
                datas.clear();//清除集合
                //picture.clear();
            }



            //这样就是把网络获取到的20条数据加在datas集合中,这样才会有数据刷新出来
            datas.addAll(appInfos);
            //????????????????????????????????????????????????????????????????????
            picture.addAll(homeJsonBean.getPicture()) ;


            CommonUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mHomeAdapter.setList(datas);
                    //mHomeAdapter.notifyDataSetChanged();
                    mHomeHeaderHolder.bindData(picture);


                   //下拉刷新结束 ，隐藏下拉刷新的界面
                    mPtrClassicFrameLayout.refreshComplete();
                    //加载更多完成，隐藏加载更多界面
                    mPtrClassicFrameLayout.loadMoreComplete(true);
                }
            });
        }

        if(homeJsonBean==null){
            return ResultState.LOAD_ERROR;
        }else{
            int size = homeJsonBean.getList().size();
            if(size>0){
                return ResultState.LOAD_SUCCESS;
            }else{
                return ResultState.LOAD_EMPTY;
            }
        }

    }





}
