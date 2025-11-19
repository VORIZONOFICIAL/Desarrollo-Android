package com.example.horza_one.ui.ui_Personal.Ev_Personal;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BarraPildora extends View {

    private final Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rBg = new RectF();
    private final RectF rFg = new RectF();

    private float radius;
    private float prog = 0f;
    private float progAnim = 0f;

    public BarraPildora(Context c) { super(c); init(); }
    public BarraPildora(Context c, @Nullable AttributeSet a) { super(c, a); init(); }
    public BarraPildora(Context c, @Nullable AttributeSet a, int s) { super(c, a, s); init(); }

    private void init() {
        bg.setStyle(Paint.Style.FILL);
        bg.setColor(Color.parseColor("#7663D8"));

        fg.setStyle(Paint.Style.FILL);
        fg.setColor(Color.parseColor("#37B9FF"));

        if (isInEditMode()) {
            prog = 60f;
            progAnim = 60f;
        }
    }

    private float dp(float v){ return v * getResources().getDisplayMetrics().density; }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int desiredW = (int) dp(220);
        int width = (wMode == MeasureSpec.EXACTLY) ? wSize
                : (wMode == MeasureSpec.AT_MOST ? Math.min(desiredW, wSize) : desiredW);

        int desiredH = (int) dp(28);
        setMeasuredDimension(width, desiredH + getPaddingTop() + getPaddingBottom());
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

        radius = effH / 2f;

        rBg.set(pl, pt, pl + effW, pt + effH);
        updateFgRect();
    }

    private void updateFgRect() {
        float width = rBg.width() * (progAnim / 100f);
        float left = rBg.left;
        float top  = rBg.top;
        float right = rBg.left + width;
        float bottom = rBg.bottom;
        if (right < left) right = left;
        rFg.set(left, top, right, bottom);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rBg.width() <= 0 || rBg.height() <= 0) return;
        canvas.drawRoundRect(rBg, radius, radius, bg);
        if (progAnim > 0f) {
            canvas.drawRoundRect(rFg, radius, radius, fg);
        }
    }

    public void setProgreso(float p){
        prog = clamp(p);
        progAnim = prog;
        updateFgRect();
    }

    public void setProgresoAnimado(float p){
        p = clamp(p);
        ValueAnimator va = ValueAnimator.ofFloat(progAnim, p);
        va.setDuration(700);
        va.addUpdateListener(a -> {
            progAnim = (float) a.getAnimatedValue();
            updateFgRect();
        });
        va.start();
        prog = p;
    }

    private float clamp(float v){ return Math.max(0f, Math.min(100f, v)); }

    public void setColorProgreso(int color){ fg.setColor(color); invalidate(); }
    public void setColorFondo(int color){ bg.setColor(color); invalidate(); }
}

