package elements;

import geometries.Plane;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene._geometries.add( //
                new Sphere(50, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene._lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene._geometries.add( //
                new Sphere(400, new Point3D(-950, -900, -1000)) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(200, new Point3D(-950, -900, -1000)) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene._lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene._geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30, new Point3D(60, 50, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene._lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * image for end of targil 7
     */
    @Test
    public void imageToTargil7_1() {
        //Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.pink),0.2));
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));;
        Camera camera = new Camera(new Point3D(100, 30, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(150, 150).setDistance(80);

        scene._geometries.add(//
                new Plane(new Point3D(15.41493101796042, -12.530455279045601, -20.331714129217218),
                        new Point3D(45.361300076875764, 66.590279814672073, -6.442114332450545),
                        new Point3D(-15.338639484045661, 33.790653850907191, -8.054955173649793)).
                        setEmission(new Color(30,30,30)).
                        setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60).setKt(0.3)),
                new Polygon(new Point3D(29.116241964534673, -238.232615468666893, -39.25099404785152),
                        new Point3D(110.332170273600511, -186.51261918309055, 148.883552554260007),
                        new Point3D(-64.241558131666466, 114.256803177515991, 137.120950857051184),
                        new Point3D(-146.264361040333483, 66.510353720166705, -48.605653457185355)).
                        setEmission(new Color(20,20,20)).
                        setMaterial(new Material().setKr(0.8)),
                new Sphere(20,new Point3D(20.116807047047587, -13.862502322740898, 0)).
                        setEmission(new Color(82,181,188)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(15,new Point3D(-10.132589295428716, 32.379903137629952, 7)).
                        setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(8,new Point3D(49.850530363547705, 65.228399717297805, 2)).
                        setEmission(new Color(java.awt.Color.red)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));


        scene._lights.add( //
                new DirectionalLight(new Color(246,250,209),new Vector(new Point3D(0,1,-1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector (-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE),new Vector(new Point3D(0,-1,-2))));

        ImageWriter imageWriter = new ImageWriter("imageToTargil7", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void testBeam() {
        //Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.pink),0.2));
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));;
        Camera camera = new Camera(new Point3D(100, 30, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(150, 150).setDistance(80);

        scene._geometries.add(//
                new Plane(new Point3D(15.41493101796042, -12.530455279045601, -20.331714129217218),
                        new Point3D(45.361300076875764, 66.590279814672073, -6.442114332450545),
                        new Point3D(-15.338639484045661, 33.790653850907191, -8.054955173649793)).
                        setEmission(new Color(30,30,30)).
                        setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60).setKt(0.3)),
                new Polygon(new Point3D(29.116241964534673, -238.232615468666893, -39.25099404785152),
                        new Point3D(110.332170273600511, -186.51261918309055, 148.883552554260007),
                        new Point3D(-64.241558131666466, 114.256803177515991, 137.120950857051184),
                        new Point3D(-146.264361040333483, 66.510353720166705, -48.605653457185355)).
                        setEmission(new Color(20,20,20)).
                        setMaterial(new Material().setKr(0.8).setPercent(10)),
                new Sphere(20,new Point3D(20.116807047047587, -13.862502322740898, 0)).
                        setEmission(new Color(82,181,188)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(15,new Point3D(-10.132589295428716, 32.379903137629952, 7)).
                        setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(8,new Point3D(49.850530363547705, 65.228399717297805, 2)).
                        setEmission(new Color(java.awt.Color.red)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));


        scene._lights.add( //
                new DirectionalLight(new Color(246,250,209),new Vector(new Point3D(0,1,-1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector (-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE),new Vector(new Point3D(0,-1,-2))));

        ImageWriter imageWriter = new ImageWriter("testBeam", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void lite() {
        //Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.pink),0.2));
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));;
        Camera camera = new Camera(new Point3D(100, 30, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(100, 100).setDistance(80);

        scene._geometries.add(//
                new Plane(new Point3D(0,1,0),
                        new Point3D(0,2,0),
                        new Point3D(-1,0,15)).
                        setEmission(new Color(30,30,30)).
                        setMaterial(new Material().setKr(1).setPercent(10)),
                new Sphere(20,new Point3D(20.116807047047587, -13.862502322740898, 30)).
                        setEmission(new Color(82,181,188)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(15,new Point3D(-10.132589295428716, 32.379903137629952, 50)).
                        setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(8,new Point3D(49.850530363547705, 65.228399717297805, 20)).
                        setEmission(new Color(java.awt.Color.red)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));


        scene._lights.add( //
                new DirectionalLight(new Color(246,250,209),new Vector(new Point3D(0,1,-1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector (-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE),new Vector(new Point3D(0,-1,-2))));

        ImageWriter imageWriter = new ImageWriter("lite", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();

    }
    @Test
    public void litelite() {
        //Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.pink),0.2));
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));;
        Camera camera = new Camera(new Point3D(10, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(8,8 ).setDistance(10);

        scene._geometries.add(//
                new Polygon(new Point3D(0,-4,0),
                        new Point3D(0,-4,4),
                        new Point3D(-4,0,4),
                        new Point3D(-4,0,0)).
                        setEmission(new Color(30,30,30)).
                        setMaterial(new Material().setKr(1).setPercent(10).setKs(0.4).setShininess(30)),
                new Polygon(new Point3D(0,0,0),
                        new Point3D(0,0,4),
                        new Point3D(-4,4,4),
                        new Point3D(-4,4,0)).
                        setEmission(new Color(java.awt.Color.BLUE)).
                        setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));



        scene._lights.add( //
                new DirectionalLight(new Color(246,250,209),new Vector(new Point3D(-1.666666666666668, -2.333333333333333, -1.666666666666667))));


        ImageWriter imageWriter = new ImageWriter("litelite", 2, 2);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();

    }
}

