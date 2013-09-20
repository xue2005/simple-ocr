/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author xueliang
 */
public interface OCR {

    public String recog(InputStream is,int charWidth) throws IOException;
    
}
