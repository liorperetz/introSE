package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries implements collection of geometries shapes
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Geometries implements Intersectable {

    private List<Intersectable> geometriesLst;

    /**
     * geometries constructor. set initialize new geometries collection
     */
    public Geometries(){
        geometriesLst=new LinkedList<Intersectable>(null);
    }

    /**
     * geometries constructor receiving geometries list
     * @param geometries list of geometries shapes
     */
    public Geometries(Intersectable... geometries){
    }

    /**
     * add geometries to the collection
     * @param geometries list of geometries to add the collection
     */
    public void add(Intersectable... geometries){

    }

    /**
     * find intersections points of ray with geometries
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the the geometries
     */
    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }

}
