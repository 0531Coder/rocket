package com.demo.moutain.rocket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by MOUTAIN on 2017/4/6.
 */

public class FloatWindowBigVIew extends LinearLayout {

    public static float viewWidth;
    public static float viewHeight;

    public FloatWindowBigVIew(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.float_window_big,this);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        viewHeight = view.getMeasuredHeight();
        viewWidth = view.getMeasuredWidth();
        Button back = (Button) view.findViewById(R.id.back);
        Button closed = (Button) view.findViewById(R.id.close);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.createSmallWindow(context);
            }
        });
        closed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWindowManager.removeBigWindow(v.getContext());
                MyWindowManager.removeSmallView(v.getContext());
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                v.getContext().stopService(intent);
            }
        });
    }
}
