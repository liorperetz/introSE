package elements;

import primitives.Color;

/**
 * AmbientLight class represents the ambient light color in a scene
 */
public class AmbientLight {
    /**
     * intensity of the ambient light
     */
    final private Color _intensity;

    /**
     * AmbientLight Constructor
     * @param iA color's intensity
     * @param kA constant for intensity
     */
    public AmbientLight(Color iA, double kA) {
        _intensity = iA.scale(kA);
    }

    /**
     * intensity getter
     * @return color's intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
