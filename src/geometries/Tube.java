package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * class Tube representing a tube in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Tube implements Geometry {
    final Ray _axisRay;//tube's axis
    final double _radius;//tube's radius

    /**
     * Tube constructor receiving radius (double) and Ray
     * @param _radius tube's radius
     * @param _axisRay the axis of the tube
     */
    public Tube(double _radius, Ray _axisRay) {
        this._radius = _radius;
        this._axisRay = _axisRay;
    }

    /**
     *
     * @param p point on the surface
     * @return normal to the tube from p
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector p0toP_vector=p.subtract(_axisRay.getP0());//vector from the beginning point to p
        Point3D o;//o is the intersection point between the tube's axis and orthogonal line between o to p.
        if(Util.isZero(p0toP_vector.dotProduct(_axisRay.getDir()))) {//if p0toP_vector orthogonal to tube's axis it means that o is p0
            o = _axisRay.getP0();
        }
        else {
            double projectionLength = _axisRay.getDir().dotProduct(p0toP_vector);//Length of the projection of p0toP_vector on the ray direction vector
            Vector projection = _axisRay.getDir().scale(projectionLength);//projection of p0toP_vector on the ray direction vector
            o = _axisRay.getP0().add(projection);
        }
        return p.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "axisRay=" + _axisRay.toString() +
                ", radius=" + _radius;
    }

    /**
     * getter
     * @return the axis of the tube
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getter
     * @return the radius of the tube
     */
    public double getRadius() {
        return _radius;
    }
}

