package com.mediustechnologies.payemi.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.mediustechnologies.payemi.R;

public  class MyProgressBar extends ProgressBar {

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Paint linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.grey));
        final float lineWidth = 5f;
        linePaint.setStrokeWidth(lineWidth);

        canvas.drawLine(getWidth() * getProgress() / 100f, -5,
                getWidth() * getProgress() / 100f, getHeight()+2,
                linePaint);

    }
}