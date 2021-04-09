
package geometries;



import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */
public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(Point3D... vertices)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to trinagle");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {

        Polygon polygon=new Polygon(new Point3D(1,1,0),new Point3D(1,-1,0),
                new Point3D(0,-1,1),new Point3D(0,1,1));
        List<Point3D> result;

        // ============ Equivalence Partitions Tests ==============
        // in all cases we assume the ray intersects the polygon's plane

        // TC01: The ray intersects the polygon  (1 point)
        Point3D p1 = new Point3D(0.75, 0.5, 0.25);
        result = polygon.findIntersections(new Ray(new Point3D(0.5, 0.5, 0),
                new Vector(1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "wrong intersection point");

        // TC02: The ray intersects outside the polygon against an edge
        assertNull(polygon.findIntersections(new Ray(new Point3D(0, 2, 0),
                new Vector(1, 0, 1))), "Ray does not intersect the polygon");

        // TC03:The ray intersects outside the polygon against a vertex
        assertNull(polygon.findIntersections(new Ray(new Point3D(-2, 2, 0),
                new Vector(1, 0, 1))), "Ray does not intersect the polygon");

        // =============== Boundary Values Tests ==================

        // TC11: Ray intersects the polygon's edge
        assertNull(polygon.findIntersections(new Ray(new Point3D(-1, 0.5, 0),
                new Vector(1, 0, 1))), "Ray does not intersect the polygon");

        // TC12: Ray intersects a vertex
        assertNull(polygon.findIntersections(new Ray(new Point3D(-1, 1, 0),
                new Vector(1, 0, 1))), "Ray does not intersect the polygon");

        // TC13: Ray intersects edge's continuation
        assertNull(polygon.findIntersections(new Ray(new Point3D(-1, 2, 0),
                new Vector(1, 0, 1))), "Ray does not intersect the polygon");
    }
}
