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

    /**
     *
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
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the sphere
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Point3D p0=ray.getP0();
        Vector v=ray.getDir();

        if(p0.equals(_center)){ //if the ray begin at the center of the sphere there is one intersection point
            Point3D p1=p0.add(v.scale(_radius));
            return List.of(p1);
        }

        Vector u=_center.subtract(p0);//u is vector from the beginning of the ray to the center of the sphere
        double tM=u.dotProduct(v);//tM is the length of the projection of u on v
        double d=alignZero(Math.sqrt(u.lengthSquared()-(tM*tM)));

        if (d>=_radius) {//when d>=radius there are no intersections point
            return null;
        }

        double tH=Math.sqrt((_radius*_radius)-(d*d));
        double t1=tM-tH;
        double t2=tM+tH;

        if(t1>0 && t2>0) {//when there are 2 intersection points
            Point3D p1 = ray.getPoint(t1); //p1=p0+t1v
            Point3D p2= ray.getPoint(t2);//p2=p0+t2v

            return List.of(p1,p2);
        }
        if(t1>0){
            Point3D p1=ray.getPoint(t1);
            return List.of(p1);
        }
        if(t2>0){
            Point3D p2=ray.getPoint(t2);
            return List.of(p2);
        }

        return null;
    }
}

