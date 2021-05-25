package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries representing collection of geometries shapes,
 * as part of implementing composite design pattern
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Geometries implements Intersectable {
    //list of intersetable geometries
    private List<Intersectable> _intersectables;

    /**
     * Geometries constructor. initialize new geometries collection
     */
    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    /**
     * Geometries constructor receiving Intersectable list
     *
     * @param intersectables list of intersectable shapes
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<>();
        add(intersectables);
    }

    /**
     * add geometries to the collection
     *
     * @param intersectables list of geometries to add the collection
     */
    public void add(Intersectable... intersectables) {

        //for (Intersectable item: intersectables) {
        //    _intersectables.add(item);
        //}

        Collections.addAll(_intersectables, intersectables);
    }

    /**
     * find intersections points of ray with geoPoints
     * in a limited maximum distance from ray starting point
     *
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the the geometries
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;

        //check for ray intersections with each geometry
        //in the collection, and return list of the intersections points
        for (Intersectable geometry : _intersectables) {
            List<GeoPoint> intersectionPoints = geometry.findGeoIntersections(ray,maxDistance);
            if (intersectionPoints!= null) {
                if(result==null){//result was not initialized yet
                    result=new LinkedList<>();
                }
                result.addAll(intersectionPoints);
            }
        }

        return result;

    }

}
