package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        // A polygon is a shape
        return 1;
    }

    @Override
    public Integer onCircle(final Circle c) {
        // A circle is a shape
        return 1;
    }

    @Override
    public Integer onGroup(final Group g) {
        // For a group, we need to count all shapes within it
        int count = 0;
        for(Shape shape : g.getShapes()){
            // Recursively count shapes in the group
            count += shape.accept(this);
        }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        // A rectangle is a shape
        return 1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        // An outline is not a separate shape, so we count the underlying shape
        return o.getShape().accept(this);
    }

    @Override
    public Integer onFill(final Fill c) {
        // A fill is not a separate shape, so we count the underlying shape
        return c.getShape().accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        // A location is not a separate shape, so we count the underlying shape
        return l.getShape().accept(this);
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        // A stroke color is not a separate shape, so we count the underlying shape
        return c.getShape().accept(this);
    }
}
