package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * SpotLight represents a point light source with direction
 *
 *  author Reuven Klein
 *  @author Lior Peretz
 */
public class SpotLight extends PointLight{
    //spotLight direction vector
    private final Vector _direction;

    /**
     * SpotLight constructor
     * @param intensity color's light intensity
     * @param position location of the spotLight
     * @param direction direction vector
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * get the PointLight contribution to the color's intensity of a point
     * the intensity attenuation formula:
     * i=(I0*max(0,direction∙L)/(kc+kl*d+kq*d^2)
     * when d is the distance from position to p, direction is the spot's
     * vector direction and L is vector from position to p
     *
     * @param p Point3D in space
     * @return color's intensity by PointLight
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dirDotL=_direction.dotProduct(getL(p)); //direction∙L
        Color intensity=super.getIntensity(p);// I0/(kc+kl*d+kq*d^2)
        return intensity.scale(Math.max(0,dirDotL));
    }


}
