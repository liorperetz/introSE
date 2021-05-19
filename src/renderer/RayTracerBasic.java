package renderer;

import geometries.Intersectable.GeoPoint;
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

        List<GeoPoint> intersections=_scene._geometries.findGeoIntersections(ray);
        //if the ray intersects geometry calculate the color of the closest hit
        if(intersections !=null){
            GeoPoint closestGeoPoint=ray.findClosestGeoPoint(intersections);
            return calcColor(closestGeoPoint);
        }
        //default color if the ray does not intersect any geometry
        return  _scene._background;
    }

    /**
     * calculate the color of single pixel in the view plane
     * considering different kinds of light sources
     * @param geoPoint point in the scene that viewed on the view plane
     * @return the color of the pixel that view the point
     */
    private Color calcColor(GeoPoint geoPoint) {
        return _scene._ambientLight.getIntensity()
                .add(geoPoint._geometry.getEmission());
    }
}
