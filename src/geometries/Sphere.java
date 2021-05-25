package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class Sphere representing a sphere in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Sphere extends Geometry {
    final Point3D _center;//the center point of the sphere
    final double _radius;//sphere's radius

    /**
     * Sphere constructor receiving radius (double) and Point3D
     *
     * @param _radius the radius of the sphere
     * @param _center the center point of the Sphere
     */
    public Sphere(double _radius, Point3D _center) {
        this._radius = _radius;
        this._center = _center;
    }

    /**
     * @param p point on the sphere
     * @return normal vector to the sphere from point p
     */
    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(_center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center.toString() +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * find intersections points of ray with a sphere
     * in a limited maximum distance from ray starting point
     *
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the sphere
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        if (p0.equals(_center)) { //if the ray begin at the center of the sphere there is one intersection point
            Point3D p1 = p0.add(v.scale(_radius));
            return List.of(new GeoPoint(this, p1));
        }

        Vector u = _center.subtract(p0);//u is vector from the beginning of the ray to the center of the sphere
        double tM = u.dotProduct(v);//tM is the length of the projection of u on v
        double d = alignZero(Math.sqrt(u.lengthSquared() - (tM * tM)));

        if (d >= _radius) {
            //when d>=radius there are no intersections point
            return null;
        }
        //calculating the distance from p0 to the intersections points
        double tH = Math.sqrt((_radius * _radius) - (d * d));
        double t1 = tM - tH;
        double t2 = tM + tH;
        //check if the intersections points are in the allowed range
        boolean validT1 = alignZero(t1 - maxDistance) <= 0;
        boolean validT2 = alignZero(t2 - maxDistance) <= 0;
        //determine how many intersections points there are and calculate their values
        if (t1 > 0 && t2 > 0 && validT1 && validT2) {
            //there are 2 intersection points
            Point3D p1 = ray.getPoint(t1); //p1=p0+t1v
            Point3D p2 = ray.getPoint(t2);//p2=p0+t2v
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }
        if (t1 > 0 && validT1) {
            Point3D p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }
        if (t2 > 0 && validT2) {
            Point3D p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }

        return null;//no intersection points
    }
}


