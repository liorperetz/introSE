package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Intersectable is an interface for intersectable by ray geometries
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public interface Intersectable {

    /**
     * GeoPoint is helper class
     * used to link Point3D to the
     * geometry in which it is located
     */
    public static class GeoPoint{
        public Geometry _geometry;
        public Point3D _point;

        /**
         * GeoPoint constructor
         * @param geometry instance of Geometry
         * @param point Point3D on the geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            _geometry = geometry;
            _point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return _geometry.equals(geoPoint._geometry) && _point.equals(geoPoint._point);
        }

    }
    /**
     * find intersections points of ray with geometries
     * @param ray ray in 3d space
     * @return list of the intersections points of the ray with a geometry
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp._point).collect(Collectors.toList());
    }

    /**
     * find intersections of ray with geoPoints.
     * contains default implementation
     * @param ray ray in 3d space
     * @return list of the geoPoints the ray intersects with
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);//infinite distance includes all the geoPoints
    }

    /**
     * find intersections of ray with geoPoints with distance limit
     * @param ray ray in 3d space
     * @param maxDistance maximum distance limit
     * @return list of the geoPoints the ray intersects with
     */
    List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance);

}

