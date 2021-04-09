package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon implements Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point3D point) {
        return plane.getNormal();
    }

    /**
     * find intersections points of ray with a polygon
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the polygon
     *
     * Mathematical principle:
     * first of all check if the ray intersect the triangle's plane.
     * then create vectors (v1,v2,v3...vn) from p0 to each vertex.
     * these vectors with the polygon create a pyramid (the polygon is the base).
     * find normals to the pyramid's faces (N1=v1xv2,N2=v2xv3...Nn=vnxv1).
     * now if the dot product from all v[ray direction] ∙ Ni have the same sign (+/-)
     * it means the intersection point is in the triangle.
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Plane plane=new Plane(vertices.get(0),vertices.get(1),vertices.get(2));
        List <Point3D> planeIntersection= plane.findIntersections(ray);
        if(planeIntersection!=null){

            Point3D p0=ray.getP0();
            Vector v=ray.getDir();

            //v∙N1
            double vDotNiPrev=alignZero(v.dotProduct(vertices.get(0).subtract(p0).crossProduct(vertices.get(1).subtract(p0)).normalize()));
            double vDotNiNext;
            for(int j=1;j<vertices.size()-1;j++){

               vDotNiNext=alignZero(v.dotProduct(vertices.get(j).subtract(p0).crossProduct(vertices.get(j+1).subtract(p0)).normalize()));

               if(vDotNiPrev<0 && vDotNiNext<0 || vDotNiPrev>0 && vDotNiNext>0){
                   //if v∙Ni and v∙N(i+1) have the same sign continue, else return null

                   vDotNiPrev=vDotNiNext;
                   continue;
               }
               return null;

            }
            //check if v∙Nn (Nn=vnxv1) also have the same sign
            vDotNiNext=alignZero(v.dotProduct(vertices.get(vertices.size()-1).subtract(p0).crossProduct(vertices.get(0).subtract(p0)).normalize()));
            if(vDotNiPrev<0 && vDotNiNext<0 || vDotNiPrev>0 && vDotNiNext>0){
                return planeIntersection;
            }
            return null;

        }

        return null;//ray does not intersect the polygon's plane
    }
}
