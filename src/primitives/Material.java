package primitives;

/**
 * Material class describe material by it's behavior when light hit it
 *
 *  @author Reuven Klein
 *  @author Lior Peretz
 */
public class Material {
    /**
     * diffusion coefficient
     */
    public double _Kd =0d;
    /**
     * shininess coefficient
     */
    public double _Ks =0d;
    /**
     * object’s shininess
     */
    public int _nShininess =0;
    /**
     * reflection coefficient
     */
    public double _Kr=0d;
    /**
     * transparency coefficient
     */
    public double _Kt=0d;
    /**
     * gloss coefficient (0%-100%)
     */
    public double _kGlossy =100d;
    /**
     * clearing coefficient (0%-100%)
     */
    public double _kClear =100d;

    //chaining setters methods
    /**
     * Kd chaining setter
     * @param Kd material's diffusion coefficient
     * @return current Material instance
     */
    public Material setKd(double Kd) {
        _Kd = Kd;
        return this;
    }

    /**
     * Kd chaining setter
     * @param Ks material's shininess coefficient
     * @return current Material instance
     */
    public Material setKs(double Ks) {
        _Ks = Ks;
        return this;
    }

    /**
     * nShininess chaining setter
     * @param nShininess material's shininess
     * @return current Material instance
     */
    public Material setShininess(int nShininess) {
        _nShininess = nShininess;
        return this;
    }

    /**
     * Kr chaining setter
     * @param kr material's reflection coefficient
     * @return current Material instance
     */
    public Material setKr(double kr) {
        _Kr = kr;
        return this;
    }

    /**
     * Kt chaining setter
     * @param kt material's transparency coefficient
     * @return current Material instance
     */
    public Material setKt(double kt) {
        _Kt = kt;
        return this;
    }

    /**
     * kGlossy chaining setter
     * @param kGlossy material's glossy coefficient
     * @return current Material instance
     */
    public Material setKGlossy(double kGlossy) {
        _kGlossy = kGlossy;
        return this;
    }

    /**
     * kBlur chaining setter
     * @param kClear material's blurring coefficient
     * @return
     */
    public Material setKClear(double kClear) {
        _kClear = kClear;
        return this;
    }
}
