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
    //point light location
    private final Point3D _position;

    //attenuation coefficients
    private double _Kc=1;//constant attenuation coefficient
    private double _Kl=0;//linear attenuation coefficient
    private double _Kq=0;//quadratic attenuation coefficients

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
