package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Lior Peretz
 */

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    /**
     * Test method to test thar zero vector cannot be created
     */
    void testZeroPoint(){
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException e) {
            out.println("vector 0 was not created");
        }
    }

    @Test
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}
     */
    void add() {
        Vector res=v1.add(v2);
        //Test that add() produce correct result
        assertEquals(new Vector(-1,-2,-3),res,"add() result is not correct");
    }

    @Test
    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}
     */
    void subtract() {
        Vector res=v1.subtract(v2);
        //Test that subtract() produce correct result
        assertEquals(new Vector(3,6,9),res,"subtract() result is not correct");
    }

    @Test
    /**
     * Test method for {@link primitives.Vector#scale()}
     */
    void scale() {
        //Test that scale() return correct vector
        Vector v1scale=v1.scale(-1);
        assertEquals(new Vector(-1,-2,-3),v1scale);

    }

    @Test
    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}
     */
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============

        //Test that dotProduct() produce correct value
        if (!Util.isZero(v1.dotProduct(v2) + 28.0D)) {
            System.out.println("ERROR: dotProduct() wrong value");
        }

        //Test that dot product between orthogonal vectors is zero
        double dotRes=v1.dotProduct(v3);
        assertEquals(0,alignZero(dotRes),"ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}
     */
    void lengthSquared() {

        // ============ Equivalence Partitions Tests ==============

        double v1SquaredLength=v1.lengthSquared();
        //Test that length return correct squared length
        assertEquals(14,v1SquaredLength,"lengthSquared() return incorrect value");
    }

    @Test
    /**
     * Test method for {@link primitives.Vector#length()}
     */
    void length() {

        // ============ Equivalence Partitions Tests ==============

        double v1Length=v1.length();
        //Test that length() return correct length
        assertEquals(14,v1Length*v1Length,"length() return incorrect value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}
     */
    @Test
    void normalize() {

        // ============ Equivalence Partitions Tests ==============

        Vector v = new Vector(1, 2, 3);
        Vector vNormalize = v.normalize();

        //Test that normalize() changes the vector
        if(v!=vNormalize)
             fail("ERROR: normalize() function creates a new vector");

        //Test that normalize() result is unit vector
        assertEquals(0,alignZero(vNormalize.length() - 1),"ERROR: normalize() result is not a unit vector");
    }

    @Test
    /**
     * Test method for {@link primitives.Vector#normalized()}
     */
    void normalized() {

        // ============ Equivalence Partitions Tests ==============

        Vector v = new Vector(1, 2, 3);
        Vector u=v.normalized();

        //Test that normalized produce unit vector
        assertEquals(0,alignZero(u.length()-1),"normalized() result is not unit vector");

        //Test that normalized() creates a new vector
        if (u==v)
            fail("normalized() does not create new vector");
    }
}