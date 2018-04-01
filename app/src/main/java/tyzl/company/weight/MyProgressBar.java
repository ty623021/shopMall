package tyzl.company.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar {
    private String text_progress;
    private Paint mPaint;
    private int curProgress;
    private int maxProgress;
    private static final int INTERVAL = 1;
    private static final int TAG_WEBVIEW = 100;

    public MyProgressBar(Context context) {
        super(context);
        initPaint();
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        setTextProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text_progress, 0, this.text_progress.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text_progress, x, y, this.mPaint);
    }

    /**
     * description: ��ʼ������
     * Create by lll on 2013-8-13 ����1:41:49
     */
    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.WHITE);
    }

    private void setTextProgress(int progress) {
        this.text_progress = "";
    }

    public synchronized void setAnimProgress(int progress) {
        maxProgress = progress;
        curProgress = 0;
        mHandler.sendEmptyMessageDelayed(INTERVAL, 5);
    }

    public synchronized void setAnimProgress2(int progress) {
        maxProgress = progress;
        curProgress = this.getProgress();
        Message msg = mHandler.obtainMessage();
        msg.what = TAG_WEBVIEW;
        mHandler.sendMessageDelayed(msg, 5);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (curProgress < maxProgress) {
                        setProgress(curProgress);
                        curProgress += 1;
                        mHandler.sendEmptyMessageDelayed(INTERVAL, 5);
                    } else {
                        setProgress(curProgress);
                    }

                    break;
                case TAG_WEBVIEW:
                    if (curProgress < maxProgress) {
                        setProgress(curProgress);
                        curProgress += 1;
                        mHandler.sendEmptyMessageDelayed(TAG_WEBVIEW, 5);
                    } else {
                        setProgress(curProgress);
                    }
                    if (curProgress == 100) {
                        Animation animationUP = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f);
                        animationUP.setDuration(600);
                        animationUP.setFillAfter(true);
                        MyProgressBar.this.setProgress(0);
                        MyProgressBar.this.setVisibility(View.GONE);
                    }
                    break;
                default:
                    setProgress(curProgress);
                    break;
            }
        }

        ;
    };


}
