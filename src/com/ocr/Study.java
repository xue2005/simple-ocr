/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xue.http.HttpHelper;

/**
 *
 * @author xueliang
 */
public class Study {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 3) {
            System.out.println("argument not enough");
        }
        String url = args[0];
        String knowledge = args[1];
        String width = args[2];
        System.out.println(url + " " + knowledge + " " + width);
        int charWidth = 0;
        if (width != null && width.length() > 0) {
            charWidth = new Integer(width).intValue();
        }
        study(url, knowledge, charWidth);
    }
    static StringBuilder message;
    static HttpHelper loader = new HttpHelper();
    static PriceOcr ocr = new PriceOcr();

    public static void study(String url, String knowledge, int charWidth) throws IOException {
        byte[] data = loader.getBytes(url);
        java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(data);
        BufferedImage bi = ImageProcessor.getBufferedImage(bis);
        int[][] matrix = ImageProcessor.getMatrix(bi);
        List<String> chars = ImageProcessor.getCharacteristics(matrix, charWidth);
        study(chars, knowledge);
    }

    static void study(List<String> characteristic, String knowledge) {
        if (characteristic == null) {
            System.out.println("characteristic is null");
            return;
        } else if (characteristic.size() < 1) {
            System.out.println("characteristic is empty");
            return;
        }
        if (knowledge == null) {
            System.out.println("knowledge is null");
            return;
        } else if (knowledge.length() < 1) {
            System.out.println("knowledge is empty");
            return;
        }
        char[] data = knowledge.toCharArray();
        if (characteristic.size() != data.length) {
            System.out.println("characteristic's size not equals knowledge'length");
            return;
        }
        Map<String, String> map = new HashMap<String, String>(data.length);
        for (int i = 0; i < characteristic.size(); i++) {
            map.put(characteristic.get(i), data[i] + "");
        }
        Persistence.save(map);
    }
}
