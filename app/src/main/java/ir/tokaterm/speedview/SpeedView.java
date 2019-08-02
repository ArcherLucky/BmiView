package ir.tokaterm.speedview;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;



import java.util.Locale;

/**
 * this Library build By Anas Altair
 * see it on <a href="https://github.com/anastr/SpeedView">GitHub</a>
 */
public class SpeedView extends Speedometer {

    private Path indicatorPath = new Path(),
            markPath = new Path();
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            paint = new Paint(Paint.ANTI_ALIAS_FLAG),
            speedometerPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            markPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint speedTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG),
            textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private RectF speedometerRect = new RectF();

    public SpeedView(Context context) {
        super(context);
        init();
    }

    public SpeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void defaultValues() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height) ? height : width;
        setMeasuredDimension(size, size);
    }

    private void init() {
        speedometerPaint.setStyle(Paint.Style.STROKE);
        markPaint.setStyle(Paint.Style.STROKE);
        speedTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        float risk = getSpeedometerWidth()/2f;
        speedometerRect.set(risk, risk, w -risk, h -risk);

        float indW = w/32f;

        indicatorPath.reset();
        indicatorPath.moveTo(w/2f, 0f);
        indicatorPath.lineTo(w/2f -indW, h*2f/3f);
        indicatorPath.lineTo(w/2f +indW, h*2f/3f);
        RectF rectF = new RectF(w/2f -indW, h*2f/3f -indW, w/2f +indW, h*2f/3f +indW);
        indicatorPath.addArc(rectF, 0f, 180f);
        indicatorPath.moveTo(0f, 0f);

        float markH = h/28f;
        markPath.reset();
        markPath.moveTo(w/2f, 0f);
        markPath.lineTo(w/2f, markH);
        markPath.moveTo(0f, 0f);
        markPaint.setStrokeWidth(markH/3f);
    }

    private void initDraw() {
        speedometerPaint.setStrokeWidth(getSpeedometerWidth());
        markPaint.setColor(getMarkColor());
        speedTextPaint.setColor(getSpeedTextColor());
        speedTextPaint.setTextSize(getSpeedTextSize());
        textPaint.setColor(getTextColor());
        textPaint.setTextSize(getTextSize());
        circlePaint.setColor(getBackgroundCircleColor());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDraw();

        if (isWithBackgroundCircle())
            canvas.drawCircle(getWidth()/2f, getHeight()/2f, getWidth()/2f, circlePaint);

        speedometerPaint.setColor(getLowSpeedColor());
        canvas.drawArc(speedometerRect, 180f, 66.6f, false, speedometerPaint);
        speedometerPaint.setColor(getMediumSpeedColor());
        canvas.drawArc(speedometerRect, 180f+66.6f, 27.4f, false, speedometerPaint);
        speedometerPaint.setColor(getMedhighSpeedcolor());
        canvas.drawArc(speedometerRect, 180f+66.6f+27.4f, 17.6f, false, speedometerPaint);
        speedometerPaint.setColor(getHighSpeedColor());
        canvas.drawArc(speedometerRect, 180f+66.6f+27.4f+17.6f, 68.4f, false, speedometerPaint);


        canvas.save();
        canvas.rotate(180f+90f, getWidth()/2f, getHeight()/2f);
        for (int i=0; i <8; i+=1) {
            canvas.rotate(20f, getWidth()/2f, getHeight()/2f);
            canvas.drawPath(markPath, markPaint);
        }
        canvas.restore();

        paint.setColor(getIndicatorColor());
        canvas.save();
        canvas.rotate(90f +getDegree(), getWidth()/2f, getHeight()/2f);
        canvas.drawPath(indicatorPath, paint);
        canvas.restore();
        paint.setColor(getCenterCircleColor());
        canvas.drawCircle(getWidth()/2f, getHeight()/2f, getWidth()/12f, paint);

        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("00", getWidth()*2/50f, getHeight()*40/70f, textPaint);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.format(Locale.getDefault(), "%d",(int) getMaxSpeed()), getWidth()*48/50f, getHeight()*40/70f, textPaint);
        canvas.drawText(String.format(Locale.getDefault(), "%.1f", getCorrectSpeed()) +getUnit()
                , getWidth()/2f, speedometerRect.bottom, speedTextPaint);
    }
}

