package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry is interface for geometric shapes
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public interface Geometry extends Intersectable {
    /**
     * get the normal vector to surface in a point
     * @param p point on the surface
     * @return reference to Vector representing the normal vector
     */
    Vector getNormal(Point3D p);
}
