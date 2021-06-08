package elements;

import primitives.Color;

/**
 * class Light is abstract class
 * parent of different kinds of
 * lights in the scene
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
abstract class Light {
    /**
     * color's intensity of the light
     */
    protected final Color _intensity;

    /**
     * Light constructor
     * @param intensity color's intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * intensity getter
     * @return color's intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
