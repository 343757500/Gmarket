package gmarket.itheima.cn.gmarket.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gmarket.itheima.cn.gmarket.base.BaseFragment;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.protocol.HotProtocol;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.DrawableUtil;
import gmarket.itheima.cn.gmarket.views.FlowLayout;

/**
 * Created by asus on 2017/2/5.
 */

public class HotFragment extends BaseFragment {
    private List<String> datas=new ArrayList<>();
    private FlowLayout mFlowLayout;


    @Override
    public View onSubCreateSuccessView() {
        ScrollView scrollView=new ScrollView(mContext);
        int padding = CommonUtil.dp2px(10);
        //设置内边距
        scrollView.setPadding(padding,padding,padding,padding);
        //设置滚动效果
        scrollView.setVerticalScrollBarEnabled(false);
        //构建一个瀑布流对象
        mFlowLayout = new FlowLayout(mContext);
        //初始化flowLayout的水平和垂直间距
        mFlowLayout.setHorizontalSpacing(CommonUtil.dp2px(6));
        mFlowLayout.setVerticalSpacing(padding);

        //向FlowLayout添加子控件
//        addItemView(flowLayout);
        scrollView.addView(mFlowLayout);
        return scrollView;
    }


    @Override
    protected ResultState onSubLoad() {
        HotProtocol hotProtocol=new HotProtocol();
        datas=hotProtocol.getData(0);
        if(datas!=null){
            CommonUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addItemView();
                }
            });
        }

        //检测状态
        if(datas!=null) {
            if (datas.size() == 0) {
                return ResultState.LOAD_EMPTY;
            } else {
                return ResultState.LOAD_SUCCESS;
            }
        }


        return ResultState.LOAD_ERROR;
    }


    //向流布局容器中添加子控件
    private void addItemView(){
        Random random=new Random();

        for (int i = 0; i < datas.size(); i++) {
            TextView textView=new TextView(mContext);
            textView.setText(datas.get(i));
            int padding=CommonUtil.dp2px(10);
            textView.setPadding(padding,padding,padding,padding);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);


            //设置随机的背景色
            int red=10+random.nextInt(255-30);  // 范围10-235 ,避免极致
            int green=10+random.nextInt(255-30);  // 范围10-235 ,避免极致
            int blue=10+random.nextInt(255-30);  // 范围10-235 ,避免极致
            int argb= Color.argb(255,red,green,blue);
//            tv.setBackgroundColor(rgb);

            //边角的半径
            int radius=CommonUtil.dp2px(6);
            // 通过代码的形式创建Shape 圆角 Drawable
//            Drawable shapeDrawable= DrawableUtil.getShape(argb,radius);
//            tv.setBackgroundDrawable(shapeDrawable);

            //通过代码实现选择器效果  DrawableStateList :状态列表
            //normalDrawable ：默认的图片
            Drawable normalDrawable= DrawableUtil.getShape(argb,radius);
            Drawable pressedDrawable=DrawableUtil.getShape(argb-0x55000000,radius);

            Drawable selectorDrawable=DrawableUtil.getStateListDrawable(normalDrawable,pressedDrawable);

            textView.setClickable(true);
            //API15以下用这个方法
            textView.setBackgroundDrawable(selectorDrawable);

            mFlowLayout.addView(textView);



        }

    }
}
