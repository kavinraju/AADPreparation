package skr.aadpreparation.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewCircle extends View {

    public CustomViewCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int x = getWidth()/2;
        int y = getHeight()/2;
        int radius = y;

        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(x,y,radius,paint);
        super.onDraw(canvas);
    }
}
