package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 *
 *
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        return 1;
    }

    @Override
    public Integer onCircle(final Circle c) { // Basic shapes, return 1
        return 1;
    }

    @Override
    public Integer onGroup(final Group g) { // Group is a list of shapes
        int count = 0;
        for (Shape shape : g.getShapes()){ // loop through the list of shapes and add 1 to count
           count += shape.accept(this);
        }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) { // Basic shapes, return 1
        return 1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Integer onFill(final Fill c) {
        return c.getShape().accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        return l.getShape().accept(this); // return count of the contained shape
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }
}
