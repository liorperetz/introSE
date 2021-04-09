package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class Triangle representing a triangle in a 3D cartesian coordinate system
 * the class inherit from Polygon
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Triangle extends Polygon {
    /**
     * Triangle constructor receiving 3 Point3D objects
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

/*    *//**
     * find intersections points of ray with a triangle
     * @param ray ray in 3d space
     * @return list of intersections points of the ray with the triangle
     *
     * Mathematical principle:
     * first of all check if the ray intersect the triangle's plane.
     * then create 3 vectors (v1,v2,v3) p0 to each vertex. these vectors with the triangle create a tetrahedron.
     * find normals to the tetrahedron's faces (N1=v1xv2,N2=v2xv3,N3=v3xv1).
     * now if the dot product from all v[ray direction] ∙ Ni have the same sign (+/-)
     * it means the intersection point is in the triangle.
     *//*
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Point3D p1=this.vertices.get(0);
        Point3D p2=this.vertices.get(1);
        Point3D p3=this.vertices.get(2);

        Plane plane=new Plane(p1,p2,p3); //plane is the triangle's plane
        List<Point3D> planeIntersections=plane.findIntersections(ray);

        if(planeIntersections != null){// if the ray does not intersect the triangle's plane return null

            Point3D p0=ray.getP0();
            Vector v=ray.getDir();

            Vector v1=p1.subtract(p0);
            Vector v2=p2.subtract(p0);
            Vector v3=p3.subtract(p0);

            Vector N1=v1.crossProduct(v2).normalize();
            Vector N2=v2.crossProduct(v3).normalize();
            Vector N3=v3.crossProduct(v1).normalize();

            double vN1=alignZero(v.dotProduct(N1));
            double vN2=alignZero(v.dotProduct(N2));
            double vN3=alignZero(v.dotProduct(N3));

            if(vN1==0 || vN2==0 || vN3==0){

                return null; //the ray intersect triangle's edge/vertex or edge's continuation

            }

            if((vN1>0 && vN2>0 && vN3>0) || (vN1<0 && vN2<0 && vN3<0)){ //when all vNi have the same sign it means the intersection point is in the plane

                return planeIntersections;
            }

            return null; // intersection point is not in the triangle
        }

        return null; //the ray does not intersect the triangle's plane
    }*/

}
