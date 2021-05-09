package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * class Scene represents a scene - data structure representing geometries, textures,
 * lights, shading, and view point information
 */
public class Scene {
    public String _name; //name of the scene
    public Color _background =new Color(Color.BLACK); //default background color is black
    public AmbientLight _ambientLight =new AmbientLight(Color.BLACK,0);//default ambient light is black with intensity 0
    public Geometries _geometries;//3D model

    /**
     * Scene constructor
     * @param name the name of the scene
     */
    public Scene(String name) {
        this._name = name;
        _geometries=new Geometries();

    }

    //chaining methods

    /**
     * background setter
     * @param background background color to the scene
     * @return current Scene instance
     */
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    /**
     * ambientLight setter
     * @param ambientLight the ambient light in the scene
     * @return current Scene instance
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }


    /**
     * geometries setter
     * @param geometries 3D model of the scene
     * @return current Scene instance
     */
    public Scene setGeometries(Geometries geometries) {
        _geometries = geometries;
        return this;
    }
}
