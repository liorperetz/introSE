package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class Tube representing a tube in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Tube implements Geometry {
    Ray _axisRay;//tube's axis
    double _radius;//tube's radius

    /**
     * Tube constructor receiving radius (double) and Ray
     * @param _radius tube's radius
     * @param _axisRay the axis of the tube
     */
    public Tube(double _radius, Ray _axisRay) {
        this._radius = _radius;
        this._axisRay = _axisRay;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    @Override
    public String toString() {
        return "axisRay=" + _axisRay.toString() +
                ", radius=" + _radius;
    }

    public Ray getAxisRay() {
        return _axisRay;
    }

    public double getRadius() {
        return _radius;
    }
}

