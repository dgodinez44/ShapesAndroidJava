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
        canvas.drawRect(45, 10, 60, 20, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(45, 10, 100, 40, paint);

        // Draw the blue polygon (trapezoid)
        paint.setColor(Color.BLUE);
        Polygon trapezoid = new Polygon(
                new Point(55, 35),
                new Point(65, 45),
                new Point(80, 45),
                new Point(85, 35)
        );

        float[] trapezoidPts = new float[trapezoid.getPoints().size() * 4];
        int i = 0;
        for (int j = 0; j < trapezoid.getPoints().size(); j++) {
            Point current = trapezoid.getPoints().get(j);
            Point next = trapezoid.getPoints().get((j + 1) % trapezoid.getPoints().size());
            trapezoidPts[i++] = current.getX();
            trapezoidPts[i++] = current.getY();
            trapezoidPts[i++] = next.getX();
            trapezoidPts[i++] = next.getY();
        }
        canvas.drawLines(trapezoidPts, paint);

        // Draw the purple circle
        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(70, 75, 15, paint);

        // Draw the partial octagon (top left)
        paint.setColor(Color.BLACK);
        Polygon octagon = new Polygon(
                //new Point(10, 10),
                //new Point(20, 5),
                //new Point(30, 10)
                new Point(10, 15),  // Top
                new Point(15, 10),  // Top-right
                new Point(25, 10),  // Right
                new Point(30, 15),  // Bottom-right
                new Point(30, 25),  // Bottom
                new Point(25, 30),  // Bottom-left
                new Point(15, 30),  // Left
                new Point(10, 25)   // Top-left
        );
        float[] octagonPts = new float[octagon.getPoints().size() * 4];
        i = 0;
        for (int j = 0; j < octagon.getPoints().size(); j++) {
            Point current = octagon.getPoints().get(j);
            Point next = octagon.getPoints().get((j + 1) % octagon.getPoints().size());
            octagonPts[i++] = current.getX();
            octagonPts[i++] = current.getY();
            octagonPts[i++] = next.getX();
            octagonPts[i++] = next.getY();
        }
        canvas.drawLines(octagonPts, paint);

    }
}
