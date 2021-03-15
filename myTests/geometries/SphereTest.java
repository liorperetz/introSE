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
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sphere =new Sphere(3,new Point3D(0,0,0));
        assertEquals(new Vector(1,0,0),sphere.getNormal(new Point3D(3,0,0)),"Bad normal to Sphere");
    }
}