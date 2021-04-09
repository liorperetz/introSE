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

    private List<Intersectable> _intersectables;

    /**
     * Geometries constructor. initialize new geometries collection
     */
    public Geometries() {

        _intersectables = new LinkedList<Intersectable>();

    }

    /**
     * Geometries constructor receiving Intersectable list
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
     * find intersections points of ray with geometries
     *
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the the geometries
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if(_intersectables.isEmpty()){

            return null;
        }

        List<Point3D> intersectionPoints=new LinkedList<>();

        for (Intersectable item: _intersectables) {

            List<Point3D> points=item.findIntersections(ray);

            if(points!=null){

                intersectionPoints.addAll(points);
            }
        }

        if(intersectionPoints.size()>0){

            return intersectionPoints;
        }

        return null; //no shape is intersected

    }

}
