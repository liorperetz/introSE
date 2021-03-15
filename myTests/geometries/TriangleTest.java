package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Sphere
 *
 * @author Lior Peretz
 *
 */
class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle =new Triangle(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), triangle.getNormal(new Point3D(0, 0, 1)),"Bad normal to trinagle");
    }
}