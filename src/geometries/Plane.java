package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * class Plane representing a plane in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Plane implements Geometry  {
    Point3D _p;//point in the plane
    Vector _normal;//normal vector to the plane

    /**
     * Plane constructor receiving 3 Point3D objects
     * @param p1 point in the plane
     * @param p2 point in the plane
     * @param p3 point in the plane
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _p= p1;//point in the plane
        _normal=null;//normal vector to the plane
    }

    /**
     * Plane constructor receiving Vector and Point3D
     * @param p point in the plane
     * @param normal perpendicular vector to the plane
     */
    public Plane(Point3D p, Vector normal) {
        this._p = p;
        this._normal = normal;

    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    public Point3D getP() {
        return _p;
    }

    public Vector getNormal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "p=" + _p.toString() +
                ", normal=" + _normal.toString() +
                '}';
    }
}
