package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource is interface to different
 * light sources illuminating the scene
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public interface LightSource {

    /**
     * get the contribution of the light source
     * to the color intensity of a point in a scene
     * @param p Point3D in space
     * @return color's intensity of p
     */
    public Color getIntensity(Point3D p);

    /**
     * get vector from light source
     * to point in the scene
     * @param p Point3D in space
     * @return vector from the light source to p
     */
    public Vector getL(Point3D p);


    double getDistance(Point3D point);
}
