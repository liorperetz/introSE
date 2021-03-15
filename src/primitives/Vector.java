package primitives;

/**
 * class vector representing a 3D vector
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Vector {

    Point3D _head;

    /**
     * Vector constructor receiving point3D
     * @param head the head of the vector
     */
    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO)) //(0,0,0) is illegal vector
            throw new IllegalArgumentException("vector cannot be point(0,0,0)");
        this._head = new Point3D(head._x.coord, head._y.coord, head._z.coord);
    }

    /**
     * Vector constructor receiving 3 coordinates
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {

        this(new Point3D(x, y, z));
    }

    /**
     * Vector constructor receiving 3 double values
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Vector(double x, double y, double z) {

        this(new Point3D(x,y,z));
    }

    /**
     * get the vector's head
     * @return reference to point3D representing the vector's head
     */
    public Point3D getHead() {

        return new Point3D(_head._x, _head._y, _head._z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return _head.toString();
    }

    /**
     * addition between two vectors
     * @param vector another vector
     * @return reference to Vector representing the addition result
     */
    public Vector add(Vector vector) {

        return new Vector(_head._x.coord + vector._head._x.coord,
                _head._y.coord + vector._head._y.coord,
                _head._z.coord + vector._head._z.coord);
    }

    /**
     * subtract another vector from the current vector
     * @param vector the subtrahend vector
     * @return reference to Vector representing the subtraction result
     */
    public Vector subtract(Vector vector) {

        return new Vector(_head._x.coord - vector._head._x.coord,
                _head._y.coord - vector._head._y.coord,
                _head._z.coord - vector._head._z.coord);
    }

    /**
     * scalar multiplication of the current vector
     * @param number scalar
     * @return reference to Vector representing the multiplication result
     */
    public Vector scale(double number) {
        return new Vector(_head._x.coord * number,
                _head._y.coord * number,
                _head._z.coord * number
        );
    }

    /**
     *  dot product between current vector to another vector
     * @param vector another vector
     * @return the dot product result
     */
    public double dotProduct(Vector vector) {
        return (_head._x.coord * vector._head._x.coord +
                _head._y.coord * vector._head._y.coord +
                _head._z.coord * vector._head._z.coord);
    }

    /**
     * cross product between current vector to another vector
     * @param vector another vector
     * @return reference to Vector representing the cross product result
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(_head._y.coord * vector._head._z.coord - _head._z.coord * vector._head._y.coord,
                _head._z.coord * vector._head._x.coord - _head._x.coord * vector._head._z.coord,
                _head._x.coord * vector._head._y.coord - _head._y.coord * vector._head._x.coord

        );
    }

    /**
     * calculate the squared length of the current vector
     * @return vector's squared length
     */
    public double lengthSquared() {
        return (_head._x.coord * _head._x.coord +
                _head._y.coord * _head._y.coord +
                _head._z.coord * _head._z.coord);
    }

    /**
     * calculate the length of the current vector
     * @return vector's length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the current vector (change the vector)
     * @return reference to normalized vector
     */
    public Vector normalize() {
        this._head = new Point3D(_head._x.coord / length(), _head._y.coord / length(),
                _head._z.coord / length());
        return this;
    }

    /**
     * normalize the current vector (doess not change the vector)
     * @return reference to normalized vector
     */
    public Vector normalized() {
        return new Vector(normalize()._head);
    }

}

