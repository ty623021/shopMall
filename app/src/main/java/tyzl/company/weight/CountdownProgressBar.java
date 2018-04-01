package tyzl.company.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import tyzl.company.R;


/**
 * com.rongteng.weight.RoundProgressBar
 * 倒计时
 *
 * @author xiaanming
 */
public class CountdownProgressBar extends View {
    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度颜色
     */
    private int roundProgressColor;

    /**
     * 文字颜色
     */
    private int textColor;

    /**
     * 文字大小
     */
    private float textSize;

    /**
     * 圆环宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;

    /**
     * 文字是否显示
     */
    private boolean textIsDisplayable;

    private int style;
    public static final int STROKE = 0;
    public static final int FILL = 1;
    private int curProgress = 0;
    private int maxProgress;
    private static final int INTERVAL = 1;
    private CountdownListener countdownListener;

    public CountdownProgressBar(Context context) {
        this(context, null);
    }

    public CountdownProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, getResources().getColor(R.color.main_color));
        roundProgressColor = mTypedArray.getColor(
                R.styleable.RoundProgressBar_roundProgressColor, getResources().getColor(R.color.gray));
        textColor = mTypedArray.getColor(
                R.styleable.RoundProgressBar_textColor, Color.BLACK);
        textSize = mTypedArray.getDimension(
                R.styleable.RoundProgressBar_textSize, getResources().getDimension(R.dimen.size_sp_12));
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 60);
        textIsDisplayable = mTypedArray.getBoolean(
                R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制圆环
         */
        int centre = getWidth() / 2;  // 宽度的中心
        int radius = (int) (centre - roundWidth / 2);  // 内圆半径
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true); // 是否给Paint加上抗锯齿标志
        canvas.drawCircle(centre, centre, radius, paint); //
        /**
         * 画圆弧
         */
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, true, paint);
                break;
            }
        }

        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);  // 设置字体样式
        float textWidth = paint.measureText((max - curProgress) + "");// 获取文字的宽度值
        if (textIsDisplayable && style == STROKE) {
            canvas.drawText(max - curProgress + "", (getWidth() - textWidth) / 2f,
                    //y公式： float baselineY = centerY + (fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom
                    getWidth() / 2f - (paint.descent() + paint.ascent()) / 2f,
                    paint);
        }

    }

    public synchronized int getMax() {
        return max;
    }

    /**
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度值
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
    }

    public synchronized void setAnimProgress(int progress) {
        maxProgress = progress;
        curProgress = 0;
        mHandler1.sendEmptyMessageDelayed(INTERVAL, 1);
    }

    /**
     * 默认在还剩20秒的时候回调
     */
    private int showVoiceTime = 20;

    /**
     * 设置倒计时中间回调的时间(语音验证码显示时间)
     *
     * @param showVoiceTime
     */
    public void setShowVoiceTime(int showVoiceTime) {
        this.showVoiceTime = showVoiceTime;
    }

    private Handler mHandler1 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (curProgress < maxProgress) {
                        if (countdownListener != null) {
                            if (maxProgress - curProgress == showVoiceTime) {
                                countdownListener.onOther();
                            }
                        }
                        setProgress(curProgress);
                        curProgress += 1;
                        mHandler1.sendEmptyMessageDelayed(INTERVAL, 1000);
                    } else {
                        if (countdownListener != null) {
                            countdownListener.onTheEnd();
                        }
                        setProgress(curProgress);
                    }
                    break;
            }
        }
    };

    public synchronized void showText(boolean show) {
        textIsDisplayable = show;
    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public interface CountdownListener {
        void onTheEnd();

        void onOther();
    }

    public void setCountdownListener(CountdownListener listener) {
        this.countdownListener = listener;
    }

}
