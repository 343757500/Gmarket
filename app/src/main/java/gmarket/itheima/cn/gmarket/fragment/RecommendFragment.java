package gmarket.itheima.cn.gmarket.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gmarket.itheima.cn.gmarket.base.BaseFragment;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.protocol.RecommendProtocol;
import gmarket.itheima.cn.gmarket.randomlayout.StellarMap;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/5.
 */

public class RecommendFragment extends BaseFragment{
    private List<String> datas=new ArrayList<>();
    private StellarMap mStellarMap;
    private StellAdapter mStellAdapter;
    private int curremtPosition=0;


    //加载本片段的数据
    @Override
    protected ResultState onSubLoad() {
        RecommendProtocol recommendProtocol=new RecommendProtocol();
         datas =  recommendProtocol.getData(100);
        CommonUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (datas!=null){
                    mStellarMap.setAdapter(mStellAdapter);
                }
            }
        });


        if(datas!=null){

            if(datas.size()==0){
              return ResultState.LOAD_EMPTY;
            }else {
                return ResultState.LOAD_SUCCESS;
            }
        }

        return ResultState.LOAD_ERROR;
    }

    @Override
    public View onSubCreateSuccessView() {
        //星空图视图 ，继承ViewGroup
        if(mStellarMap==null)
        mStellarMap = new StellarMap(mContext);
        //设置适配器
        if(mStellAdapter==null)
         mStellAdapter = new StellAdapter();
        //如果在这里就设置适配器,datas此时并没有赋值,所以会为NUll

            //mStellarMap.setAdapter(mStellAdapter);


        int padding = CommonUtil.dp2px(10);
        //设置内边距
        mStellarMap.setInnerPadding(padding,padding,padding,padding);
        //设置组的起始动画
        mStellarMap.setGroup(2,true);
        ///** 设置隐藏组和显示组的x和y的规则 */
        mStellarMap.setRegularity(12,8);

        CommonUtil.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStellarMap.zoomIn();
            }
        },600);

        return mStellarMap;
    }

    public class StellAdapter implements StellarMap.Adapter,View.OnClickListener{
        Random random=null;
        public  StellAdapter(){
            random=new Random();
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            return 20;
        }

        @Override
        public View getView(int group, final int position, View convertView) {

            final TextView textView=new TextView(mContext);
            textView.setText(datas.get(position));
            //设置随机颜色
            int red=10+random.nextInt(255-30);  // 范围10-235 ,避免极致
            int green=10+random.nextInt(255-30);  // 范围10-235 ,避免极致
            int blue=10+random.nextInt(255-30);  // 范围10-235 ,避免极致
            int rgb= Color.rgb(red,green,blue);
            textView.setTextColor(rgb);
            //随机设置大小
            int fontSize = 10 + random.nextInt(20);
            textView.setTextSize(fontSize);
            //如果这里拼命New 的话会耗费资源
            /*textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,datas.get(position),Toast.LENGTH_SHORT).show();
                }
            });*/

            textView.setOnClickListener(this);

            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }

        @Override
        public void onClick(View v) {
            //这里得到的只是最后一个值
            //Toast.makeText(CommonUtil.getContext(),curremtPosition,Toast.LENGTH_SHORT).show();

            //方法一
            TextView textView= (TextView) v;
            String name = textView.getText().toString();
            Toast.makeText(CommonUtil.getContext(),name,Toast.LENGTH_SHORT).show();
        }
    }
}
