package renderer;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * TargetSurface class represents an imaginary squared surface
 * used to send rays to it when the color of glossy surfaces/diffused glass is calculated
 *
 *  @author Reuven Klein
 *  @author Lior Peretz
 */
public class TargetSurface {

    /**
     * the top left point of the target surface
     */
    private final Point3D _topLeftPoint;
    /**
     * the edge length of the target surface
     */
    private final double _edgeLen;
    /**
     * length of each square in the target surface
     */
    private double _squareLen;
    /**
     * right direction vector
     */
    private  Vector _right;
    /**
     * down direction vector
     */
    private  Vector _down;

    /**
     * TargetSurface constructor
     * @param ray reflection/refraction ray
     * @param upVector up vector of the view plane
     * @param edgeLen length of the target surface edge
     */
    public TargetSurface(Ray ray,Vector upVector,double edgeLen ) {
        //calculate the directions vectors of the target surface
         _right = ray.getDir().crossProduct(upVector.normalize());
        _down = _right.crossProduct(ray.getDir()).normalize().scale(-1);
        //get from the reflection/refraction source point to the up left vertex of the target surface
        Point3D center = ray.getP0().add(ray.getDir().scale(100));
        _topLeftPoint = center.add(_down.scale(-(edgeLen/2)).add(_right.scale(-(edgeLen/2))));
        _edgeLen=edgeLen;
    }

    /**
     * topLeftPoint getter
     * @return TopLeftPoint
     */
    public Point3D getTopLeftPoint() {
        return _topLeftPoint;
    }

    /**
     * right vector getter
     * @return right vector
     */
    public Vector getRight() {
        return _right;
    }

    /**
     * down vector getter
     * @return down vector
     */
    public Vector getDown() {
        return _down;
    }

    /**
     * edgeLen getter
     * @return edgeLen
     */
    public double getEdgeLen() {
        return _edgeLen;
    }

    /**
     * squareLen getter
     * @return squareLen
     */
    public double getSquareLen() {
        return _squareLen;
    }

    /**
     * squareLen setter
     * @param squareLen length of each square in the target surface
     */
    public void setSquareLen(double squareLen) {
        this._squareLen = squareLen;
    }
}
