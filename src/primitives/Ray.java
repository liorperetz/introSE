package primitives;

/**
 * class Ray representing a Ray - part of a line that has a fixed starting point
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Ray {

    Point3D _p0;//beginning point
    Vector _dir;//direction vector

    /**
     * Ray constructor receiving Point3D and Vector
     * @param p0 value for beginning point
     * @param dir value for direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this._p0 = new Point3D(p0._x, p0._y, p0._z);
        this._dir = dir.normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    @Override
    public String toString() {
        return "p0=" + _p0.toString() +
                ", dir=" + _dir.toString();
    }
}
