package com.mgl.mybanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

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
//        ImageView imageView0 = new ImageView(this);
//        imageView0.setImageResource(com.mgl.mybannerview.R.drawable.shouye1);
////        imageView0.setImageResource(com.mgl.mybannerview.R.drawable.a1);
//        ImageView imageView1 = new ImageView(this);
//        imageView1.setImageResource(com.mgl.mybannerview.R.drawable.shouye3);
////        imageView1.setImageResource(com.mgl.mybannerview.R.drawable.a2);
//        ImageView imageView2 = new ImageView(this);
//        imageView2.setImageResource(com.mgl.mybannerview.R.drawable.shouye4);
////        imageView2.setImageResource(com.mgl.mybannerview.R.drawable.a3);
//        ImageView imageView3 = new ImageView(this);
//        imageView3.setImageResource(com.mgl.mybannerview.R.drawable.shouye5);
////        imageView3.setImageResource(com.mgl.mybannerview.R.drawable.a1);
//
//
//        ImageView imageView4 = new ImageView(this);
//        imageView4.setImageResource(com.mgl.mybannerview.R.drawable.shouye1);
//        ImageView imageView5 = new ImageView(this);
//        imageView5.setImageResource(com.mgl.mybannerview.R.drawable.shouye5);
//        data.add(imageView5);
//        data.add(imageView0);
//        data.add(imageView1);
//        data.add(imageView2);
//        data.add(imageView3);
//        data.add(imageView4);
        int[] data=new int[]{R.drawable.shouye1,R.drawable.shouye3,R.drawable.shouye4,R.drawable.shouye5};
        MyBannerViewViewPager.Builder builder=viewViewPager.new Builder(this,data,MyBannerViewViewPager.BANNER_TYPE_MEIZU);
        builder.setPageScaleMin(0.65f)
                .setViewPaddingLeftAndRight(50)
                .setDelayMillis(4500)
                .setVviewPagerCurrentItem(0)
                .setViewPagerMarginLeftAndRight(150)
                .setOpenCarousel(true)
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
