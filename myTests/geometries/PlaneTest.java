package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Plane
 *
 * @author Lior Peretz
 * @author Reuven Klein
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), plane.getNormal(new Point3D(0, 0, 1)), "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {

        Plane plane = new Plane(new Point3D(1, 0, 0), new Vector(1, 1, 1));
        List<Point3D> result;
        Point3D p1;

        // ============ Equivalence Partitions Tests ==============
        //  The Ray neither orthogonal nor parallel to the plane

        // TC01: Ray intersects the plane
        p1 = new Point3D(-0.555555555555556, 0.666666666666667, 0.888888888888889);
        result = plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(2, 3, 4)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses plane");

        // TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(-1, -1, -1))), "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: ray included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 1),
                new Vector(0.5, -0.5, 0))), "The ray included in the plane, there are no intersection points");

        // TC12: ray does not included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(0.5, -0.5, 0))), "there are no intersection points");

        // **** Group: Ray is orthogonal to the plane
        //TC13: ray begin before the plane (1 point)
        p1 = new Point3D(-1, 1, 1);
        result = plane.findIntersections(new Ray(new Point3D(-2, 0, 0),
                new Vector(2, 2, 2)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Wrong intersection point");

        //TC14: ray begin in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(-1, 1, 1),
                new Vector(2, 2, 2))), "there are no intersection points");

        //TC15: ray begin after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 2, 2),
                new Vector(2, 2, 2))), "there are no intersection points");
        //end Group

        //TC16:Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(-1, 1, 1),
                new Vector(1, 2, 3))), "there are no intersection points");

        //TC17: Ray begins in the same point which appears as reference point in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(plane.getP(),
                new Vector(1, 2, 3))), "there are no intersection points");


    }
}