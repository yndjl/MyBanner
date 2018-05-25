package com.mgl.mybanner;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mgl.mybannerview.MyBannerViewImageView;
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
        ImageView imageView0 = new ImageView(this);
        imageView0.setImageResource(com.mgl.mybannerview.R.drawable.shouye1);
//        imageView0.setImageResource(com.mgl.mybannerview.R.drawable.a1);
        ImageView imageView1 = new ImageView(this);
        imageView1.setImageResource(com.mgl.mybannerview.R.drawable.shouye3);
//        imageView1.setImageResource(com.mgl.mybannerview.R.drawable.a2);
        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(com.mgl.mybannerview.R.drawable.shouye4);
//        imageView2.setImageResource(com.mgl.mybannerview.R.drawable.a3);
        ImageView imageView3 = new ImageView(this);
        imageView3.setImageResource(com.mgl.mybannerview.R.drawable.shouye5);
//        imageView3.setImageResource(com.mgl.mybannerview.R.drawable.a1);
        data.add(imageView0);
        data.add(imageView1);
        data.add(imageView2);
        data.add(imageView3);
        MyBannerViewViewPager.Builder builder=viewViewPager.new Builder(this,data);
        builder.setPageScaleMin(0.65f).setViewPaddingLeftAndRight(50).build();
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
