package com.mgl.mybannerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MyBannerViewImageView extends LinearLayout {
    private ImageView img1, img2, img3;
    private List<Integer> resInts;
    private Context context;
    Animation alphaAnimationLeft, alphaAnimationLeft_1, alphaAnimationLeft_0;
    Animation alphaAnimationRight, alphaAnimationRight_1, alphaAnimationRight_0;
    int delayMillis = 4000;


    public MyBannerViewImageView(Context context) {
        super(context);
    }

    public MyBannerViewImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        alphaAnimationLeft_0 = AnimationUtils.loadAnimation(context, R.anim.anim_my_banner_left_0);
        alphaAnimationLeft = AnimationUtils.loadAnimation(context, R.anim.anim_my_banner_left_1);
        alphaAnimationLeft_1 = AnimationUtils.loadAnimation(context, R.anim.anim_my_banner_left_2);
        alphaAnimationRight_0 = AnimationUtils.loadAnimation(context, R.anim.anim_my_banner_right_0);
        alphaAnimationRight = AnimationUtils.loadAnimation(context, R.anim.anim_my_banner_right_1);
        alphaAnimationRight_1 = AnimationUtils.loadAnimation(context, R.anim.anim_my_banner_right_2);
        resInts = new ArrayList<>();
        resInts.add(R.drawable.a1);
        resInts.add(R.drawable.a2);
        resInts.add(R.drawable.a3);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.my_banner_view, this);
        img1 = (ImageView) findViewById(R.id.image1);
        img2 = (ImageView) findViewById(R.id.image2);
        img3 = (ImageView) findViewById(R.id.image3);
        init();
        Log.d("MyBannerView", "MyBannerView");
    }


    public List<Integer> getResInts() {
        return resInts;
    }

    public void setResInts(List<Integer> resInts) {
        this.resInts = resInts;
        Log.d("MyBannerView", "setResInts");
    }

    int showImgPosInListMid, showImgPosInListLeft, showImgPosInListRight;

    public void init() {
        if (resInts.size() == 1) {
            img2.setImageResource(resInts.get(0));
            showImgPosInListMid = 0;
        } else if (resInts.size() == 2) {
            img2.setImageResource(resInts.get(0));
            img3.setImageBitmap(clipImg(resInts.get(1)));
            showImgPosInListMid = 0;
        } else if (resInts.size() >= 3) {
            img1.setImageBitmap(clipImg(resInts.get(0)));
            img2.setImageResource(resInts.get(1));
            img3.setImageBitmap(clipImg(resInts.get(2)));
            showImgPosInListLeft = 0;
            showImgPosInListMid = 1;
            showImgPosInListRight = 2;
        }
        handler.postDelayed(target, delayMillis);
        invalidate();
        Log.d("MyBannerView", "init");
    }


    Runnable target = new Runnable() {
        @Override
        public void run() {
            Log.d("MyBannerView", "target");
            if (oneSlideComplete) {
                handler.obtainMessage().sendToTarget();
                handler.postDelayed(target, delayMillis);
            }
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("MyBannerView", "handleMessage  右");
            if (oneSlideComplete) {
                Log.d("MyBannerView", "handleMessage  右   wan");
                figureSlideToRight();
            }
        }
    };

    float xStart, xEnd;
    boolean oneSlideComplete = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("MyBannerView", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStart = event.getX();
                Log.d("MyBannerView", "onTouchEvent  1");
                oneSlideComplete = false;
                handler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_MOVE:
                xEnd = event.getX();
                if (!oneSlideComplete) {
                    if (xEnd - xStart > 180) {
                        Log.d("MyBannerView", "onTouchEvent  右");
                        figureSlideToRight();
                        oneSlideComplete = true;
                    } else if (xStart - xEnd > 180) {
                        Log.d("MyBannerView", "onTouchEvent  左");
                        figureSlideToLeft();
                        oneSlideComplete = true;
                    }
                }
                Log.d("MyBannerView", "onTouchEvent  " + showImgPosInListLeft + "    " + showImgPosInListMid + "    " + showImgPosInListRight + "    ");
                break;
            case MotionEvent.ACTION_UP:
                handler.postDelayed(target, delayMillis);
                break;
        }
        return true;
    }

    /**
     * 手指向左滑动时，图片的切换
     */
    private void figureSlideToLeft() {
        if (resInts.size() == 1) {
        } else if (resInts.size() == 2) {
            if (showImgPosInListMid == 0) {
                img1.setImageBitmap(clipImg(resInts.get(0)));
                img2.setImageResource(resInts.get(1));
                showImgPosInListMid = 1;
            }

            img2.startAnimation(alphaAnimationLeft);
            img3.startAnimation(alphaAnimationLeft_1);
        } else if (resInts.size() >= 3) {
            img1.startAnimation(alphaAnimationLeft_0);
            img2.startAnimation(alphaAnimationLeft);
            img3.startAnimation(alphaAnimationLeft_1);
            if (showImgPosInListLeft + 1 <= resInts.size() - 1) {
                img1.setImageBitmap(clipImg(resInts.get(showImgPosInListLeft + 1)));
                showImgPosInListLeft = showImgPosInListLeft + 1;
            } else {
                img1.setImageBitmap(clipImg(resInts.get(0)));
                showImgPosInListLeft = 0;
            }

            if (showImgPosInListMid + 1 <= resInts.size() - 1) {
                img2.setImageResource(resInts.get(showImgPosInListMid + 1));
                showImgPosInListMid = showImgPosInListMid + 1;
            } else {
                img2.setImageResource(resInts.get(0));
                showImgPosInListMid = 0;
            }

            if (showImgPosInListRight + 1 <= resInts.size() - 1) {
                img3.setImageBitmap(clipImg(resInts.get(showImgPosInListRight + 1)));
                showImgPosInListRight = showImgPosInListRight + 1;
            } else {
                img3.setImageBitmap(clipImg(resInts.get(0)));
                showImgPosInListRight = 0;
            }


        }
    }

    /**
     * 手指向右滑动时，图片的切换
     */
    private void figureSlideToRight() {
        if (resInts.size() == 1) {
        } else if (resInts.size() == 2) {
            if (showImgPosInListMid == 1) {
                img2.setImageResource(resInts.get(0));
                img3.setImageBitmap(clipImg(resInts.get(1)));
                showImgPosInListMid = 0;
            }
            img2.startAnimation(alphaAnimationRight);
            img3.startAnimation(alphaAnimationRight_1);
        } else if (resInts.size() >= 3) {
            img1.startAnimation(alphaAnimationRight_0);
            img2.startAnimation(alphaAnimationRight);
            img3.startAnimation(alphaAnimationRight_1);
            if (showImgPosInListLeft - 1 >= 0) {
                img1.setImageBitmap(clipImg(resInts.get(showImgPosInListLeft - 1)));
                showImgPosInListLeft = showImgPosInListLeft - 1;
            } else {
                img1.setImageBitmap(clipImg(resInts.get(showImgPosInListLeft - 1 + resInts.size())));
                showImgPosInListLeft = showImgPosInListLeft - 1 + resInts.size();
            }

            if (showImgPosInListMid - 1 >= 0) {
                img2.setImageResource(resInts.get(showImgPosInListMid - 1));
                showImgPosInListMid = showImgPosInListMid - 1;
            } else {
                img2.setImageResource(resInts.get(showImgPosInListMid - 1 + resInts.size()));
                showImgPosInListMid = showImgPosInListMid - 1 + resInts.size();
            }

            if (showImgPosInListRight - 1 >= 0) {
                img3.setImageBitmap(clipImg(resInts.get(showImgPosInListRight - 1)));
                showImgPosInListRight = showImgPosInListRight - 1;
            } else {
                img3.setImageBitmap(clipImg(resInts.get(showImgPosInListRight - 1 + resInts.size())));
                showImgPosInListRight = showImgPosInListRight - 1 + resInts.size();
            }
        }
    }

    /**
     * 裁剪图片，用于左右两边的图片只显示部分
     *
     * @param resId 图片资源id
     * @return
     */
    public Bitmap clipImg(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = 0.6f;
        float scaleHeight = 0.6f;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, (int) (width * 0.7), 0, width - (int) (width * 0.7), height, matrix, true);
        return bitmap1;
    }

    public class Builder {
        public Context context;
        public List<Integer> resInts;
        public List<String> resStrings;
        public Animation alphaAnimationLeft_0;
        public Animation alphaAnimationLeft;
        public Animation alphaAnimationLeft_1;
        public Animation alphaAnimationRight_0;
        public Animation alphaAnimationRight;
        public Animation alphaAnimationRight_1;

        public Builder(Context context, List<Integer> resInts, List<String> resStrings) {
            this.context = context;
            if (resInts != null)
                this.resInts = resInts;
            if (resStrings != null)
                this.resStrings = resStrings;
        }
    }
}
