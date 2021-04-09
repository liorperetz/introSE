package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Sphere
 *
 * @author Lior Peretz
 * @author Reuven Klein
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Sphere sphere = new Sphere(3, new Point3D(0, 0, 0));
        assertEquals(new Vector(1, 0, 0),
                sphere.getNormal(new Point3D(3, 0, 0)),
                "Bad normal to Sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
        List<Point3D> result;
        Point3D p1;
        Point3D p2;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);

        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));

        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "wrong intersections points");

        // TC03: Ray starts inside the sphere (1 point)
        p1 = new Point3D(1.911437827766148, 0.411437827766148, 0);

        result = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0),
                new Vector(1, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersection points");

        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(3, 0, 0),
                new Vector(1, 1, 0)));
        assertNull(result, "Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p1 = new Point3D(2, 0, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, -1, 0),
                new Vector(1, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersections points");

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(1, 1, 0)));
        assertNull(result, "Wrong number of points");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point3D(1, 1, 0);
        p2 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 2, 0),
                new Vector(0, -1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getY() < result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "wrong intersections points");

        // TC14: Ray starts at sphere and goes inside (1 points)
        p1 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersection points");

        // TC15: Ray starts inside (1 points)
        p1 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersection points");

        // TC16: Ray starts at the center (1 points)
        p1 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersection points");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 1),
                new Vector(1, 1, 1)));
        assertNull(result, "Wrong number of points");

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 2),
                new Vector(1, 1, 1)));
        assertNull(result, "Wrong number of points");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(2, -1, -1),
                new Vector(0, 1, 1)));
        assertNull(result, "Wrong number of points");

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(2, 0, 0),
                new Vector(0, 1, 1)));
        assertNull(result, "Wrong number of points");

        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(2, 1, 1),
                new Vector(0, 1, 1)));
        assertNull(result, "Wrong number of points");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point3D(3, 0, -1),
                new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

    }
}