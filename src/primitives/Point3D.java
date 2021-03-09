package primitives;

/**
 * class Point3D representing a point in a 3D cartesian coordinate system
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Point3D {
    /**
     * 3 coordinates values to represent a 3D point (x,y,z)
     */
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    /**
     * 3D point constant (0,0,0) use for comparisons
     */
    final public static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    /**
     * Point3D constructor receiving 3 coordinates values
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
        return "{" +
                _x.toString() +
                ", " + _y.toString() +
                ", " + _z.toString() +
                "}";
    }

    /**
     * subtraction between two 3D points
     * @param p1 a 3D point
     * @return reference to vector from p1 to current point
     */
    public Vector subtract(Point3D p1) {
        if(p1.equals((this))) //subtraction between identical points gives illegal vector (0,0,0)
            throw new IllegalArgumentException("cannot create vector to point (0,0,0)");
        return new Vector(new Point3D(this._x.coord - p1._x.coord,
                this._y.coord - p1._y.coord,
                this._z.coord - p1._z.coord)

        );
    }

    /**
     * add a 3D vector to 3D point
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
     * @param p1 a 3D point
     * @param p2 a 3D point
     * @return Euclidean squared distance between p1 and p2
     */

    public double distanceSquared(Point3D p1, Point3D p2) {
        return ((p1._x.coord - p2._x.coord) * (p1._x.coord - p2._x.coord) +
                (p1._y.coord - p2._y.coord) * (p1._y.coord - p2._y.coord) +
                (p1._z.coord - p2._z.coord) * (p1._z.coord - p2._z.coord));
    }

    /**
     * calculate the Euclidean distance between two 3D points
     * @param p1 a 3D point
     * @param p2 a 3D point
     * @return Euclidean distance between p1 and p2
     */
    public double distance(Point3D p1, Point3D p2) {
        return Math.sqrt(distanceSquared(p1, p2));
    }


}


