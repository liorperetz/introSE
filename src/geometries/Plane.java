package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Plane representing a plane in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Plane implements Geometry  {
    final Point3D _p;//point in the plane
    final Vector _normal;//normal vector to the plane

    /**
     * Plane constructor receiving 3 Point3D objects
     * @param p1 point in the plane
     * @param p2 point in the plane
     * @param p3 point in the plane
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _p= p1;//point in the plane
        Vector v1=p1.subtract(p2);
        Vector v2=p1.subtract(p3);
        _normal=v1.crossProduct(v2).normalize();
    }

    /**
     * Plane constructor receiving Vector and Point3D
     * @param p point in the plane
     * @param normal normal vector to the plane
     */
    public Plane(Point3D p, Vector normal) {

        _p = p;
        _normal = normal.normalized();

    }

    /**
     *
     * @param p point on the surface
     * @return normal vector to the plane
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    /**
     * getter
     * @return p, point in the plane
     */
    public Point3D getP() {
        return _p;
    }

    /**
     * getter
     * @return normal vector to the plane
     */
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

    /**
     * find intersections points of ray with a plane
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the plane
     *
     * Mathematical principle:
     * intersection point P equal to P= p0 + t∙v , t > 0 (v is ray direction vector)
     * we know that:
     * _normal∙( _p − p0 - t∙v ) = 0 => _normal∙(_p−p0)−t∙_normal∙v = 0 =>
     * => t= (_normal∙(_p-p0))/_normal∙v
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Vector v =ray.getDir();
        Point3D p0=ray.getP0();

        if(p0.equals(_p)){

            return null; //the ray begin in the plane
        }

        double nv=_normal.dotProduct(v);
        double nQMinusP0=_normal.dotProduct(_p.subtract(p0));

        if(isZero(nQMinusP0) || isZero(nv)){

            return null; // nQMinusP0 is 0 when the ray begin in the plane
                         // nv is 0 when the ray is in the plane
        }

        double t= alignZero(nQMinusP0 / nv);

        if (t>0){
            return List.of(ray.getPoint(t));
        }

        return null;


    }
}
