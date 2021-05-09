package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * RayTracerBasic is tracing rays from the camera to the scene
 * and calculate the color of the point the ray hits
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * RayTracerBase constructor
     * @param scene instance of Scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * get the color of a single pixel by sending ray from the camera through it
     * @param ray ray from the camera
     * @return the color of the pixel the ray hit in the view plane
     */
    @Override
    public Color traceRay(Ray ray) {

        List<Point3D> intersections=_scene._geometries.findIntersections(ray);
        //if the ray intersects geometry object calculate the pixel color
        if(intersections !=null){
            Point3D closestPoint=ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }
        //default color if the ray does not intersect any geometry
        return  _scene._background;
    }

    /**
     * calculate the color of single pixel in the view plane
     * considering different kinds of light sources
     * @param point point in the scene that viewed on the view plane
     * @return the color of the pixel that view the point
     */
    private Color calcColor(Point3D point) {
        return _scene._ambientLight.getIntensity();//for now the only color is the ambient light.
    }
}
