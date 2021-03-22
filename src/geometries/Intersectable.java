package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;

/**
 * Intersectable is an interface for ray intersectable geometry shapes
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public interface Intersectable {
    /**
     *
      * @param ray ray in 3d space
     * @return list that contains the intersections points of the ray with a geometric shape
     */
    List<Point3D> findIntersections(Ray ray);
}

