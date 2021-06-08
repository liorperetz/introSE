package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class Cylinder representing a cylinder in a 3D cartesian coordinate system
 * the class inherit from Tube
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Cylinder extends Tube {
    /**
     * cylinder's height
     */
   final double _height;

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

    /**
     * normal to the the cylinder from point on it
     * @param p point on the surface
     * @return normal vector to the cylinder from p
     *
     * Mathematical principle:
     * if dot product between the axis ray direction vector (v)
     * to a vector from the beginning axis ray to p (t) gives the cylinder height
     * or 0, it means that p is on one of the bases.
     * in other case we set o to p0+vt and the normal is p-o
     */
    @Override
    public Vector getNormal(Point3D p) {

        Point3D p0=_axisRay.getP0();
        Vector v=_axisRay.getDir();

        if(p.equals(p0)){//p is on the base
            return v.scale(-1);
        }

        Vector pMinusp0=p.subtract(p0);
        double t=v.dotProduct(pMinusp0);

        if(t==0){

            return v.scale(-1);
        }
        if(t==_height){

            return v;
        }

        Point3D o= _axisRay.getPoint(t);
        return p.subtract(o).normalize();

    }

    @Override
    public String toString() {
        return super.toString() + ",height=" + _height;
    }

    /**
     * getter
     * @return the cylinder height
     */
    public double getHeight() {
        return _height;
    }
}
