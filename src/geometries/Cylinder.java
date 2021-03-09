package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class Cylinder representing a cylinder in a 3D cartesian coordinate system
 * the class inherit from Tube
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Cylinder extends Tube {
    double _height;//cylinder's height

    /**
     * Cylinder constructor receiving radius (double), Ray and height (double)
     * @param _radius cylinder's radius
     * @param _axisRay the axis of the cylinder
     * @param _height cylinder's height
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ",height=" + _height;
    }

    public double getHeight() {
        return _height;
    }
}
