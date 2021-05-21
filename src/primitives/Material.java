package primitives;

/**
 * Material class describe material by it's light reflection qualities
 */
public class Material {
    public double _Kd =0; //diffusion coefficient
    public double _Ks =0; //shininess coefficient
    public int _nShininess =0; //object’s shininess

    //chaining setters methods

    /**
     * Kd setter
     * @param Kd diffusion coefficient
     * @return current Material instance
     */
    public Material setKd(double Kd) {
        _Kd = Kd;
        return this;
    }

    /**
     * Kd setter
     * @param Ks shininess coefficient
     * @return current Material instance
     */
    public Material setKs(double Ks) {
        _Ks = Ks;
        return this;
    }

    /**
     * nShininess setter
     * @param nShininess object’s shininess
     * @return current Material instance
     */
    public Material setShininess(int nShininess) {
        _nShininess = nShininess;
        return this;
    }
}
