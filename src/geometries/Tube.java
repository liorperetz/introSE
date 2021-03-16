package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

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

        Vector p0toP=p.subtract(_axisRay.getP0());//vector from the beginning point of the ray to p
        double t=_axisRay.getDir().dotProduct(p0toP);//Length of the projection of p0toP on the ray direction vector

        if(isZero(t)){//if t=0 o is actually p0
            return p.subtract(_axisRay.getP0()).normalize();
        }

        Vector v=_axisRay.getDir().scale(t);//vector from p0 to o
        Point3D o=_axisRay.getP0().add(v);//o=p0+t*v

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

