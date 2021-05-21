package elements;

import primitives.Color;

/**
 * AmbientLight class represents the ambient light color in a scene
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class AmbientLight extends Light{

    /**
     * AmbientLight default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * AmbientLight Constructor
     * @param iA color's intensity
     * @param kA constant for intensity
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }
}
