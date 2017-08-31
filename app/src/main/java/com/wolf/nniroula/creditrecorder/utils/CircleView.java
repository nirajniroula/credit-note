package com.wolf.nniroula.creditrecorder.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Niraj Niroula on 8/30/17.
 */

public class CircleView extends View {

    private Bitmap mIconBitmap;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix = new Matrix();
    private Paint mPaint;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setBitmapResource(int color) {
        //mIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leak_canary_icon);
        mIconBitmap =  Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
        mIconBitmap.eraseColor(color);

        mBitmapShader = new BitmapShader(mIconBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int bWidth = mIconBitmap.getWidth();
        int bHeight = mIconBitmap.getHeight();

        // Use a Matrix to scale the bitmap (naive method assumes bWidth == bHeight or aspect ratio gets messed up)
        mMatrix.setScale((float) width / bWidth, (float) height / bHeight);
        mBitmapShader.setLocalMatrix(mMatrix);

        canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);
    }
}