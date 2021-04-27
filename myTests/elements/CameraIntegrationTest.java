package elements;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing integration between creating ray from Camera to find intersection with Geometries
 *
 *  @author Lior Peretz
 *  @author Reuven Klein
 */
public class CameraIntegrationTest {

    //Camera object to test intersections of rays that starts from it
    Camera cam;

    /**
     * calculate all intersections points of Rays that start at a Camera
     * and go through the center of the pixels in the view plane with a geometry
     * @param Nx number of pixels at x axis
     * @param Ny number of pixels at y axis
     * @param shape shape from geometries
     * @return list of intersections points with the shape
     */
    private int intersectionPointsNumber(int Nx, int Ny,Intersectable shape){

        int intersectionsNumber=0;
        //iterate all view plane's pixels and count intersection points
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                List<Point3D> lst=shape.findIntersections(cam.constructRayThroughPixel(Nx,Ny,j,i));
                if(lst!=null) {
                    intersectionsNumber += lst.size();
                }
            }
        }
        return intersectionsNumber;
    }

    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel}, {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    void sphereIntersections(){

        // TC01: intersection through 1 pixel
        Sphere sphere=new Sphere(1,new Point3D(0,0,-3));
        cam=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0)).
                setDistance(1).
                setViewPlaneSize(3,3);
        assertEquals(2,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC02: intersection through all pixels
        sphere=new Sphere(2.5,new Point3D(0,0,-2.5));
        cam=new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0)).
                setDistance(1).
                setViewPlaneSize(3,3);
        assertEquals(18,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC03: intersection through 3 pixels in the middle column
        sphere=new Sphere(2,new Point3D(0,0,-2));
        assertEquals(10,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC04: the view plane is in the sphere, intersection through all 9 pixels
        sphere=new Sphere(4,new Point3D(0,0,-1));
        cam=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0)).
                setDistance(1).
                setViewPlaneSize(3,3);
        assertEquals(9,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");

        // TC05: no intersection points
        sphere=new Sphere(0.5,new Point3D(0,0,1));
        assertEquals(0,intersectionPointsNumber(3,3,sphere),
                "Wrong number of intersection points");
    }

    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel}, {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    void planeIntersections(){

        // TC01: the plane is parallel to the view plane, intersection through all 9 pixels
        Plane plane=new Plane(new Point3D(0,0,-2),new Vector(0,0,-1));
        cam=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0)).
                setDistance(1).
                setViewPlaneSize(3,3);
        assertEquals(9,intersectionPointsNumber(3,3,plane),
                "Wrong number of intersection points");

        // TC02: the plane intersects the view plane at the top (ray intersects through 9 pixels)
        plane=new Plane(new Point3D(0,1.5,-1),new Point3D(0,0,-2),new Point3D(1,0,-2));
        assertEquals(9,intersectionPointsNumber(3,3,plane),
                "Wrong number of intersection points");

        // TC03: the plane intersects the view plane between two rows (ray intersects through 6 pixels
        plane=new Plane(new Point3D(0,0.5,-1),new Point3D(0,0,-2),new Point3D(1,0,-2));
        assertEquals(6,intersectionPointsNumber(3,3,plane),
                "Wrong number of intersection points");

    }

    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel}, {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void triangleIntersections(){

        // TC01: intersection through the central pixel only
        Triangle triangle=new Triangle(new Point3D(0,1,-2),
                new Point3D(-1,-1,-2),new Point3D(1,-1,-2));
        cam=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0)).
                setDistance(1).
                setViewPlaneSize(3,3);

        assertEquals(1,intersectionPointsNumber(3,3,triangle),
                "Wrong number of intersection points");

        // TC01: intersection through 2 pixels in the central column
        triangle=new Triangle(new Point3D(0,20,-2),
                new Point3D(-1,-1,-2),new Point3D(1,-1,-2));

        assertEquals(2,intersectionPointsNumber(3,3,triangle),
                "Wrong number of intersection points");



    }

}
