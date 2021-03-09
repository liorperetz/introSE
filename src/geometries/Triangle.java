package geometries;

import primitives.Point3D;

/**
 * class Triangle representing a triangle in a 3D cartesian coordinate system
 * the class inherit from Polygon
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Triangle extends Polygon {
    /**
     * Triangle constructor receiving 3 Point3D objects
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

}
