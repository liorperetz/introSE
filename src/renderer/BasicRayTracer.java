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
    /**
     * desired amount of rays in a beam
     */
    private static final int AMOUNT_OF_RAYS = 1089;
    /**
     * adaptive supersampling switch
     */
    boolean adaptiveSuperSampling;
    /**
     * adaptive super sampling recursion max depth
     */
    private static final int MAX_SUPER_SAMPLING_LEVEL = 6;
    /**
     * BasicRayTracer constructor
     *
     * @param scene instance of Scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * adaptive super sampling switch
     * @param adaptiveSuperSampling  true to turn on adaptiveSuperSampling feature
     * @return current BasicRayTracer instance
     */
    public BasicRayTracer setAdaptiveSuperSampling(boolean adaptiveSuperSampling) {
        this.adaptiveSuperSampling = adaptiveSuperSampling;
        return this;
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
            color = calcColorFromBeamOfRays(r, n, level, material._Kr, kkr, material._kGlossy);
        }
        double kkt = k * material._Kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray r = constructRefractedRay(gp._point, v, n);
            color = color.add(
                    calcColorFromBeamOfRays(r, n, level, material._Kt, kkt, material._kClear));
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

    /**
     * calculating recursively the global color effect of geoPoint
     * from beam of rays that scatter around a central ray.
     *
     * @param r              the central ray
     * @param n              normal to the geoPoint
     * @param level          recursion depth
     * @param kx             either transparency or reflection coefficient of the current calculated geometry
     * @param kkx            either transparency or reflection coefficient from the last recursion level
     * @param kGlossyOrClear coefficient of glossy or clear
     * @return average color from the beam of the rays
     */
    private Color calcColorFromBeamOfRays(Ray r, Vector n, int level, double kx, double kkx, double kGlossyOrClear) {

        if (kGlossyOrClear == 100) {//if kGlossy is 100 the surface is perfect mirror and
                                    //if KClear is 100 the surface is perfect transparent
            return calcGlobalEffect(r, level, kx, kkx);
        }
        double scatteringWidth = 100 - kGlossyOrClear;//scatteringWidth determines the edge's length of the
                                                     // target surface the rays are sent to.
                                                    // the more glossy and clear the material is, the rays less scatters.
        //building a target surface to which rays will be sent
        TargetSurface targetSurface=new TargetSurface(r,_upVector.normalize(),scatteringWidth*2);

        //reflection/refraction ray parameters
        Point3D p0 = r.getP0();
        Vector rVector = r.getDir();

        rVector = rVector.scale(100);//Set the target surface at a distance of 100 from the starting point

        if(adaptiveSuperSampling){
            //use adaptive super sampling
            return adaptiveSuperSampling(kx,kkx,level,p0,targetSurface);
        }
        //don't use adaptive super sampling
        return colorFromRegularBeam(rVector,level,kx,kkx,p0,n,targetSurface);
    }

    /**
     * calculate the color of reflection/refraction ray
     * to glossy surfaces/diffused glass when adaptive super sampling is not activated
     * @param rVector reflection/refraction vector
     * @param level reflection/refraction recursion depth
     * @param kx either transparency or reflection coefficient of the current calculated geometry
     * @param kkx either transparency or reflection coefficient from the last recursion level
     * @param p0 source point of the reflection/refraction ray
     * @param n normal to the geoPoint
     * @param targetSurface ray's target surface
     * @return
     */
    private Color colorFromRegularBeam(Vector rVector, int level, double kx, double kkx, Point3D p0, Vector n,TargetSurface targetSurface){

        //calculate the actual amount of rays (must have an integer square root)
        int squaresPerEdge = (int) Math.sqrt(AMOUNT_OF_RAYS);
        int sumOfRays = squaresPerEdge * squaresPerEdge;
        //length of each square in the grid of the target plane
        double squareLength = (targetSurface.getEdgeLen()) / squaresPerEdge;
        Color color = Color.BLACK;
        //divide the target surface to squared grid
        //and send random ray to each square in it
        for (int i = 0; i < squaresPerEdge; i++) {
            for (int j = 0; j < squaresPerEdge; j++) {
                //scaling the right and down vectors in random value
                //in order to reach to a random point at the square
                //and create ray from p0 to it
                double randomRightToScale = ThreadLocalRandom.current().nextDouble(0, squareLength);
                double randomDownToScale = ThreadLocalRandom.current().nextDouble(0, squareLength);
                Vector randomVector = targetSurface.getRight().scale(randomRightToScale + j * squareLength).
                        add(targetSurface.getDown().scale(randomDownToScale + i * squareLength));
                Point3D randomPoint = targetSurface.getTopLeftPoint().add(randomVector);
                Vector randomRayDir = randomPoint.subtract(p0);

                //if the ray does not pass the surface to the other side
                // calculate the global color effect from it, else ignore it.
                if (alignZero(rVector.dotProduct(n)) * alignZero(randomRayDir.dotProduct(n)) > 0) {
                    Ray randomRay = new Ray(p0, randomRayDir);
                    color = color.add(calcGlobalEffect(randomRay, level, kx, kkx));
                } else {
                    sumOfRays--;
                }
            }
        }
        return color.reduce(sumOfRays);//average color from all rays

    }

    /**
     * calculate the color of reflection/refraction using adaptive super sampling
     * wrapper to recursive method
     * @param kx either transparency or reflection coefficient of the current calculated geometry
     * @param kkx either transparency or reflection coefficient from the last recursion level
     * @param level reflection/refraction recursion depth
     * @param p0 source point of the reflection/refraction ray
     * @param targetSurface ray's target surface
     * @return color of the the reflection/refraction source point
     */
    private Color adaptiveSuperSampling(double kx, double kkx, int level, Point3D p0, TargetSurface targetSurface){
        //create and initialize colors matrix. used to reduce redundant calculations
        int colorMatrixDimension=(int)Math.pow(2,MAX_SUPER_SAMPLING_LEVEL-1)+1;//(colorMatrixDimension)^2 is maximum samples number
        Color[][]colors=new Color[colorMatrixDimension][colorMatrixDimension];
        //initialize to null
        for (int i = 0; i <colorMatrixDimension ; i++) {
            for (int j = 0; j < colorMatrixDimension; j++) {
                colors[i][j]=null;
            }
        }
        //edge's length of each square in the target surface (depends recursive max depth)
        double squareLen=targetSurface.getEdgeLen()/(colorMatrixDimension-1);
        targetSurface.setSquareLen(squareLen);

        //first recursive call. send the indexes of the four vertexes of the target surface
        Index2D topLeft=new Index2D(0,0);
        Index2D topRight=new Index2D(0,colorMatrixDimension-1);
        Index2D bottomLeft=new Index2D(colorMatrixDimension-1,0);
        Index2D bottomRight=new Index2D(colorMatrixDimension-1,colorMatrixDimension-1);
        return adaptiveSuperSampling(colors,MAX_SUPER_SAMPLING_LEVEL,topLeft,topRight,bottomLeft, bottomRight,kx,kkx, level,p0,targetSurface);

    }

    /**
     * Index2D is an internal helper class
     * representing a 2D index (x,y) in a matrix
     */
    private class Index2D {
        /**
         * rows index
         */
       private int _x;
        /**
         * column index
         */
       private int _y;

        /**
         * Index2D constructor
         * @param x row index
         * @param y column index
         */
       public Index2D(int x, int y){
            _x=x;
            _y=y;
        }
    }

    /**
     * calculate recursively, using adaptive super sampling, the average color of the
     * target surface for glossy surfaces/diffused glass
     * reflection
     * @param colors matrix of calculated colors
     * @param superSamplingLevel super sampling recursion depth
     * @param topLeft Index (in colors) of the top left vertex in the square
     * @param topRight Index (in colors) of the top right vertex in the square
     * @param bottomLeft Index (in colors) of the bottom left vertex in the square
     * @param bottomRight Index (in colors) of the bottom right vertex in the square
     * @param kx either transparency or reflection coefficient of the current calculated geometry
     * @param kkx either transparency or reflection coefficient from the last recursion level
     * @param level reflection/refraction recursion depth
     * @param p0 source point of the reflection/refraction ray
     * @param targetSurface ray's target surface
     * @return color of the the reflection/refraction source point
     */
    private Color adaptiveSuperSampling(Color[][] colors, int superSamplingLevel, Index2D topLeft, Index2D topRight, Index2D bottomLeft, Index2D bottomRight, double kx, double kkx, int level, Point3D p0, TargetSurface targetSurface){
        Color color=Color.BLACK;//basic natural color

        //try to get colors of the vertexes from the colors array (get null if the don't exist yet)
        Color colorTopLeft=colors[topLeft._x][topLeft._y];
        Color colorTopRight=colors[topRight._x][topRight._y];
        Color colorBottomLeft=colors[bottomLeft._x][bottomLeft._y];
        Color colorBottomRight=colors[bottomRight._x][bottomRight._y];

        //if the color does not exist in the color's array then calculate it and save it in the array
        if(colorBottomRight==null){
            colorBottomRight= calcColor(p0,targetSurface,bottomRight,level,kx,kkx);
            colors[bottomRight._x][bottomRight._y]=colorBottomRight;
        }
        if(colorBottomLeft==null){
            colorBottomLeft= calcColor(p0,targetSurface,bottomLeft,level,kx,kkx);
            colors[bottomLeft._x][bottomLeft._y]=colorBottomLeft;
        }
        if(colorTopRight==null){
            colorTopRight= calcColor(p0,targetSurface,topRight,level,kx,kkx);
            colors[topRight._x][topRight._y]=colorTopRight;
        }
        if(colorTopLeft==null){
            colorTopLeft= calcColor(p0,targetSurface,topLeft,level,kx,kkx);
            colorTopLeft=colors[topLeft._x][topLeft._y]=colorTopLeft;
        }

        //if the 4 colors are similar return their average
        if(colorBottomRight.equals(colorBottomLeft)&&
                colorBottomLeft.equals(colorTopRight)&&
                colorTopRight.equals(colorTopLeft)){
            return colorBottomRight.add(colorBottomLeft,colorTopRight,colorTopLeft).reduce(4);
        }

        //if the colors are not similar sample smaller area as long as the superSamplingLevel bigger then 1
        if(superSamplingLevel>=1) {

            //indexes of each middle edge and central point
            Index2D middleTop = new Index2D(topLeft._x, (topLeft._y + topRight._y) / 2);
            Index2D middleRight = new Index2D((topRight._x + bottomRight._x) / 2, topRight._y);
            Index2D middleLeft = new Index2D((topLeft._x + bottomLeft._x) / 2, topLeft._y);
            Index2D middleBottom = new Index2D(bottomLeft._x, (bottomLeft._y + bottomRight._y) / 2);
            Index2D center = new Index2D((topLeft._x + bottomLeft._x) / 2, (topLeft._y + topRight._y) / 2);

            //4 recursive calls, each one to quarter square. decreasing the recursive level by 1
            color = color.add(adaptiveSuperSampling(colors, superSamplingLevel - 1, topLeft, middleTop, middleLeft, center, kx, kkx, level, p0,targetSurface));
            color = color.add(adaptiveSuperSampling(colors, superSamplingLevel - 1, middleTop, topRight, center, middleRight, kx, kkx, level, p0,targetSurface));
            color = color.add(adaptiveSuperSampling(colors, superSamplingLevel - 1, middleLeft, center, bottomLeft, middleBottom, kx, kkx, level, p0,targetSurface));
            color = color.add(adaptiveSuperSampling(colors, superSamplingLevel - 1, center, middleRight, middleBottom, bottomRight, kx, kkx, level, p0,targetSurface));
            //return the average color
            return color.reduce(4);
        }
        //return the average color of the 4 vertexes
        return colorBottomLeft.add(colorBottomRight,colorTopRight,colorTopLeft).reduce(4);

    }


    /**
     * invoking calcGlobalEffect to point on the target surface of glossy/diffused
     * @param p0 source point of the reflection/refraction ray
     * @param targetSurface ray's target surface
     * @param index index in the color's array
     * @param level reflection/refraction recursion depth
     * @param kx either transparency or reflection coefficient of the current calculated geometry
     * @param kkx kkx either transparency or reflection coefficient from the last recursion level
     * @return color of the point on the target surface
     */
    public Color calcColor(Point3D p0,TargetSurface targetSurface, Index2D index, int level, double kx, double kkx){

        //get from the top left corner vertex of the target surface to
        //the target point and use calcGlobalEffect to calc the color
        Point3D targetPoint=targetSurface.getTopLeftPoint();
        if(index._y>0){
            //add scaled right vector
            targetPoint=targetPoint.add(targetSurface.getRight().scale(index._y*targetSurface.getSquareLen()));
        }
        if(index._x>0){
            //add scales down vector
            targetPoint=targetPoint.add(targetSurface.getDown().scale(index._x*targetSurface.getSquareLen()));
        }
        Ray ray=new Ray(p0,targetPoint.subtract(p0));
        return calcGlobalEffect(ray,level,kx,kkx);

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
