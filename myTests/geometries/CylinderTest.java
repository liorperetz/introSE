package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Cylinder
 *
 * @author Lior Peretz
 * @author Reuven Klein
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        Cylinder cylinder=new Cylinder(2,new Ray(new Point3D(0,0,-2),new Vector(0,0,1)),4);

        // ============ Equivalence Partitions Tests ==============

        // TC01: the point is not at one of the bases
        assertEquals(new Vector(0,-1,0),cylinder.getNormal(new Point3D(0,-2,0)),"Wrong vector");

        // TC02: The point is at the same base as p0
        assertEquals(new Vector(0,0,-1),cylinder.getNormal(new Point3D(1,1,-2)),"Wrong vector");

        // TC03: The point is at the other base
        assertEquals(new Vector(0,0,1),cylinder.getNormal(new Point3D(-1,-1,2)),"Wrong vector");

        // =============== Boundary Values Tests ==================

        // TC11: The point is on the border of the first base (where p0 is)
        assertEquals(new Vector(0,0,-1),cylinder.getNormal(new Point3D(2,0,-2)),"Wrong vector");

        // TC12: The point is on the border of the second base
        assertEquals(new Vector(0,0,1),cylinder.getNormal(new Point3D(2,0,2)),"Wrong vector");
    }
}