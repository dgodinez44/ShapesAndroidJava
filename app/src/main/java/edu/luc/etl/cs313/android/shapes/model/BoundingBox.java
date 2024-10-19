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
        // For a circle the bounding box is a square with side length = 2 * radius
        // The circle's center is at (0,0) so the top-left corner of the bounding box
        // is at (-radius, -radius)
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        // Fill doesn't affect the bounding box
       return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        if(g.getShapes().isEmpty()){
            // If the group is empty, return a zero-sized rectangle at (0,0)
            return new Location(0,0,new Rectangle(0,0));
        }
        // Set min and max coordinates
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        // Iterate through all shapes in the group
        for( Shape shape : g.getShapes()){
            Location loc = shape.accept(this);
            // Update min and max coordinates
            minX = Math.min(minX, loc.getX());
            minY = Math.min(minY, loc.getY());
            maxX = Math.max(maxX, loc.getX() + ((Rectangle)loc.getShape()).getWidth());
            maxY = Math.max(maxY, loc.getY() + ((Rectangle)loc.getShape()).getHeight());
        }

        // Return a new location with the calculated bounding box
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        // Get the bounding box of the underlying shape
        Location innerLoc = l.getShape().accept(this);
        // Adjust the location based on the current location
        return new Location(l.getX() + innerLoc.getX(), l.getY() + innerLoc.getY(), innerLoc.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        // For a rectangle the bounding box is the rectangle itself at (0,0)
        return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        // StrokeColor doesn't affect the bounding box
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        // Outlines doesn't affect the bounding box
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        // Set min and max coordinates
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        // Iterate through all the points in the polygon
        for(Point p : s.getPoints()){
            // Update min and max coordinates
            minX = Math.min(minX, p.getX());
            minY = Math.min(minY, p.getY());
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }

        // Return a new location with the calculated bounding box
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }
}
