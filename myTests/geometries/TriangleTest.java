package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 *
 * @author Lior Peretz
 * @author Reuven Klein
 */
class TriangleTest {

    Triangle triangle = new Triangle(new Point3D(1, 0, 0),
            new Point3D(0, 1, 0),
            new Point3D(0, 0, 1));

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), triangle.getNormal(new Point3D(0, 0, 1)), "Bad normal to trinagle");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {

        List<Point3D> result;
        Point3D p1;

        // ============ Equivalence Partitions Tests ==============
        // in all cases we assume the ray intersects the triangle's plane

        // TC01: The ray intersects the Triangle  (1 point)
        p1 = new Point3D(0.2, 0.2, 0.6);
        result = triangle.findIntersections(new Ray(new Point3D(-1, -1, 0),
                new Vector(1, 1, 0.5)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersection points");

        // TC02: The ray intersects outside the triangle against edge
        assertNull(triangle.findIntersections(new Ray(new Point3D(0, -2, 0),
                new Vector(1, 1, 1))), "Ray does not intersect the triangle");

        // TC03:The ray intersects outside the triangle against vertex
        assertNull(triangle.findIntersections(new Ray(new Point3D(-1, -1, 0),
                new Vector(1, 1, 2))), "Ray does not intersect the triangle");

        // =============== Boundary Values Tests ==================

        // TC11: Ray intersects the triangle's edge
        assertNull(triangle.findIntersections(new Ray(new Point3D(-1, -1, 0),
                new Vector(1, 1, 0))), "Ray does not intersect the triangle");

        // TC12: Ray intersects a vertex
        assertNull(triangle.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 1, 0))), "Ray does not intersect the triangle");

        // TC13: Ray intersects edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point3D(-2, 0, 0),
                new Vector(1, 1, 0))), "Ray does not intersect the triangle");

    }
}