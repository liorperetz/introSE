package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        return null;
    }
}

