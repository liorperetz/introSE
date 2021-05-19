package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry is interface for geometric shapes
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public abstract class Geometry implements Intersectable {

    //self color of geometry
    protected Color _emission=Color.BLACK;

    /**
     * get the normal vector to surface from a point
     * @param p point on the surface
     * @return reference to Vector representing the normal vector
     */
    public abstract Vector getNormal(Point3D p);

    /**
     * emission getter
     * @return self color of geometry
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * emission chaining setter
     * @param emission color of the geometry
     * @return current Geometry instance
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }
}
