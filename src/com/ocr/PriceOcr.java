/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr;

import static com.ocr.Study.loader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * OCR
 *
 * @author xueliang
 */
public class PriceOcr implements OCR {

    private static Map<String,String> cs = null;
    static{
        cs = Persistence.read();
    }
    
    @Override
    public String recog(InputStream is,int charWidth) throws IOException {
        BufferedImage bi = ImageProcessor.getBufferedImage(is);
        int[][] matrix = ImageProcessor.getMatrix(bi);
        List<String> chars = ImageProcessor.getCharacteristics(matrix, charWidth);
        StringBuilder content = new StringBuilder(chars.size());
        for(String c : chars){
            String x = cs.get(c);
            if(x != null){
                content.append(x);
            }
        }
        return content.toString();
    }

}
