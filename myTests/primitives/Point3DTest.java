package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Point3D testing
 */
class Point3DTest {

    @Test
    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}
     */
    void distance() {
        Point3D point1=new Point3D(1,2,3);
        Point3D point2=new Point3D(4,5,6);
        double distance= point1.distance(point2);
        assertEquals(Math.sqrt(27),distance,"wrong distance");

    }
}