package com.mgl.mybannerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class MyBannerViewViewPager extends RelativeLayout {
    public static final int BANNER_TYPE_MEIZU = 1;//开启魅族模式
    public static final int BANNER_TYPE_NOMOL = 2;//普通模式
    private ViewPager viewPager;
    private Context context;
    private AdapterViewPager adapter;
    private List<View> data = new ArrayList<>();//view数据
    int childViewPaddingLeftAndRight = 30;//viewPager中控件左右padding距离
    float pageScaleMin = 0.8f;//页面缩放比列
    int viewPagerMarginLeftAndRight = 150;//设置viewPager距离父控件的margin距离
    int bannerType = BANNER_TYPE_NOMOL;
    LayoutParams viewPagerLayoutParams;
    int delayMillis = 4000;//轮播的延时时间
    int viewPagerCurrentItem = 1;//初始化轮播显示View的位置（data中数据的真实位置 ）
    private Boolean openCarousel = false;//轮播模式
    int[] dataRes;//图片来源---资源ID
    String[] dataUrl;//图片来源---网络url地址
    List<View> dotsList = null;//外部用户自己定义好了指示器，传递自己的指示器view集合进来，用于在本控件内部控制轮播时指示器的切换
    Integer dotsFocusRes = -1, dotsNormalRes = -1;//用户指定指示器图片资源

    public void initMyBannerViewValue(Builder builder) {
        this.context = builder.context;
        this.dataRes = builder.dataRes;
        this.dataUrl = builder.dataUrl;
        this.childViewPaddingLeftAndRight = builder.childViewPaddingLeftAndRight;
        this.pageScaleMin = builder.pageScaleMin;
        this.viewPagerMarginLeftAndRight = builder.viewPagerMarginLeftAndRight;
        this.bannerType = builder.bannerType;
        this.delayMillis = builder.delayMillis;
        this.viewPagerCurrentItem = builder.viewPagerCurrentItem;
        this.openCarousel = builder.openCarousel;
        this.dotsList = builder.dotsList;
        this.dotsFocusRes = builder.dotsFocusRes;
        this.dotsNormalRes = builder.dotsNormalRes;
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
        if (dataRes != null) {//使用apk图片资源id来初始化data数据
            for (int i = 0; i < dataRes.length + 2; i++) {//设置viewPager中page的左右padding，使效果中两个页面间可以留白
                ImageView imageView = new ImageView(context);
                if (i == 0) {
                    imageView.setImageResource(dataRes[dataRes.length - 1]);
                    data.add(imageView);
                } else if (i == dataRes.length + 1) {
                    imageView.setImageResource(dataRes[0]);
                    data.add(imageView);
                } else {
                    imageView.setImageResource(dataRes[i - 1]);
                    data.add(imageView);
                }
                imageView.setPadding(childViewPaddingLeftAndRight, 0, childViewPaddingLeftAndRight, 0);
            }
        }
        if (dataUrl != null) {

        }


        if (bannerType == 1) {//设置为魅族模式时，设置viewPager的margin距离,以便开启魅族样式
            viewPagerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            viewPagerLayoutParams.setMargins(viewPagerMarginLeftAndRight, 0, viewPagerMarginLeftAndRight, 0);
            viewPager.setLayoutParams(viewPagerLayoutParams);
        }


        adapter = new AdapterViewPager(this.data);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(++viewPagerCurrentItem);//设置为魅族模式时，设置初始page时，页面在data中的position加1
        if (dotsList != null && viewPagerCurrentItem != 0 && viewPagerCurrentItem != data.size() - 1) {//初始化外部自定义指示器
            for (int i = 0; i < dotsList.size(); i++) {
                if (i == viewPagerCurrentItem - 1)
                    dotsList.get(i).setBackgroundResource(dotsFocusRes);
                else
                    dotsList.get(i).setBackgroundResource(dotsNormalRes);
            }
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (openCarousel) {//2018/6/29  调整了ViewPager时是否开启轮播的功能的判断位置（因为在viewpager在屏幕完成绘制时会调用onPageScrolled方法）
                    if (positionOffset != 0.0f) {
                        handler.removeCallbacksAndMessages(null);
                    } else {//无论是轮播还是手动滑动的时候，都能保证前面没有线程执行自动轮播功能
                        handler.postDelayed(target, delayMillis);
                    }
                }

                // TODO: 2018/5/25 滑动的时候的页面计算
                Log.d("MyBannerView", "position--" + position + "      positionOffset--" + positionOffset + "      positionOffsetPixels--" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerCurrentItem = position;
                Log.d("MyBannerView", "viewPagerCurrentItem    " + viewPagerCurrentItem);
                //pager手动滑动时切换到data中的第一个和倒数第一个时，松手后自动切换到倒数第二个和第二个位置
                if (position == 0) {
                    viewPagerCurrentItem = data.size() - 2;
                    viewPager.setCurrentItem(viewPagerCurrentItem);
                } else if (position == data.size() - 1) {
                    viewPagerCurrentItem = 1;
                    viewPager.setCurrentItem(viewPagerCurrentItem);
                }
                //pager切换时的监听，进行指示器刷新
                if (dotsList != null && position != 0 && position != data.size() - 1) {
                    for (int i = 0; i < dotsList.size(); i++) {
                        if (i == position - 1)
                            dotsList.get(i).setBackgroundResource(dotsFocusRes);
                        else
                            dotsList.get(i).setBackgroundResource(dotsNormalRes);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setImgOutScreenScaleAnimation();
    }

    /**
     * 循环线程初始化
     */
    Runnable target = new Runnable() {
        @Override
        public void run() {
            Log.d("MyBannerView", "target");
            handler.obtainMessage().sendToTarget();
            handler.postDelayed(target, delayMillis);
        }
    };

    /**
     * viewPager页面更新
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (viewPagerCurrentItem + 1 < data.size() - 1) {
                viewPagerCurrentItem++;
                viewPager.setCurrentItem(viewPagerCurrentItem);
            } else if (viewPagerCurrentItem + 1 == data.size() - 1) {
                viewPagerCurrentItem = 1;
                viewPager.setCurrentItem(viewPagerCurrentItem);
            }

        }
    };


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
        int childViewPaddingLeftAndRight = 30;
        float pageScaleMin = 0.8f;
        int viewPagerMarginLeftAndRight = 150;
        int bannerType = BANNER_TYPE_NOMOL;
        int delayMillis = 4000;
        int viewPagerCurrentItem = 0;
        private Boolean openCarousel = false;//轮播模式
        int[] dataRes;
        String[] dataUrl;
        List<View> dotsList = null;//外部用户自己定义好了指示器，传递自己的指示器view集合进来，用于在本控件内部控制轮播时指示器的切换
        Integer dotsFocusRes = -1, dotsNormalRes = -1;//用户指定指示器图片资源


        public Builder(Context context, int[] dataRes, int bannerType) {
            this.context = context;
            this.dataRes = dataRes;
            this.bannerType = bannerType;
        }

        public Builder(Context context, String[] dataUrl, int bannerType) {
            this.context = context;
            this.dataUrl = dataUrl;
            this.bannerType = bannerType;
        }

        public Builder setViewPaddingLeftAndRight(int childViewPaddingLeftAndRight) {
            this.childViewPaddingLeftAndRight = childViewPaddingLeftAndRight;
            return this;
        }

        public Builder setPageScaleMin(float pageScaleMin) {
            this.pageScaleMin = pageScaleMin;
            return this;
        }

        public Builder setViewPagerMarginLeftAndRight(int viewPagerMarginLeftAndRight) {
            this.viewPagerMarginLeftAndRight = viewPagerMarginLeftAndRight;
            return this;
        }

        public Builder setDelayMillis(int delayMillis) {
            this.delayMillis = delayMillis;
            return this;
        }

        public Builder setVviewPagerCurrentItem(int viewPagerCurrentItem) {
            this.viewPagerCurrentItem = viewPagerCurrentItem;
            return this;
        }

        public Builder setOpenCarousel(boolean openCarousel) {
            this.openCarousel = openCarousel;
            return this;
        }

        public Builder setIndicatorExternalUserDefined(List<View> dotsList, Integer dotsFocusRes, Integer dotsNormalRes) {
            this.dotsList = dotsList;
            this.dotsFocusRes = dotsFocusRes;
            this.dotsNormalRes = dotsNormalRes;
            return this;
        }

        public void build() {
            initMyBannerViewValue(this);
        }
    }
}
