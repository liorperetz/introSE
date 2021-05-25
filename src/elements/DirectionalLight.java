package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight represents a powerful
 * light source that it's intensity does not
 * attenuate by distance
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class DirectionalLight extends Light implements LightSource {
    //direction vector of the  DirectionalLight
    private final Vector _direction;

    /**
     * DirectionalLight constructor
     * @param intensity color's intensity
     * @param direction light direction vector
     */
    protected DirectionalLight(Color intensity,Vector direction) {
        super(intensity);
        _direction=direction.normalized();
    }

    /**
     * get the DirectionalLight contribution to
     * the color's intensity of a point
     * (DirectionalLight produce the same intensity for all points)
     * @param p Point3D in space
     * @return color's intensity by DirectionalLight
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * get the direction vector of the DirectionalLight
     * @param p Point3D in space
     * @return direction vector (same for all points)
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * distance from point3D to the DirectionalLight
     * @param point Point3D
     * @return distance from the point to the DirectionalLight
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY; //the distance is assumed to be infinite
    }
}
