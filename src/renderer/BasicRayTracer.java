package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
     * recursion depth counter when coloring points
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * minimal considered color change
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * initial coefficient for recursive calculations of
     * reflections and refractions
     */
    private static final double INITIAL_K = 1.0;

    private static final int AMOUNT_OF_RAYS = 500;

    /**
     * BasicRayTracer constructor
     *
     * @param scene instance of Scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * get the color of a single pixel by sending
     * ray from the camera through it and calculate it's color
     *
     * @param ray ray from the camera
     * @return the color of the pixel the ray hit in the view plane
     */
    @Override
    public Color traceRay(Ray ray) {
        //get the ray's closest intersection point and determine it's color
        GeoPoint closestPoint = findClosestIntersection(ray);
        //default color if the ray does not intersect any geometry
        return closestPoint == null ? _scene._background : calcColor(closestPoint, ray);
    }

    /**
     * find the ray's closest intersection with GeoPoint.
     *
     * @param ray ray from the camera
     * @return closest geoPoint intersection
     * (if there are no intersection return null)
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(ray);
        if (intersections != null) {
            return ray.findClosestGeoPoint(intersections);
        }
        return null;
    }

    /**
     * wrapper function to recursive function calcColor.
     * used to add the color the ambient light effect.
     *
     * @param gp  a GeoPoint point in the scene that viewed on the view plane
     * @param ray the ray that hits the geoPoint
     * @return the color of the pixel that view the geoPoint
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }

    /**
     * calculating the color of a geoPoint considering the geometry's color,
     * local effects, and global effects (global effects are calculating recursively.
     * the recursive call is at calcGlobalEffect function).
     *
     * @param geoPoint a GeoPoint point in the scene that viewed on the view plane
     * @param ray      the ray that intersects the geoPoint
     * @param level    maximum allowing recursion depth
     * @param k        color's initial coefficient that multiplied in each recursive calling
     * @return the color of the pixel that view the geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        //use geometry's self color as base
        Color color = (geoPoint._geometry.getEmission());
        //add up local effects to the color
        color = color.add(calcLocalEffects(geoPoint, ray, k));
        //add up global effects to the color until the leve went down to 1
        return level == 1 ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }

    /**
     * calculating color's effects from reflection and refraction rays
     *
     * @param gp    the geoPoint that it's color is calculated
     * @param v     normalized vector form the light source to the geoPoint
     * @param level recursion depth level
     * @param k     transparency and reflection coefficients multiplier
     * @return color from global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;//natural color as base
        Vector n = gp._geometry.getNormal(gp._point);//normal vector to the geometry's surface from the geoPoint
        Material material = gp._geometry.getMaterial();

        //reducing the reflection and transparency coefficients
        //in each recursion calling until the influence on the color
        // is smaller then MIN_CALC_COLOR_K
        double kkr = k * material._Kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray r = constructReflectedRay(gp._point, v, n);
            color = beamOfRays(r, n, level, material._Kr, kkr, material._kGlossy);
//              color = calcGlobalEffect(constructReflectedRay(gp._point, v, n), level, material._Kr, kkr);
        }
        double kkt = k * material._Kt;
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp._point, v, n), level, material._Kt, kkt));
        }
        return color;
    }

    /**
     * Calculate the color effect of the geometry
     * that intersected from the reflected or refracted ray
     * by calling recursively to calcColor function
     *
     * @param ray   the ray that hits the geoPoint
     * @param level recursion depth level
     * @param kx    either transparency or reflection coefficient of the current calculated geometry
     * @param kkx   either transparency or reflection coefficient from the last recursion level
     * @return calculated global effect's color
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        //if the ray intersect a geometry color is continued to be calculated
        //and if it not, return the default background color
        return (gp == null ? _scene._background : calcColor(gp, ray, level - 1, kkx).scale(kx));
    }

    /**
     * construct the reflected ray of the ray that hits the geometry.
     *
     * @param point  the point on the geometry surface the ray hit
     * @param v      direction vector of the ray from the camera
     * @param normal normal vector to the geometry from point
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector normal) {
        double vDotN = v.dotProduct(normal);
//        if(vDotN<0){
//            normal= normal.scale(-1);
//        }
        //r=v-2*(v*n)*n
        Vector r = v.subtract(normal.scale(2 * vDotN));
        return new Ray(point, r, normal);//using ray's constructor that moves
                                        // the point by delta at the normal direction
    }

    /**
     * construct the refracted ray of the ray that hits the geometry
     *
     * @param point  the point on the geometry surface the ray hit
     * @param v      direction vector of the ray from the camera
     * @param normal normal vector to the geometry from point
     * @return refracted ray
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector normal) {
        return new Ray(point, v, normal); //using ray's constructor that moves
        // the point by delta in the normal direction
    }

    private Color beamOfRays(Ray r, Vector n, int level, double kx, double kkx, double kPercent) {

        if (kPercent == 0) {
            return calcGlobalEffect(r, level, kx, kkx);
        }

        Point3D p0 = r.getP0();
        Vector rVector = r.getDir();
        Vector right;
        Vector up;
        if(rVector.dotProduct(n)>0) {
            //right = rVector.crossProduct(n).normalize();
            right = rVector.crossProduct(new Vector(0,0,1)).normalize();
        }
        else{
            //right = n.crossProduct(rVector).normalize();
            right = rVector.crossProduct(new Vector(0,0,1)).normalize();
        }
        //Vector up = right.crossProduct(rVector).normalize();
        up = right.crossProduct(rVector).normalize();
        rVector=rVector.scale(100);
        Point3D center = p0.add(rVector);
        Point3D upLeftCorner = center.add(up.scale(kPercent).add(right.scale(-kPercent)));

        int pixelsPerEdge = (int) Math.sqrt(AMOUNT_OF_RAYS);
        double pixelLen = kPercent / pixelsPerEdge;
        int sumOfRays = pixelsPerEdge * pixelsPerEdge;

        Vector down = up.scale(-1);
        Color color = Color.BLACK;
        for (int i = 0; i < pixelsPerEdge; i++) {
            for (int j = 0; j < pixelsPerEdge; j++) {
                double randomRightToScale = ThreadLocalRandom.current().nextDouble(0, pixelLen);
                double randomDownToScale = ThreadLocalRandom.current().nextDouble(0, pixelLen);
                Vector randomVector = right.scale(randomRightToScale + j*pixelLen).add(down.scale(randomDownToScale + i*pixelLen));
                Point3D randomPoint = upLeftCorner.add(randomVector);
                Vector randomRayDir = randomPoint.subtract(p0);

                if (alignZero(rVector.dotProduct(n)) * alignZero(randomRayDir.dotProduct(n)) > 0) {
                    Ray randomRay = new Ray(p0, randomRayDir);
                    color = color.add(calcGlobalEffect(randomRay, level, kx, kkx));
                } else {
                    sumOfRays--;
                }
            }
        }
        return color.reduce(sumOfRays);
    }

    /**
     * calculate color's local effects (diffusive and shininess)
     * of single geoPoint
     *
     * @param geoPoint point on geometry surface
     * @param ray      ray from the camera to the point
     * @return calculated color with effects
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) { //90 degrees angle between n and v, no local effects
            return Color.BLACK;
        }
        Color color = Color.BLACK;//basic natural color
        //get material qualities
        Material material = geoPoint._geometry.getMaterial();
        int nShininess = material._nShininess;
        double kd = material._Kd;
        double ks = material._Ks;
        //calculate color's effects from each light source
        for (LightSource lightSource : _scene._lights) {
            Vector l = lightSource.getL(geoPoint._point);//normalized vector from the light source to the geoPoint
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint._point).scale(ktr);
                    //add up diffusive and shininess effects
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * calculating diffusion color. formula: (Kd∙|l∙n|)∙lightIntensity
     *
     * @param kd             diffusion coefficient
     * @param l              vector from the light source to the current point
     * @param n              normal vector to the geometry in the current point
     * @param lightIntensity light's color intensity
     * @return diffusion color
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(alignZero(l.dotProduct(n)));
        return lightIntensity.scale(kd * ln);
    }

    /**
     * calculating shininess color.
     *
     * @param ks             shininess coefficient
     * @param l              vector from the light source to the current geoPoint
     * @param n              normal vector to the geometry in the current point
     * @param v              vector from the camera to the point
     * @param nShininess     object’s shininess
     * @param lightIntensity light's color intensity
     * @return shininess color
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        //formula: (Ks∙(−v∙r)^nShininess)∙lightIntensity
        //when r is  reflectance vector
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));//r=l-2*(l*n)*n
        double minusVdotR = alignZero(v.scale(-1).dotProduct(r));
        double vrn = Math.pow(minusVdotR, nShininess);
        return lightIntensity.scale(ks * vrn);
    }

    /**
     * calculating the effective transparency's coefficient of all the geometries
     * between a geoPoint to a light source
     *
     * @param lightSource the light source that ray from the geoPoint hit it
     * @param l           normalized vector from the light source to the geoPoint
     * @param n           normal vector to the geoPoint
     * @param geoPoint    a geoPoint in the scene
     * @return effective transparency's coefficient
     */
    private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint._point, lightDirection, n);//ray from delta moved geoPoint to the light source
        //check for ray hits on the way from the geoPoint to the light source
        List<GeoPoint> intersections = _scene._geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(geoPoint._point));
        if (intersections == null) {
            return 1d;
        }
        //multiplying all the transparency's coefficients of the intersected geoPoints
        double ktr = 1d;//transparency's coefficient
        for (GeoPoint gp : intersections) {
            ktr *= gp._geometry.getMaterial()._Kt;
            if (ktr < MIN_CALC_COLOR_K) {
                return 0d;//geometries are considered opaque
            }
        }
        return ktr;
    }
}
