package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Color;
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
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {

        int oldColor = paint.getColor();
        Style oldStyle = paint.getStyle();
        paint.setColor(c.getColor());
        paint.setStyle(Style.FILL_AND_STROKE);  // Ensure the style is set as expected
        c.getShape().accept(this);
        paint.setColor(oldColor);
        paint.setStyle(oldStyle);
        return null;

    }

    @Override
    public Void onFill(final Fill f) {

        Style oldStyle = paint.getStyle();
        paint.setStyle(Style.FILL);
        f.getShape().accept(this);
        paint.setStyle(oldStyle);
        return null;

    }

    @Override
    public Void onGroup(final Group g) {

        for (Shape s : g.getShapes()) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {

        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        canvas.translate(-l.getX(), -l.getY());
        return null;

    }

    @Override
    public Void onRectangle(final Rectangle r) {

        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {

        final Style original = paint.getStyle();
        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(original);
        return null;

    }

    @Override
    public Void onPolygon(final Polygon s) {

        final int size = s.getPoints().size();
        if (size < 2) return null;

        final float[] pts = new float[(size + 1) * 4];
        int index = 0;

        for (int i = 0; i < size; i++) {
            Point start = s.getPoints().get(i);
            Point end = s.getPoints().get((i + 1) % size);
            pts[index++] = start.getX();
            pts[index++] = start.getY();
            pts[index++] = end.getX();
            pts[index++] = end.getY();
        }

        canvas.drawLines(pts, paint);
        return null;

    }
}
