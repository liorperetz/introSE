package primitives;

/**
 * class Point3D representing a point in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Point3D {
    /**
     * x axis coordinate
     */
    final Coordinate _x;
    /**
     * y axis coordinate
     */
    final Coordinate _y;
    /**
     * z axis coordinate
     */
    final Coordinate _z;
    /**
     * 3D point constant (0,0,0) use for comparisons
     */
    final public static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    /**
     * Point3D constructor receiving 3 coordinates values
     *
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {

        _x = new Coordinate(x.coord);
        _y = new Coordinate(y.coord);
        _z = new Coordinate(z.coord);
    }

    /**
     * Point3D constructor receiving 3 double values
     *
     * @param x value for x axis
     * @param y value for y axis
     * @param z value for z axis
     */
    public Point3D(double x, double y, double z) {

        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" +
                _x.toString() +
                ", " + _y.toString() +
                ", " + _z.toString() +
                ")";
    }

    /**
     * subtraction between two 3D points
     *
     * @param p1 a 3D point
     * @return reference to vector from p1 to current point
     */
    public Vector subtract(Point3D p1) {

        if (p1.equals((this))) //subtraction between identical points gives illegal vector (0,0,0)
            throw new IllegalArgumentException("cannot create vector to point (0,0,0)");

        return new Vector(new Point3D(_x.coord - p1._x.coord,
                _y.coord - p1._y.coord,
                _z.coord - p1._z.coord)

        );
    }

    /**
     * add a 3D vector to 3D point
     *
     * @param p1 a 3D point
     * @return a reference to 3D point after addition
     */
    public Point3D add(Vector p1) {
        return new Point3D(this._x.coord + p1._head._x.coord,
                this._y.coord + p1._head._y.coord,
                this._z.coord + p1._head._z.coord
        );
    }

    /**
     * calculate the Euclidean squared distance between two 3D points
     *
     * @param p a 3D point
     * @return Euclidean squared distance between current instance and p
     */

    public double distanceSquared(Point3D p) {
        return ((_x.coord - p._x.coord) * (_x.coord - p._x.coord) +
                (_y.coord - p._y.coord) * (_y.coord - p._y.coord) +
                (_z.coord - p._z.coord) * (_z.coord - p._z.coord));
    }

    /**
     * calculate the Euclidean distance between two 3D points
     *
     * @param p a 3D point
     * @return Euclidean distance between current instance to p
     */
    public double distance(Point3D p) {
        return Math.sqrt(distanceSquared(p));
    }

    /**
     * calculate the midpoint value between two points
     * @param p point3D
     * @return the midpoint between p and current Point3d instance
     */
    public Point3D midPoint(Point3D p){
        return new Point3D((getX()+p.getX())/2,
                (getY()+p.getY())/2,
                (getZ()+p.getZ())/2);
    }

    /**
     * x coordinate getter
     * @return x coordinate value
     */
    public double getX() {
        return _x.coord;
    }

    /**
     * x coordinate getter
     * @return x coordinate value
     */
    public double getY() {
        return _y.coord;
    }

    /**
     * x coordinate getter
     * @return x coordinate value
     */
    public double getZ() {
        return _z.coord;
    }
}


