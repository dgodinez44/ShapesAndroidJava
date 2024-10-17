package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Color;
import edu.luc.etl.cs313.android.shapes.model.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawWidget extends View {

    private final Paint paint = new Paint();

    public DrawWidget(final Context context, final AttributeSet attrs, final int defStyle) {
        this(context);
    }

    public DrawWidget(final Context context, final AttributeSet attrs) {
        this(context);
    }

    public DrawWidget(final Context context) { super(context); }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(100, 100);
    }
    
    // TODO once BoundingBox and Draw are implemented, change Fixtures.simpleCircle
    // to Fixtures.complexGroup and test the app on an emulator or Android device
    // to make sure the correct figure is drawn (see Project 3 description for link)

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(final Canvas canvas) {
        final var shape = Fixtures.complexGroup;
        final var b = shape.accept(new BoundingBox());
        canvas.translate(-b.getX(), -b.getY());
        b.accept(new Draw(canvas, paint));
        shape.accept(new Draw(canvas, paint));
        canvas.translate(b.getX(), b.getY());

        // Clear the canvas with a white background
        canvas.drawColor(Color.WHITE);


        // Set up the paint object
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        // Draw the rectangle (left side)
        paint.setColor(Color.BLACK);
        canvas.drawRect(10, 10, 40, 90, paint);

        // Draw the red rectangle (right side)
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(50, 10, 70, 30, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(50, 10, 90, 50, paint);

        // Draw the blue polygon (trapezoid)
        paint.setColor(Color.BLUE);
        float[] trapezoidPts = {
                55, 35,  // First point
                65, 45,  // Second point
                80, 45,  // Third point
                85, 35,  // Fourth point
                55, 35   // Back to first point
        };
        canvas.drawLines(trapezoidPts, paint);

        // Draw the purple circle
        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(70, 75, 15, paint);

        // Draw the partial octagon (top left)
        paint.setColor(Color.BLACK);
        float[] octagonPts = {
                10, 10,  // First point
                20, 5,   // Second point
                30, 10,  // Third point
                10, 10   // Back to first point
        };
        canvas.drawLines(octagonPts, paint);

    }
}
