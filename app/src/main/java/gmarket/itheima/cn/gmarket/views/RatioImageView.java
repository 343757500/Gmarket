package gmarket.itheima.cn.gmarket.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by asus on 2017/2/10.
 */

public class RatioImageView extends ImageView {
    //没有这句的话用不了这个自定义控件的属性和功能,也要在XML文件中设置
    private  static  final  String NAMESPACE="http://schemas.android.com/apk/res-auto";
    private float mRatio;


    public RatioImageView(Context context) {
        super(context);
    }
    //xml中会调用该构造方法,也就是在布局文件中
    public RatioImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
        // 取得属性值， NAMESPACE：命名空间  ratio： 属性名   defvalues：缺省值,当取不到的时候哦
        mRatio = attrs.getAttributeFloatValue(NAMESPACE, "ratio", 1.0f);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
         mRatio = attrs.getAttributeFloatValue(NAMESPACE, "ratio", 1.0f);
    }

    /**测量方法
     * 测量规格：  32位   前2位  模式    后30位  ，具体的值
     *   MeasureSpec.AT_MOST ：最多多少像素
     *  easureSpec.UNSPECIFIED  ：未确定 比如 ListView  ，当适配器装配好条目数据才好确定
     * MeasureSpec.EXACTLY :精确的值，具体的像素
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //取得图片的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //依据比例获取高度,该高度的图片是重新设置的高度
        int height = (int) (width/mRatio);

        //替换heightMeasureSpec   MeasureSpec.getMode();//取得模式
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);;


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
