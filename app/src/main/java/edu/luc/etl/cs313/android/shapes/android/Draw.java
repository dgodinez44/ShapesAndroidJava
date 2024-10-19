package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXME
        this.paint = paint; // FIXME
        // Set default style to stroke
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        // Draw a circle at (0,0) with the specified radius
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        // Save the current color, set new color, draw the shape,
        // then restore the old color
        int oldColor = paint.getColor();
        paint.setColor(c.getColor());
        c.getShape().accept(this);
        paint.setColor(oldColor);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        // Save the current style, set to FILL_AND_STROKE,
        // draw the shape, then restore the old style
        Style oldStyle = paint.getStyle();
        paint.setStyle(Style.FILL_AND_STROKE);
        f.getShape().accept(this);
        paint.setStyle(oldStyle);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        // Draw each shape in the group
        for(Shape s : g.getShapes()){
            s.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        // Translate the canvas, draw the shape, then translate back
        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        canvas.translate(-l.getX(), -l.getY());
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        // Draw a rectangle with top-left at (0,0) and specified width and height
        canvas.drawRect(0,0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        // Save the current style, set to STROKE, draw the shape,
        // then restore the old style
        Style oldStyle = paint.getStyle();
        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(oldStyle);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        // create an array of points for the polygon
        final float[] pts = new float[s.getPoints().size() * 4 - 2];
        int i = 0;
        for(Point p : s.getPoints()){
            pts[i++] = p.getX();
            pts[i++] = p.getY();
        }
        // Connect back to the first point to close the polygon
        pts[i++] = s.getPoints().get(0).getX();
        pts[i] = s.getPoints().get(0).getY();

        // Draw the polygon as a series of connected lines
        canvas.drawLines(pts, paint);
        return null;
    }
}
