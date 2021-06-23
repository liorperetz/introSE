package elements;

import geometries.Plane;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.Arrays;

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
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Camera camera = new Camera(new Point3D(100, 30, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(150, 150).setDistance(80);

        scene._geometries.add(//
                new Plane(new Point3D(15.41493101796042, -12.530455279045601, -20.331714129217218),
                        new Point3D(45.361300076875764, 66.590279814672073, -6.442114332450545),
                        new Point3D(-15.338639484045661, 33.790653850907191, -8.054955173649793)).
                        setEmission(new Color(30, 30, 30)).
                        setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60).setKt(0.3)),
                new Polygon(new Point3D(29.116241964534673, -238.232615468666893, -39.25099404785152),
                        new Point3D(110.332170273600511, -186.51261918309055, 148.883552554260007),
                        new Point3D(-64.241558131666466, 114.256803177515991, 137.120950857051184),
                        new Point3D(-146.264361040333483, 66.510353720166705, -48.605653457185355)).
                        setEmission(new Color(20, 20, 20)).
                        setMaterial(new Material().setKr(0.8)),
                new Sphere(20, new Point3D(20.116807047047587, -13.862502322740898, 0)).
                        setEmission(new Color(82, 181, 188)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(15, new Point3D(-10.132589295428716, 32.379903137629952, 7)).
                        setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(8, new Point3D(49.850530363547705, 65.228399717297805, 2)).
                        setEmission(new Color(java.awt.Color.red)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));


        scene._lights.add( //
                new DirectionalLight(new Color(246, 250, 209), new Vector(new Point3D(0, 1, -1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector(-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE), new Vector(new Point3D(0, -1, -2))));

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
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Camera camera = new Camera(new Point3D(100, 30, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(150, 150).setDistance(80);

        scene._geometries.add(//
                new Plane(new Point3D(15.41493101796042, -12.530455279045601, -20.331714129217218),
                        new Point3D(45.361300076875764, 66.590279814672073, -6.442114332450545),
                        new Point3D(-15.338639484045661, 33.790653850907191, -8.054955173649793)).
                        setEmission(new Color(30, 30, 30)).
                        setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60).setKt(0.3)),
                new Polygon(new Point3D(29.116241964534673, -238.232615468666893, -39.25099404785152),
                        new Point3D(110.332170273600511, -186.51261918309055, 148.883552554260007),
                        new Point3D(-64.241558131666466, 114.256803177515991, 137.120950857051184),
                        new Point3D(-146.264361040333483, 66.510353720166705, -48.605653457185355)).
                        setEmission(new Color(20, 20, 20)).
                        setMaterial(new Material().setKr(0.8).setKGlossy(90)),
                new Sphere(20, new Point3D(20.116807047047587, -13.862502322740898, 0)).
                        setEmission(new Color(82, 181, 188)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(15, new Point3D(-10.132589295428716, 32.379903137629952, 7)).
                        setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Sphere(8, new Point3D(49.850530363547705, 65.228399717297805, 2)).
                        setEmission(new Color(java.awt.Color.red)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));


        scene._lights.add( //
                new DirectionalLight(new Color(246, 250, 209), new Vector(new Point3D(0, 1, -1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector(-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE), new Vector(new Point3D(0, -1, -2))));

        ImageWriter imageWriter = new ImageWriter("testBeamAA", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
        render.setMultithreading(3);

    }


    @Test
    public void targil8() {

        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Camera camera = new Camera(new Point3D(290, -130, 50), new Vector(-3, 1, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(350, 350).setDistance(200);

        //lying dreidel

        //body
        Point3D I=new Point3D(-274.670629949575641, -269.913751689840183, 0);
        Point3D M=new Point3D(-274.670629949575641,-269.913751689840183,102.500101094101979);
        Point3D N=new Point3D(-323.56405075902876, -179.826548792587005, 102.500101094101979);
        Point3D J=new Point3D(-323.56405075902876, -179.826548792587005, 0);
        Point3D L=new Point3D(-364.757832846828819, -318.807172499293301, 0);
        Point3D P=new Point3D(-364.757832846828819, -318.807172499293301, 102.500101094101979);
        Point3D O=new Point3D(-413.651253656281938, -228.719969602040123, 102.500101094101979);
        Point3D K=new Point3D(-413.651253656281938,-228.719969602040123,0);

        scene._geometries.add(new Polygon(I,M,N,J).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(I,M,P,L).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(L,P,O,K).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(K,O,N,J).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(M,P,O,N).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(I,L,K,J).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));

        //base
        Point3D Q=new Point3D(-368.607652207655349, -204.273259197313564, 51.250050547050989).
                add(K.subtract(L).normalize().scale(80));

        scene._geometries.add(new Polygon(Q,O,K).setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(Q,O,N).setEmission(new Color(java.awt.Color.PINK)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(Q,N,J).setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(Q,K,J).setEmission(new Color(java.awt.Color.PINK)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));

        //handle
        Point3D midPointML=new Point3D(-319.71423139820223, -294.360462094566742, 51.250050547050989);
        Vector LM=M.subtract(L).normalize();
        Vector IP=P.subtract(I).normalize();

        LM=LM.scale(5);
        IP=IP.scale(5);
        Point3D R=midPointML.add(LM);
        Point3D S=midPointML.add(LM.scale(-1));
        Point3D T=midPointML.add(IP);
        Point3D U=midPointML.add(IP.scale(-1));
        Vector JI=I.subtract(J).normalize().scale(40);
        Point3D Rup=R.add(JI);
        Point3D Sup=S.add(JI);
        Point3D Tup=T.add(JI);
        Point3D Uup=U.add(JI);


        scene._geometries.add(new Polygon(R,U,Uup,Rup).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(R,T,Tup,Rup).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(T,S,Sup,Tup).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(S,U,Uup,Sup).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(Rup,Uup,Sup,Tup).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));

        //standing dreidel

        //body
        Point3D A=new Point3D(-119.126345343447156, 55.188148144158816, 60);
        Point3D B=new Point3D(-218.281167997278999, 109.00446363953202, 60);
        Point3D C=new Point3D(-272.097483492652202, 9.849640985700162, 60);
        Point3D D=new Point3D(-172.942660838820359, -43.966674509673027, 60);
        Point3D E=new Point3D(-119.126345343447156, 55.188148144158816, 172.817882753623735);
        Point3D F=new Point3D(-218.281167997278999,109.00446363953202,172.817882753623735);
        Point3D G=new Point3D(-272.097483492652202, 9.849640985700177, 172.817882753623735);
        Point3D H=new Point3D(-172.942660838820359, -43.966674509673027, 172.817882753623735);

        scene._geometries.add(new Polygon(A,E,F,B).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(B,F,G,C).setEmission(new Color(java.awt.Color.orange)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(C,G,H,D).setEmission(new Color(java.awt.Color.orange)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(D,H,E,A).setEmission(new Color(java.awt.Color.orange)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(H,G,F,E).setEmission(new Color(java.awt.Color.orange)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(C,D,A,B).setEmission(new Color(java.awt.Color.orange)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));
        //base
        Point3D topRightPyramid=A.midPoint(C).
                add(B.subtract(F).normalize().scale(60));

        scene._geometries.add(new Polygon(topRightPyramid,A,B).setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(topRightPyramid,B,C).setEmission(new Color(java.awt.Color.PINK)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(topRightPyramid,C,D).setEmission(new Color(java.awt.Color.pink)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(topRightPyramid,A,D).setEmission(new Color(java.awt.Color.PINK)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));

        //handle
        Point3D midPointEG=E.midPoint(G);
        Vector EG=G.subtract(E).normalize();
        Vector FH=H.subtract(F).normalize();

        EG=EG.scale(5);
        FH=FH.scale(5);
        Point3D V=midPointEG.add(EG);
        Point3D W=midPointEG.add(EG.scale(-1));
        Point3D X=midPointEG.add(FH);
        Point3D Y=midPointEG.add(FH.scale(-1));
        Vector AE=E.subtract(A).normalize().scale(50);
        Point3D Vup=V.add(AE);
        Point3D Wup=W.add(AE);
        Point3D Xup=X.add(AE);
        Point3D Yup=Y.add(AE);


        scene._geometries.add(new Polygon(V,Vup,Yup,Y).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(Y,Yup,Wup,W).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(W,Wup,Xup,X).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(X,Xup,Wup,W).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)),
                new Polygon(Xup,Vup,Yup,Wup).setEmission(new Color(java.awt.Color.ORANGE)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));

        //floor
        scene._geometries.add(new Plane(new Point3D(0,0,0),
                new Point3D(0,1,0),
                new Point3D(1,0,0)).
                setEmission(new Color(82, 42, 2)).
                setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)));

        //balls
        scene._geometries.add(new Sphere(80,new Point3D(-250, 400, 80)).
                setEmission(new Color(java.awt.Color.RED)).
                setMaterial(new Material().setKs(0.4).setShininess(20).setKd(0.4)));
                //setMaterial(new Material().setKr(1).setKs(0.4).setShininess(20).setKd(0.4).setKGlossy(90)));
        scene._geometries.add(new Sphere(20,new Point3D(30, -150, 20)).
                setEmission(new Color(java.awt.Color.RED)).
                setMaterial(new Material().setKs(0.4).setShininess(20).setKd(0.4)));
                //setMaterial(new Material().setKr(1).setKs(0.4).setShininess(20).setKd(0.4).setKGlossy(85)));

        //hanukia
        Point3D p=new Point3D(175,20,0);
        Point3D[][] hannukia=cube(p,new Vector(0,-100,0),new Vector(-10,0,0),new Vector(0,0,10));
        scene._geometries.add(new Polygon(hannukia[0][0],hannukia[0][1],hannukia[0][2],hannukia[0][3]).setEmission(new Color(java.awt.Color.GRAY)));
        scene._geometries.add(new Polygon(hannukia[1][0],hannukia[1][1],hannukia[1][2],hannukia[1][3]).setEmission(new Color(java.awt.Color.GRAY)));
        scene._geometries.add(new Polygon(hannukia[2][0],hannukia[2][1],hannukia[2][2],hannukia[2][3]).setEmission(new Color(java.awt.Color.GRAY)));
        scene._geometries.add(new Polygon(hannukia[3][0],hannukia[3][1],hannukia[3][2],hannukia[3][3]).setEmission(new Color(java.awt.Color.GRAY)));
        scene._geometries.add(new Polygon(hannukia[4][0],hannukia[4][1],hannukia[4][2],hannukia[4][3]).setEmission(new Color(java.awt.Color.GRAY)));
        scene._geometries.add(new Polygon(hannukia[5][0],hannukia[5][1],hannukia[5][2],hannukia[5][3]).setEmission(new Color(java.awt.Color.GRAY)));

        Point3D p1=new Point3D(171,18,10);
        Point3D[][] candle=cube(p1,new Vector(0,-4,0),new Vector(-2,0,0),new Vector(0,0,15));
        scene._geometries.add(new Polygon(candle[0][0],candle[0][1],candle[0][2],candle[0][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(candle[1][0],candle[1][1],candle[1][2],candle[1][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(candle[2][0],candle[2][1],candle[2][2],candle[2][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(candle[3][0],candle[3][1],candle[3][2],candle[3][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(candle[4][0],candle[4][1],candle[4][2],candle[4][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(candle[5][0],candle[5][1],candle[5][2],candle[5][3]).setEmission(new Color(java.awt.Color.BLUE)));

        Point3D[][] candle2=moveCube(candle,new Vector(0,-12,0),null);
        scene._geometries.add(new Polygon(candle2[0][0],candle2[0][1],candle2[0][2],candle2[0][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(candle2[1][0],candle2[1][1],candle2[1][2],candle2[1][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(candle2[2][0],candle2[2][1],candle2[2][2],candle2[2][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(candle2[3][0],candle2[3][1],candle2[3][2],candle2[3][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(candle2[4][0],candle2[4][1],candle2[4][2],candle2[4][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(candle2[5][0],candle2[5][1],candle2[5][2],candle2[5][3]).setEmission(new Color(java.awt.Color.RED)));

        Point3D[][] candle3=moveCube(candle,new Vector(0,-12,0),null);
        scene._geometries.add(new Polygon(candle3[0][0],candle3[0][1],candle3[0][2],candle3[0][3]).setEmission(new Color(java.awt.Color.PINK)));
        scene._geometries.add(new Polygon(candle3[1][0],candle3[1][1],candle3[1][2],candle3[1][3]).setEmission(new Color(java.awt.Color.PINK)));
        scene._geometries.add(new Polygon(candle3[2][0],candle3[2][1],candle3[2][2],candle3[2][3]).setEmission(new Color(java.awt.Color.PINK)));
        scene._geometries.add(new Polygon(candle3[3][0],candle3[3][1],candle3[3][2],candle3[3][3]).setEmission(new Color(java.awt.Color.PINK)));
        scene._geometries.add(new Polygon(candle3[4][0],candle3[4][1],candle3[4][2],candle3[4][3]).setEmission(new Color(java.awt.Color.PINK)));
        scene._geometries.add(new Polygon(candle3[5][0],candle3[5][1],candle3[5][2],candle3[5][3]).setEmission(new Color(java.awt.Color.PINK)));

        Point3D p2=new Point3D(171,-74,10);
        Point3D[][] shamash=cube(p2,new Vector(0,-4,0),new Vector(-2,0,0),new Vector(0,0,20));
        scene._geometries.add(new Polygon(shamash[0][0],shamash[0][1],shamash[0][2],shamash[0][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(shamash[1][0],shamash[1][1],shamash[1][2],shamash[1][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(shamash[2][0],shamash[2][1],shamash[2][2],shamash[2][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(shamash[3][0],shamash[3][1],shamash[3][2],shamash[3][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(shamash[4][0],shamash[4][1],shamash[4][2],shamash[4][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(shamash[5][0],shamash[5][1],shamash[5][2],shamash[5][3]).setEmission(new Color(java.awt.Color.RED)));

        scene._geometries.add(new Sphere(2,new Point3D(169, -75.5, 32)).
                setEmission(new Color(java.awt.Color.YELLOW)).
                setMaterial(new Material().setKs(0.4).setShininess(20).setKd(0.4)));
        scene._geometries.add(new Sphere(2,new Point3D(169,17,27)).
                setEmission(new Color(java.awt.Color.YELLOW)).
                setMaterial(new Material().setKs(0.4).setShininess(20).setKd(0.4)));
        scene._geometries.add(new Sphere(2,new Point3D(169,5,27)).
                setEmission(new Color(java.awt.Color.YELLOW)).
                setMaterial(new Material().setKs(0.4).setShininess(20).setKd(0.4)));
        scene._geometries.add(new Sphere(2,new Point3D(169,-7,27)).
                setEmission(new Color(java.awt.Color.YELLOW)).
                setMaterial(new Material().setKs(0.4).setShininess(20).setKd(0.4)));

        scene._geometries.add(new Plane(new Point3D(-700,0,0),new Point3D(0,700,0),new Point3D(-350,350,1)).setEmission(new Color(20,20,20)).setMaterial(new Material().setKr(1).setKGlossy(90)));





        scene._lights.add( //
                new DirectionalLight(new Color(246, 250, 209), new Vector(new Point3D(0, 1, -1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector(-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE), new Vector(new Point3D(0, -1, -2))));

        ImageWriter imageWriter = new ImageWriter("testtestGlossy111", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene).setAdaptiveSuperSampling(true));
        render.setDebugPrint();
        render.setMultithreading(3);
        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void diffusedGlass() {
        Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene.setBackground(new Color(20,20,20));
        Camera camera = new Camera(new Point3D(100, 0, 0), new Vector(-1, 0, 0),new Vector(0,0,1)) //
                .setViewPlaneSize(150, 150).setDistance(120);

        scene._geometries.add(//
                new Polygon(new Point3D(-20,-20,10),
                        new Point3D(-20,-20,-10),
                        new Point3D(-20,-5,-10),
                        new Point3D(-20,-5,10)).
                        setEmission(new Color(20, 20, 20)).
                        setMaterial(new Material().setKt(0.9).setKClear(90)),
                new Sphere(15, new Point3D(-40, 0, 0)).
                        setEmission(new Color(java.awt.Color.GRAY)).setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(20)));



        scene._lights.add( //
                new DirectionalLight(new Color(246, 250, 209), new Vector(new Point3D(0, 1, -1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector(-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE), new Vector(new Point3D(0, -1, -2))));

        ImageWriter imageWriter = new ImageWriter("testDiffusedGlass11", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.setMultithreading(3);
        render.renderImage();
        render.writeToImage();

    }


    @Test
    public void targil9() {

        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Camera camera = new Camera(new Point3D(-95, -150, 90), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(250, 250).setDistance(120);

        Point3D p=new Point3D(-50,0,0);
        Point3D[][] forwardRightLeg=cube(p,new Vector(-5,0,0),new Vector(0,5,0),new Vector(0,0,10));
        scene._geometries.add(new Polygon(forwardRightLeg[0][0],forwardRightLeg[0][1],forwardRightLeg[0][2],forwardRightLeg[0][3]).setEmission(new Color(java.awt.Color.ORANGE)));
        scene._geometries.add(new Polygon(forwardRightLeg[1][0],forwardRightLeg[1][1],forwardRightLeg[1][2],forwardRightLeg[1][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(forwardRightLeg[2][0],forwardRightLeg[2][1],forwardRightLeg[2][2],forwardRightLeg[2][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(forwardRightLeg[3][0],forwardRightLeg[3][1],forwardRightLeg[3][2],forwardRightLeg[3][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(forwardRightLeg[4][0],forwardRightLeg[4][1],forwardRightLeg[4][2],forwardRightLeg[4][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(forwardRightLeg[5][0],forwardRightLeg[5][1],forwardRightLeg[5][2],forwardRightLeg[5][3]).setEmission(new Color(java.awt.Color.RED)));

        Point3D[][] forwardLeftLeg=moveCube(forwardRightLeg,null,new Vector(-120,0,0));
        scene._geometries.add(new Polygon(forwardLeftLeg[1][0],forwardLeftLeg[1][1],forwardLeftLeg[1][2],forwardLeftLeg[1][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(forwardLeftLeg[0][0],forwardLeftLeg[0][1],forwardLeftLeg[0][2],forwardLeftLeg[0][3]).setEmission(new Color(java.awt.Color.ORANGE)));
        scene._geometries.add(new Polygon(forwardLeftLeg[2][0],forwardLeftLeg[2][1],forwardLeftLeg[2][2],forwardLeftLeg[2][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(forwardLeftLeg[3][0],forwardLeftLeg[3][1],forwardLeftLeg[3][2],forwardLeftLeg[3][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(forwardLeftLeg[4][0],forwardLeftLeg[4][1],forwardLeftLeg[4][2],forwardLeftLeg[4][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(forwardLeftLeg[5][0],forwardLeftLeg[5][1],forwardLeftLeg[5][2],forwardLeftLeg[5][3]).setEmission(new Color(java.awt.Color.RED)));

        Point3D[][] frontLeftLeg=moveCube(forwardLeftLeg,new Vector(0,-60,0),new Vector(-5,0,0));
        scene._geometries.add(new Polygon(frontLeftLeg[1][0],frontLeftLeg[1][1],frontLeftLeg[1][2],frontLeftLeg[1][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(frontLeftLeg[0][0],frontLeftLeg[0][1],frontLeftLeg[0][2],frontLeftLeg[0][3]).setEmission(new Color(java.awt.Color.ORANGE)));
        scene._geometries.add(new Polygon(frontLeftLeg[2][0],frontLeftLeg[2][1],frontLeftLeg[2][2],frontLeftLeg[2][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(frontLeftLeg[3][0],frontLeftLeg[3][1],frontLeftLeg[3][2],frontLeftLeg[3][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(frontLeftLeg[4][0],frontLeftLeg[4][1],frontLeftLeg[4][2],frontLeftLeg[4][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(frontLeftLeg[5][0],frontLeftLeg[5][1],frontLeftLeg[5][2],frontLeftLeg[5][3]).setEmission(new Color(java.awt.Color.RED)));

        Point3D[][] frontRightLeg=moveCube(frontLeftLeg,null,new Vector(130,0,0));
        scene._geometries.add(new Polygon(frontRightLeg[1][0],frontRightLeg[1][1],frontRightLeg[1][2],frontRightLeg[1][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(frontRightLeg[0][0],frontRightLeg[0][1],frontRightLeg[0][2],frontRightLeg[0][3]).setEmission(new Color(java.awt.Color.ORANGE)));
        scene._geometries.add(new Polygon(frontRightLeg[2][0],frontRightLeg[2][1],frontRightLeg[2][2],frontRightLeg[2][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(frontRightLeg[3][0],frontRightLeg[3][1],frontRightLeg[3][2],frontRightLeg[3][3]).setEmission(new Color(java.awt.Color.BLUE)));
        scene._geometries.add(new Polygon(frontRightLeg[4][0],frontRightLeg[4][1],frontRightLeg[4][2],frontRightLeg[4][3]).setEmission(new Color(java.awt.Color.RED)));
        scene._geometries.add(new Polygon(frontRightLeg[5][0],frontRightLeg[5][1],frontRightLeg[5][2],frontRightLeg[5][3]).setEmission(new Color(java.awt.Color.RED)));



        scene._lights.add( //
                new DirectionalLight(new Color(246, 250, 209), new Vector(new Point3D(0, 1, -1))));
        scene._lights.add(new SpotLight(new Color(148, 228, 233), new Point3D(60, 0, 0), new Vector(-39.883192952952413, -13.862502322740898, 0)) //
                .setKl(0.001).setKq(0.001));
        scene._lights.add( //
                new DirectionalLight(new Color(java.awt.Color.BLUE), new Vector(new Point3D(0, -1, -2))));

        ImageWriter imageWriter = new ImageWriter("testtest", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.setDebugPrint();
        render.setMultithreading(3);
        render.renderImage();
        render.writeToImage();

    }

    private Point3D[][]cube(Point3D baseBottomRight,Vector left,Vector forward,Vector up) {

        Point3D A = baseBottomRight;
        Point3D B = baseBottomRight.add(forward);
        Point3D C = B.add(left);
        Point3D D = A.add(left);

        Point3D E = A.add(up);
        Point3D F = B.add(up);
        Point3D G = C.add(up);
        Point3D H = D.add(up);

        Point3D[][] points = {
                {A, B, C, D},//base
                {E, F, G, H},//top
                {A, E, F, B},//right
                {D, H, G, C},//left
                {A, E, H, D},//back
                {B, F, G, C}//front
        };

        return points;

    }

    private Point3D[][] moveCube(Point3D[][]cube,Vector forward,Vector left){

        Point3D[][]moved=cube;
        Vector move;
        if(forward !=null){
            move=forward;
            if(left!=null){
                move=move.add(left);
            }
        }
        else{
            move=left;
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                moved[i][j] = moved[i][j].add(move);
            }
        }
        return moved;



    }

}

