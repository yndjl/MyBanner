package com.mgl.mybannerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class MyBannerViewViewPager extends RelativeLayout {
    private ViewPager viewPager;
    private Context context;
    private AdapterViewPager adapter;
    private List<View> data;
    int viewPaddingLeftAndRight = 30;//viewPager中控件左右padding距离
    float pageScaleMin = 0.8f;//页面缩放比列

    public void initMyBannerViewValue(Builder builder) {
        this.context = builder.context;
        this.data = builder.data;
        this.viewPaddingLeftAndRight = builder.viewPaddingLeftAndRight;
        this.pageScaleMin = builder.pageScaleMin;

        startViewPager();
    }

    public MyBannerViewViewPager(Context context) {
        super(context);
    }

    public MyBannerViewViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_banner_view_vp, this, true);
        viewPager = (ViewPager) inflate.findViewById(R.id.vp_my_banner_view_vp);
    }


    /**
     * 初始化ViewPager页面
     */
    public void startViewPager() {
        for (int i = 0; i < data.size(); i++) {
            View e = data.get(i);
            e.setPadding(viewPaddingLeftAndRight, 0, viewPaddingLeftAndRight, 0);
        }
        adapter = new AdapterViewPager(this.data);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        setImgOutScreenScaleAnimation();
    }


    /**
     * 设置ViewPager中屏幕外的页面滑至屏幕中央页面时的放大动画
     */
    private void setImgOutScreenScaleAnimation() {
        ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position < -1 || position > 1) {//屏幕外的设置为原大小的#pageScaleMin#倍
                    page.setScaleY(pageScaleMin);
                } else {
                    float scale = (1 - Math.abs(position)) * (1 - pageScaleMin) + pageScaleMin;
                    page.setScaleY(scale);
                }
            }
        };
        viewPager.setPageTransformer(false, pageTransformer);
    }

    public class Builder {
        Context context;
        List<View> data;
        int viewPaddingLeftAndRight = 30;
        float pageScaleMin = 0.8f;

        public Builder(Context context, List<View> data) {
            this.context = context;
            this.data = data;
        }

        public Builder setViewPaddingLeftAndRight(int viewPaddingLeftAndRight) {
            this.viewPaddingLeftAndRight = viewPaddingLeftAndRight;
            return this;
        }

        public Builder setPageScaleMin(float pageScaleMin) {
            this.pageScaleMin = pageScaleMin;
            return this;
        }

        public void build() {
            initMyBannerViewValue(this);
        }
    }
}
