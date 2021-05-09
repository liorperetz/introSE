package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Camera class represents the location and the direction of the camera that watch the scene,
 * and the dimensions of view plane the scene is watched through
 *
 * @author Lior Peretz
 * @author Reuven Klein
 */
public class Camera {

    //location of the camera
    private final  Point3D _p0;

    //three orthogonal direction vectors relatively to p0
    private final Vector _vUp;
    private final Vector _vTo;
    private final Vector _vRight;

    //dimensions of the view plane
    private double _width;
    private double _height;

    //distance from the camera to the view plane
    private double _distance;

    /**
     * Camera constructor
     * @param p0 location of the camera
     * @param vTo normal vector to the view plane
     * @param vUp vector that orthogonal to vTo and points up from p0
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {

        if (!isZero(vTo.dotProduct(vUp)))// if vTo and vUp are not orthogonal throw exception
            throw new IllegalArgumentException("The vectors need to be orthogonal");

        _p0 = new Point3D(p0.getX(), p0.getY(), p0.getZ());
        _vTo = vTo.normalize();
        _vUp = vUp.normalize();
        _vRight = _vTo.crossProduct(_vUp);//vRight is the cross product vTo X vUp according to right hand rule
    }

    /**
     * p0 getter
     * @return reference to Point3D representing p0
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * vUp getter
     * @return reference to Vector representing vUp
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     * vTo getter
     * @return reference to Vector representing vTo
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     * vRight getter
     * @return reference to Vector representing vRight
     */
    public Vector get_vRight() {
        return _vRight;
    }

    /**
     * setter to the view plane width and length (chaining method)
     * @param width width of the view plane
     * @param height length of the view plane
     * @return current Camera instance
     */
    public Camera setViewPlaneSize(double width, double height){

        _width=width;
        _height=height;
        return this;
    }

    /**
     * setter to distance (chaining method)
     * @param distance distance from the camera to the view plane
     * @return current Camera instance
     */
    public Camera setDistance(double distance){

        _distance=distance;
        return this;
    }

    /**
     * find ray that starts at p0 and go through the center of specific pixel in the view plane
     * @param nX view plane's pixels number in x axis (columns)
     * @param nY view plane's pixels number in Y axis (rows)
     * @param j pixel's column index
     * @param i pixel's row index
     * @return reference to Ray representing a ray that starts at p0 go through the center of the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

        Point3D pc=_p0.add(_vTo.scale(_distance)); //Pc=P0+d*Vto Pc is the center point of the view plane

        //size of the pixels in y axis and x axis
        double Ry=_height/nY;
        double Rx=_width/nX;

        //Center of the P[i,j] pixel
        Point3D Pij=pc; //for case that pc is exactly Pij
        double Yi = -(i - (nY-1) / 2d) * Ry;//distance from pc to the center of p[i,j] in y axis
        double Xj = (j - (nX-1) / 2d) * Rx;//distance from pc to the center of p[i,j] in x axis

        if (!isZero(Xj)) {//if p[i,j] is not in the same row of pc change P[i,j] x coordinate
            Pij = Pij.add(_vRight.scale(Xj));
        }

        if (!isZero(Yi)) {//if p[i,j] is not in the same column of pc change P[i,j] x coordinate
            Pij = Pij.add(_vUp.scale(Yi));
        }

        Vector Vij=Pij.subtract(_p0);//vector from p0 to pij

        return new Ray(_p0,Vij);//ray that starts at p0 and go through the center of pixel[i,j]

    }

}

