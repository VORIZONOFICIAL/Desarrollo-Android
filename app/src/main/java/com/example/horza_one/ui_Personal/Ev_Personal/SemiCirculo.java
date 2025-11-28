package com.example.horza_one.ui_Personal.Ev_Personal;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SemiCirculo extends View {

    private final Paint arcoBase = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint arcoProg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint aguja    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF oval     = new RectF();

    private float swArco;
    private float swAguja;

    private float prog = 0f;
    private float progAnim = 0f;

    public SemiCirculo(Context c) { super(c); init(); }
    public SemiCirculo(Context c, @Nullable AttributeSet a) { super(c, a); init(); }
    public SemiCirculo(Context c, @Nullable AttributeSet a, int s) { super(c, a, s); init(); }

    private void init() {
        swArco  = dp(20);
        swAguja = dp(10);

        arcoBase.setStyle(Paint.Style.STROKE);
        arcoBase.setStrokeCap(Paint.Cap.ROUND);
        arcoBase.setStrokeWidth(swArco);
        arcoBase.setColor(Color.parseColor("#D9EFFD"));

        arcoProg.setStyle(Paint.Style.STROKE);
        arcoProg.setStrokeCap(Paint.Cap.ROUND);
        arcoProg.setStrokeWidth(swArco);
        arcoProg.setColor(Color.parseColor("#66C6FF"));

        aguja.setStyle(Paint.Style.STROKE);
        aguja.setStrokeCap(Paint.Cap.ROUND);
        aguja.setStrokeWidth(swAguja);
        aguja.setColor(Color.parseColor("#8B70E8"));

        if (isInEditMode()) {
            prog = 65f;
            progAnim = 65f;
        }
    }

    private float dp(float v){ return v * getResources().getDisplayMetrics().density; }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int desiredW = (int) dp(180);
        int width = (wMode == MeasureSpec.EXACTLY) ? wSize
                : (wMode == MeasureSpec.AT_MOST ? Math.min(desiredW, wSize) : desiredW);

        int height = width / 2 + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float pl = getPaddingLeft();
        float pt = getPaddingTop();
        float pr = getPaddingRight();
        float pb = getPaddingBottom();

        float effW = Math.max(0, w - pl - pr);
        float effH = Math.max(0, h - pt - pb);

        float cx = pl + effW / 2f;
        float cy = pt + effH;

        float rMaxW = effW / 2f - swArco / 2f;
        float rMaxH = effH - swArco / 2f;
        float r = Math.max(0, Math.min(rMaxW, rMaxH));

        oval.set(cx - r, cy - r, cx + r, cy + r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (oval.width() <= 0f || oval.height() <= 0f) return;

        canvas.drawArc(oval, 180f, 180f, false, arcoBase);

        float sweep = (progAnim / 100f) * 180f;
        if (sweep > 0f) canvas.drawArc(oval, 180f, sweep, false, arcoProg);

        float angDeg = 180f + (progAnim / 100f) * 180f;
        double ang = Math.toRadians(angDeg);

        float cx = oval.centerX();
        float cy = oval.centerY();
        float r  = (oval.width() / 2f) - dp(12);

        float x2 = (float) (cx + r * Math.cos(ang));
        float y2 = (float) (cy + r * Math.sin(ang));

        canvas.drawLine(cx, cy, x2, y2, aguja);
    }

    public void setProgreso(float p){
        prog = clamp(p);
        progAnim = prog;
        invalidate();
    }

    public void setProgresoAnimado(float p){
        p = clamp(p);
        ValueAnimator va = ValueAnimator.ofFloat(progAnim, p);
        va.setDuration(800);
        va.addUpdateListener(a -> {
            progAnim = (float) a.getAnimatedValue();
            invalidate();
        });
        va.start();
        prog = p;
    }

    private float clamp(float v){ return Math.max(0f, Math.min(100f, v)); }
}


