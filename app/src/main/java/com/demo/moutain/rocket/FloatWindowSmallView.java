package com.demo.moutain.rocket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MOUTAIN on 2017/4/6.
 */

public class FloatWindowSmallView extends LinearLayout {

    private float xInview;
    private float yInview;
    private float xInScreen;
    private float yInScreen;
    private float yRelativeScreen;
    private WindowManager.LayoutParams params;
    private final WindowManager windowManager;
    private float xRelativeScreen;
    public static int viewWidth;
    public static int viewHeight;

    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        View view = findViewById(R.id.float_window_small);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        viewHeight = view.getMeasuredHeight();
        viewWidth = view.getMeasuredWidth();
        System.out.println("measure width=" + viewWidth + " height=" + viewHeight);
        TextView tv_float = (TextView) findViewById(R.id.tv_float);
        tv_float.setText("用力拽我");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下记录当前的值,event.getX():相对于view的x坐标（不超过view的宽度）。
                // event.getRawX():相对于屏幕的X坐标。
                //手指按下记录当前的值,event.gety():相对于view的y坐标（不超过 view的高度）。
                // event.getRawY():相对于屏幕的Y坐标。
                xInview = event.getX();
                yInview = event.getY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusHeight();
                yRelativeScreen = event.getRawY()-getStatusHeight();
                xRelativeScreen = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusHeight();
                //更新小窗的位置
                updateViewPostion();
                break;
            case MotionEvent.ACTION_UP:
                //抬起手指的时候位置未变，则就是没有拖动，视为点击事件
                if(xInScreen==xRelativeScreen&&yRelativeScreen==yInScreen){
                    openBigWindow();
                }
        }
        return super.onTouchEvent(event);
    }

    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallView(getContext());
    }

    /**
     * 设置小窗的参数
     */
    public void setParams(WindowManager.LayoutParams params){
        this.params = params;
    }

    private void updateViewPostion() {
        params.x = (int) (xInScreen-xInview);
        params.y = (int) (yInScreen-yInview);
        windowManager.updateViewLayout(this,params);
    }

    /**
     * 通过反射的方式来获取状态栏高度
     * @param
     * @return
     */
    public int getStatusHeight() {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
