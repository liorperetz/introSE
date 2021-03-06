package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * abstract class to trace a ray and calculate the color of the point the ray hits
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public abstract class RayTracerBase {
    /**
     * scene to be colored
     */
    protected Scene _scene;
    /**
     * the up vector of the view plane
     */
    protected Vector _upVector;

    /**
     * RayTracerBase constructor
     * @param scene instance of Scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * determine the color of the color of the point the ray hit
     * @param ray ray from the camera
     * @return color of the point
     */
    public abstract Color traceRay(Ray ray);

    /**
     * upVector setter
     * @param vUp up vector of the view plane
     */
    public void set_upVector(Vector vUp) {
        _upVector=vUp;
    }
}
