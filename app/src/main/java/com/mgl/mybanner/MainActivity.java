package com.mgl.mybanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mgl.mybannerview.MyBannerViewViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<View> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initBannerByImageView();
        initBannerByViewPager();
    }

    /**
     * ViewPager实现的banner图片轮播
     */
    private void initBannerByViewPager() {
        MyBannerViewViewPager viewViewPager = (MyBannerViewViewPager) findViewById(R.id.bannerVP);
        int[] data = new int[]{R.drawable.shouye1, R.drawable.shouye3, R.drawable.shouye6};
        List<View> dotsList = new ArrayList<>();
        LinearLayout dotsContainer = (LinearLayout) findViewById(R.id.ll_activity_shouye_indicator_container);
        for (int i = 0; i < data.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            if (i != 0)
                layoutParams.setMargins(20, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
//            if (i == 0)
//                imageView.setImageResource(R.drawable.dot_focused);
//            else
//                imageView.setImageResource(R.drawable.dot_normal);
            dotsList.add(imageView);
            dotsContainer.addView(imageView);
        }
        MyBannerViewViewPager.Builder builder = viewViewPager.new Builder(this, data, MyBannerViewViewPager.BANNER_TYPE_MEIZU);
        builder.setPageScaleMin(0.75f)
                .setViewPaddingLeftAndRight(0)
                .setDelayMillis(4500)
                .setVviewPagerCurrentItem(0)
                .setViewPagerMarginLeftAndRight(0)
                .setOpenCarousel(false)
                .setIndicatorExternalUserDefined(dotsList, R.drawable.dot_focused, R.drawable.dot_normal)
                .build();
    }

    /**
     * ImageView实现的banner图片轮播
     */
//    private void initBannerByImageView() {
//        MyBannerViewImageView bannerView = (MyBannerViewImageView) findViewById(R.id.banner);
//        List<Integer> bgs = new ArrayList<>();
//        bgs.add(R.drawable.shouye1);
//        bgs.add(R.drawable.shouye3);
//        bgs.add(R.drawable.shouye4);
//        bgs.add(R.drawable.shouye5);
//        bannerView.setResInts(bgs);
//        bannerView.init();
//    }
}
