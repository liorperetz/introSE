package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Tube
 *
 * @author Lior Peretz
 *
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tube=new Tube(3,new Ray(new Point3D(0,0,-1),new Vector(0,0,1)));
        assertEquals(new Vector(1,0,0),tube.getNormal(new Point3D(3,0,0)));
    }
}