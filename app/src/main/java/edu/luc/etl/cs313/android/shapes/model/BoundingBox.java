package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
       return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        if(g.getShapes().isEmpty()){
            return new Location(0,0,new Rectangle(0,0));
        }
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for( Shape shape : g.getShapes()){
            Location loc = shape.accept(this);
            minX = Math.min(minX, loc.getX());
            minY = Math.min(minY, loc.getY());
            maxX = Math.max(maxX, loc.getX() + ((Rectangle)loc.getShape()).getWidth());
            maxY = Math.max(maxY, loc.getY() + ((Rectangle)loc.getShape()).getHeight());
        }

        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        Location innerLoc = l.getShape().accept(this);
        return new Location(l.getX() + innerLoc.getX(), l.getY() + innerLoc.getY(), innerLoc.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for(Point p : s.getPoints()){
            minX = Math.min(minX, p.getX());
            minY = Math.min(minY, p.getY());
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }

        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }
}
