package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class Sphere representing a sphere in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Sphere implements Geometry {
    final Point3D _center;//the center point of the sphere
    final double _radius;//sphere's radius

    /**
     * Sphere constructor receiving radius (double) and Point3D
     * @param _radius the radius of the sphere
     * @param _center the center point of the Sphere
     */
    public Sphere(double _radius, Point3D _center) {
        this._radius=_radius;
        this._center = _center;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center.toString() +
                ", _radius=" + _radius +
                '}';
    }
}

