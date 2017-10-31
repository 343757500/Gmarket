package gmarket.itheima.cn.gmarket.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;
import gmarket.itheima.cn.gmarket.views.HackyViewPager;
import uk.co.senab.photoview.PhotoView;


public class ScaleImageActivity extends AppCompatActivity {


    @BindView(R.id.vp_scale_image)
    HackyViewPager mViewPager;
    private ArrayList<String> screenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_image);
        screenList = (ArrayList<String>) getIntent().getSerializableExtra("screenList");
        int position = getIntent().getIntExtra("position", 0);
//        ToastUtil.show(position+screenList.get(0)+"新的任务栈");
        ButterKnife.bind(this);

        //ViewPager要设置适配器
        mViewPager.setAdapter(new ScaleAdpater());
        mViewPager.setCurrentItem(position);
    }

    private class ScaleAdpater extends PagerAdapter {
        @Override
        public int getCount() {
            return screenList == null ? 0 : screenList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(CommonUtil.getContext());
            x.image().bind(photoView, ServerAPI.IMAGE_BASE_URL + screenList.get(position));
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
