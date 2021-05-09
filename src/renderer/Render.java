package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;

/**
 * Render class creates the color matrix from the scene
 *
 * @author Reuven Klein
 * @author Lior Peretz
 */
public class Render {

    ImageWriter _imageWriter;
    Camera _camera;
    RayTracerBase _rayTracer;

    /**
     * color each pixel of the view plane
     */
    public void renderImage() {
        //trow exception if one of the fields was not set yet
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
            }

            if (_rayTracer == null) {
                throw new MissingResourceException("missing resource value", RayTracerBase.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("missing resource value", Camera.class.getName(), "");
            }

            //rendering the image
            int nX= _imageWriter.getNx();//amount of pixels in x axis (width)
            int nY=_imageWriter.getNy();//amount of pixels in y axis (height)
            //iterate each pixel of the view plane, send ray from the camera through it
            //and determine the color
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray=_camera.constructRayThroughPixel(nX,nY,j,i);
                    Color pixelColor=_rayTracer.traceRay(ray);
                    _imageWriter.writePixel(j,i,pixelColor);
                }

            }

        } catch (MissingResourceException exception) {
            throw new UnsupportedOperationException("Not all fields were set yet" + exception.getClassName());
        }


    }

    /**
     * create a squared grid in the image
     * @param interval square's side length (in pixels)
     * @param color wished color of the grid
     */
    public void printGrid(int interval, Color color) {
        //cannot coloring pixels if imageWriter was not defined yet
        if (_imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }

        int nX = _imageWriter.getNx();//amount of pixels in x axis (width)
        int nY = _imageWriter.getNy();//amount of pixels in y axis (height)

        //coloring the grid's pixels
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                //coloring only the grid according to the interval dimensions
                if (i % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                } else if (j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }

    }

    /**
     *  delegation using. function writeToImage produces unoptimized png file of the image according to
     *  pixel color matrix in the directory of the project
     */
    public void writeToImage(){
        //cannot write to image if imageWriter was not defined yet
        if (_imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        _imageWriter.writeToImage();
    }

    //chaining setters methods
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracer = rayTracer;
        return this;
    }
}
