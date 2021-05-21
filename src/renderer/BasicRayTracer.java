package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * BasicRayTracer is tracing rays from the camera to the scene
 * and calculate the color of the point the ray hits
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class BasicRayTracer extends RayTracerBase {

    /**
     * BasicRayTracer constructor
     * @param scene instance of Scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * get the color of a single pixel by sending ray from the camera through it
     *
     * @param ray ray from the camera
     * @return the color of the pixel the ray hit in the view plane
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(ray);
        //if the ray intersects geometry calculate the color of the closest hit
        if (intersections != null) {
            GeoPoint closestGeoPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestGeoPoint, ray);
        }
        //default color if the ray does not intersect any geometry
        return _scene._background;
    }

    /**
     * calculate the color of single pixel in the view plane
     * considering different kinds of light sources
     * @param geoPoint point in the scene that viewed on the view plane
     * @return the color of the pixel that view the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        //add up self emission color and ambient light color
        Color color = _scene._ambientLight.getIntensity()
                .add(geoPoint._geometry.getEmission());
        //add up color's effects
        color = color.add(calcLocalEffects(geoPoint, ray));
        return color;
    }

    /**
     * calculate color's effects of single point
     * @param geoPoint point on geometry surface
     * @param ray ray from the camera to the point
     * @return calculated color with effects
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0){ //90 degrees angle between n and v
            return Color.BLACK;
        }
        Color color = Color.BLACK;
        //material qualities
        Material material=geoPoint._geometry.getMaterial();
        int nShininess = material._nShininess;
        double kd = material._Kd;
        double ks = material._Ks;
        //calculate color's effects from each light source
        for (LightSource lightSource : _scene._lights) {
            Vector l = lightSource.getL(geoPoint._point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(geoPoint._point);
                //add up diffusive and shininess effects
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * calculating diffusion color. formula: (Kd∙|l∙n|)∙lightIntensity
     * @param kd diffusion coefficient
     * @param l vector from the light source to the current point
     * @param n normal vector to the geometry in the current point
     * @param lightIntensity light's color intensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln=Math.abs(alignZero(l.dotProduct(n)));
        return lightIntensity.scale(kd*ln);
    }

    /**
     * calculating shininess color. formula: (Ks∙(−v∙r)^nShininess)∙lightIntensity
     * when r is  reflectance vector
     * @param ks shininess coefficient
     * @param l vector from the light source to the current point
     * @param n normal vector to the geometry in the current point
     * @param v vector from the camera to the point
     * @param nShininess object’s shininess
     * @param lightIntensity light's color intensity
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.subtract(n.scale(2*alignZero(l.dotProduct(n))));
        double minusVdotR=alignZero(v.scale(-1).dotProduct(r));
        double vrn=Math.pow(minusVdotR,nShininess);
        return lightIntensity.scale(ks*vrn);
    }
}
