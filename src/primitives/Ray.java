package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * class Ray representing a Ray - part of a line that has a fixed starting point
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Ray {
    /**
     * constant delta, used to move ray's starting point
     */
    private static final double DELTA = 0.1;
    /**
     * ray's beginning point
     */
    final Point3D _p0;
    /**
     * ray's direction vector
     */
    final Vector _dir;

    /**
     * Ray constructor receiving Point3D and Vector
     *
     * @param p0  value for beginning point
     * @param dir value for direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this._p0 = new Point3D(p0._x, p0._y, p0._z);
        this._dir = dir.normalized();
    }

    /**
     * Ray constructor with moving in a delta the starting point
     * @param point basic starting point
     * @param n moving the point in the direction of n vector
     * @param dir ray direction vector
     */
    public Ray(Point3D point, Vector n, Vector dir) {
        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : - DELTA);
        _p0=point.add(delta);
        _dir=dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    @Override
    public String toString() {
        return "p0=" + _p0.toString() +
                ", dir=" + _dir.toString();
    }

    /**
     * getter
     *
     * @return beginning point of the ray
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter
     *
     * @return direction vector of the ray
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * Calculate the value of a point
     * that is a finite distance from the starting point
     * of the ray and in the direction of the ray
     *
     * @param t finite distance from p0
     * @return reference to Point3D representing the new point value
     */
    public Point3D getPoint(double t) {

        return _p0.add(_dir.scale(t));
    }

    /**
     * find the closet point to _p0 from list of points
     *
     * @param pointsList list of Point3D objects
     * @return the closest point to _p0
     */
    public Point3D findClosestPoint(List<Point3D> pointsList) {

        if (pointsList == null) {
            return null;
        }

        Point3D closestPoint = pointsList.get(0);
        for (Point3D point : pointsList) {
            if (_p0.distance(point) < _p0.distance(closestPoint)) {
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    /**
     * find the closet geoPoint to _p0 from list of geoPoints
     *
     * @param geoPointsList list of GeoPoint objects
     * @return the closest geoPoint to _p0
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointsList){

        if (geoPointsList == null) {
            return null;
        }

        double closetDistance=Double.MAX_VALUE;
        GeoPoint closestGeoPoint=null;

        for (GeoPoint geoPoint : geoPointsList) {
            double distance = _p0.distance(geoPoint._point);
            if (distance < closetDistance) {
                closetDistance=distance;
                closestGeoPoint = geoPoint;
            }
        }
        return closestGeoPoint;

    }


}
