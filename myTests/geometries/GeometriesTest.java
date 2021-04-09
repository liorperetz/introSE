package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Geometries
 *
 * @author Lior Peretz
 * @author Reuven Klein
 *
 */
class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {

        Plane plane = new Plane(new Point3D(1, 0, 0), new Vector(1, 1, 1));
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
        Triangle triangle = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
        Geometries geometries=new Geometries(plane,sphere,triangle);
        List<Point3D> result;


        // ============ Equivalence Partitions Tests ==============

        // TC01: part intersected and part not
        result=geometries.findIntersections(new Ray(new Point3D(2,0,0),new Vector(-1,-1,-1)));
        assertEquals(2,result.size(),"Wrong number of points");

        // =============== Boundary Values Tests ==================

        //TC11: empty Geometries collection
        result=new Geometries().findIntersections(new Ray(new Point3D(1,1,1),new Vector(-1,-2,-3)));
        assertNull(result, "Wrong number of points");

        //TC12: no intersections
        result=geometries.findIntersections(new Ray(new Point3D(1,1,1),new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC14: one shape is intersected
        result=geometries.findIntersections(new Ray(new Point3D(3,3,1),new Vector(-1,-1,-1)));
        assertEquals(1,result.size(),"Wrong number of points");

        //TC15 : all shapes are intersected
        result=geometries.findIntersections(new Ray(new Point3D(1,1,1),new Vector(-1,-1,-1)));
        assertEquals(4,result.size(),"Wrong number of points");

    }

}