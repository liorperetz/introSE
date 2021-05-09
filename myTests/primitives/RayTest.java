package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 *
 *  @author Lior Peretz
 *  @author Reuven Klein
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List<Point3D>)}
     */
    @Test
    void findClosestPoint() {

       Ray ray=new Ray(new Point3D(1,1,1),new Vector(1,2,3));
        List<Point3D> lst=new LinkedList<>();
        // ============ Equivalence Partitions Tests ==============

        // TC01: The closest point is in the middle of the list
        lst.add(new Point3D(12,11,10));
        lst.add(new Point3D(2,3,4));
        lst.add(new Point3D(9,8,7));
        assertEquals(lst.get(1),ray.findClosestPoint(lst),"wrong point");

        // =============== Boundary Values Tests ==================

        // TC11: The closest point is in the end of the list
        lst.add(new Point3D(12,11,10));
        lst.add(2,new Point3D(2,3,4));
        lst.add(1,new Point3D(9,8,7));
        assertEquals(lst.get(2),ray.findClosestPoint(lst),"wrong point");

        // TC12: The closest point is in the beginning of the list
        lst.add(2,new Point3D(12,11,10));
        lst.add(0,new Point3D(2,3,4));
        lst.add(new Point3D(9,8,7));
        assertEquals(lst.get(0),ray.findClosestPoint(lst),"wrong point");

        // TC13: The list is empty
        lst=null;
        assertNull(ray.findClosestPoint(lst),"method supposed to return null");
    }
}