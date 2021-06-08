package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight represents an omni-directional point light source
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class PointLight extends Light implements LightSource{
    /**
     * point light location
     */
    private final Point3D _position;

    //attenuation coefficients
    /**
     * constant attenuation coefficient
     */
    private double _Kc=1;
    /**
     * linear attenuation coefficient
     */
    private double _Kl=0;
    /**
     * quadratic attenuation coefficients
     */
    private double _Kq=0;

    /**
     * PointLight constructor
     * @param intensity color's light intensity
     * @param position location of the pointLight
     */
    public PointLight(Color intensity,Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * get the PointLight contribution to the color's intensity of a point
     * the intensity attenuation formula:
     * i=I0/(kc+kl*d+kq*d^2) when d is the distance from position to p
     * @param p Point3D in space
     * @return color's intensity by PointLight
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d=_position.distance(p);
        //calculation attenuation
        double attenuation=1d/(_Kc+_Kl*d+_Kq*d*d);
        return _intensity.scale(attenuation);
    }

    /**
     * get vector from PointLight position to  another point
     * @param p Point3D in space
     * @return vector from position to p
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    /**
     * distance from point3D to the PointLight
     * @param point Point3D
     * @return distance from the point to the PointLight position point
     */
    @Override
    public double getDistance(Point3D point) {
        return point.distance(_position);
    }

    //chaining setters methods
    public PointLight setKc(double kc) {
        _Kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }
}
