package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing render.ImageWriter
 *
 * @author Lior Peretz
 * @author Reuven Klein
 */

class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}
     *
     * creating an image of a grid made up of 10x16 squares in resolution of 500x800 pixels
     */
    @Test
    void testWriteToImage(){
        ImageWriter imagewriter=new ImageWriter("testImageWriter",800,500);
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                //color in black a grid pixel (800/16=50)
                if(i%50==0){
                    imagewriter.writePixel(j,i, Color.BLACK);
                }
                //500/10=50
                else if(j%50==0){
                    imagewriter.writePixel(j,i,Color.BLACK);
                }
                else{
                    imagewriter.writePixel(j,i,new Color(0d,0d,255));
                }
            }
        }
        imagewriter.writeToImage();
    }

}