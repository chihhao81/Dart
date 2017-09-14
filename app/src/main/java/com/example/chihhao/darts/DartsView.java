package com.example.chihhao.darts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.chihhao.darts.tool.Game01Item;

/**
 * Created by chihhao on 2017/7/10.
 */

public class DartsView extends View implements View.OnTouchListener {

    private final int[] NUMBERS = new int[]{6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5, 20, 1, 18, 4, 13};
    private final int[] DOUBLE_COLOR = new int[]{Color.RED, Color.WHITE};
    private final int[] SINGLE_COLOR = new int[]{Color.WHITE, Color.BLUE};

    private Paint paint;
    private Point centerPoint;
    private Canvas canvas;
    private OnScoreListener onScoreListener;

    private String aimText = "";
    private int textSize;
    private int doubleWidth, singleWidth, tripleWidth, singleBullWidth, doubleBullWidth;
    private int triplePosition;
    private boolean bullAllDouble = true; // bull要不要分單雙倍

    public DartsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        centerPoint = new Point(width/2, height/2);

        int size = Math.min(width, height);
        textSize = size/25;
        singleBullWidth = size/25;
        doubleBullWidth = size/30;
        doubleWidth = size/20;
        tripleWidth = size/20;
        singleWidth = size/2 - singleBullWidth - doubleBullWidth - doubleWidth;
        triplePosition = (int)(singleWidth - tripleWidth*2.5f);

        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(getMeasuredHeight() > App.screenWidth)
            setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);

        if(getMeasuredWidth() > 0 && getMeasuredHeight() > 0){
            init();
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        drawText();
        drawSingle();
        drawSingleBull();
        drawDoubleBull();
        drawDouble();
        drawTriple();
        drawDivide();
    }

    /** 分數數字 */
    private void drawText() {
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        float x, y;
        int r = singleWidth + doubleWidth + textSize;
        for (int i = 0; i < 20; i++) {
            int degrees = i * 18;
            String text = ""+NUMBERS[i];

            float sin = (float) Math.sin(Math.toRadians(degrees));
            float cos = (float) Math.cos(Math.toRadians(degrees));

            x = centerPoint.x + r * cos - (text.length() == 1 ? textSize/4 : textSize/2);
            y = centerPoint.y + r * sin + textSize/2;

            canvas.drawText(text, x, y, paint);
        }
    }

    private void drawArc(boolean single, int radius){
        int[] color = single ? SINGLE_COLOR : DOUBLE_COLOR;
        RectF rectF = new RectF();
        for(int i = 0; i < 20; i++) {
            int degrees = i * 18 - 9;
            rectF.set(centerPoint.x - radius, centerPoint.y - radius, centerPoint.x + radius, centerPoint.y + radius);
            paint.setColor(color[i%2]);
            canvas.drawArc(rectF, degrees, 18, single, paint);
        }
    }

    /** single */
    private void drawSingle(){
        paint.setStrokeWidth(singleWidth);
        paint.setStyle(Paint.Style.FILL);
        drawArc(true, singleWidth);
    }

    /** double */
    private void drawDouble(){
        paint.setStrokeWidth(doubleWidth);
        paint.setStyle(Paint.Style.STROKE);
        drawArc(false, singleWidth + doubleWidth/2);
    }

    /** triple */
    private void drawTriple(){
        paint.setStrokeWidth(tripleWidth);
        paint.setStyle(Paint.Style.STROKE);
        drawArc(false, triplePosition);
    }

    /** single bull */
    private void drawSingleBull(){
        paint.setStrokeWidth(singleBullWidth);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        int radius = singleBullWidth/2 + doubleBullWidth;
        RectF rectF = new RectF();
        rectF.set(centerPoint.x - radius, centerPoint.y - radius, centerPoint.x + radius, centerPoint.y + radius);
        canvas.drawArc(rectF, 0, 360, false, paint);
    }

    /** double bull */
    private void drawDoubleBull(){
        paint.setStrokeWidth(doubleBullWidth);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPoint.x, centerPoint.y, doubleBullWidth, paint);
    }

    /** divide */
    private void drawDivide(){
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(centerPoint.x, centerPoint.y, doubleBullWidth, paint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, singleBullWidth + doubleBullWidth, paint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, singleWidth - tripleWidth*3, paint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, singleWidth - tripleWidth*2, paint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, singleWidth, paint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, singleWidth + doubleWidth, paint);

        float startX, startY, endX, endY;
        int startR = singleBullWidth + doubleBullWidth;
        int endR = singleWidth + doubleWidth;
        for(int i = 0; i < 20; i++) {
            int degrees = i * 18 + 9;

            float sin = (float)Math.sin(Math.toRadians(degrees));
            float cos = (float)Math.cos(Math.toRadians(degrees));

            startX = centerPoint.x + startR * cos;
            startY = centerPoint.y + startR * sin;
            endX = centerPoint.x + endR * cos;
            endY = centerPoint.y + endR * sin;

            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    /** 點到哪個區域 */
    private void whichArea(MotionEvent event){
        float x = event.getX() - centerPoint.x;
        float y = centerPoint.y - event.getY();
        float r = (float) Math.sqrt(x*x + y*y); // 到圓心的距離
        float degrees = (float) Math.toDegrees(Math.atan(y/x));
        int score = 0;
        String scoreText = "";

        if(x < 0) // 第 2,3 象限
            degrees += 180;
        else if(x > 0 && y < 0) // 第 4 象限
            degrees += 360; // 轉成正數再計算

        if(r <= doubleBullWidth) {
            score = 50;
            scoreText = "D-BULL";
        } else if(r > doubleBullWidth && r <= doubleBullWidth+singleBullWidth) {
            score = 25 * (bullAllDouble ? 2 : 1);
            scoreText = "S-BULL";
        } else {
            int rate;
            if(r > singleWidth + doubleWidth) { // out
                rate = 0;
                scoreText = "OUT";
            } else if(r > singleWidth && r <= singleWidth + doubleWidth) { // double
                rate = 2;
                scoreText = "D-";
            } else if(r > triplePosition - tripleWidth/2 && r <= triplePosition + tripleWidth/2) { // triple
                rate = 3;
                scoreText = "T-";
            } else { // single
                rate = 1;
            }

            if(rate != 0) {
                if (degrees <= 9 || degrees > 351)
                    score = NUMBERS[0];
                else
                    score = NUMBERS[20 - (int) ((degrees + 9) / 18)];
                scoreText += score;
                score *= rate;
            }
        }
        if(onScoreListener != null) {
            if (event.getAction() == MotionEvent.ACTION_UP)
                onScoreListener.onScore(new Game01Item(score, scoreText));
            else if(event.getAction() == MotionEvent.ACTION_MOVE && !aimText.equals(scoreText)) {
                onScoreListener.onAim(scoreText);
                aimText = scoreText;
            }
        }
    }

    public void setBullAllDouble(boolean bullAllDouble){
        this.bullAllDouble = bullAllDouble;
    }

    public void setOnScoreListener(OnScoreListener onScoreListener){
        this.onScoreListener = onScoreListener;
    }

    public interface OnScoreListener{
        void onScore(Game01Item item);
        void onAim(String scoreText);
    }

    public static long time;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                whichArea(event);
                break;
            case MotionEvent.ACTION_DOWN:
                time = System.currentTimeMillis();
                whichArea(event);
                break;
            case MotionEvent.ACTION_UP:
                whichArea(event);
                break;
        }
        return true;
    }
}
