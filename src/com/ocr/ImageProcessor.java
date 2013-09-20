/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author xueliang
 */
public class ImageProcessor {

    /**
     * 把图片序列化字成字符串
     *
     * @param bi
     * @return
     */
    public static String serialize(BufferedImage bi) throws IOException {
        int width = bi.getWidth();
        int height = bi.getHeight();
        //<editor-fold desc="把矩阵拼成一个字符串">
        int[][] matrix = getMatrix(bi);
        StringBuilder cup = new StringBuilder(width * height);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                cup.append(matrix[i][j] + " ");
            }
            cup.append("\r\n");
        }
        //</editor-fold>
        return cup.toString();
    }

    public static List<String> getCharacteristics(int[][] matrix, int maxCharWidth) {
        if (matrix == null || matrix.length < 1) {
            return null;
        }
        List<String> data = new LinkedList<String>();
        StringBuilder cup = new StringBuilder();
        //<editor-fold desc="把矩阵拼成一个字符串">
        for (int i = 0; i < matrix.length; i++) {
            boolean isBorder = isBorder(matrix, i);
            if (!isBorder) {
                for (int j = 0; j < matrix[i].length; j++) {
                    cup.append(matrix[i][j]);
                }
            }
            if ((maxCharWidth < 1 && isBorder) || ((i + 1) % maxCharWidth) == 0) {
                data.add(cup.toString());
                cup.delete(0, cup.length());
            }
        }
        //</editor-fold>
        return data;
    }

    public static int[][] getMatrix(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        int[][] matrix = new int[width][height];
        int background = getBackground(bi);
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y > 0; y--) {
                int pixel = bi.getRGB(x, y);
                if (pixel == background) {
                    matrix[x][height - y - 1] = 0;
                } else {
                    matrix[x][height - y - 1] = 1;
                }
            }
        }
        return matrix;
    }

    protected static BufferedImage binary(int width, int height, BufferedImage bi) {
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bi.getRGB(i, j);
                graph.setRGB(i, j, rgb);
            }
        }

        try {
            ImageIO.write(bi, "png", new File("E:/binary.jpg"));
        } catch (IOException ex) {
        }
        return graph;
    }

    protected static BufferedImage average(int width, int height, BufferedImage bi) {
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bi.getRGB(i, j);
                int alpha = rgb & 0xff000000;
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                rgb = (red * 77 + green * 151 + blue * 28) >> 8;
                rgb = alpha | (rgb << 16) | (rgb << 8) | rgb;
                graph.setRGB(i, j, rgb);
            }
        }
        try {
            ImageIO.write(bi, "png", new File("E:/average.jpg"));
        } catch (IOException ex) {
        }
        return graph;
    }

    private static int getBackground(BufferedImage bi) {
        int background = 0;
        int width = bi.getWidth();
        int height = bi.getHeight();
        boolean isBackground = false;
        for (int x = 0; x < width; x++) {
            background = bi.getRGB(x, 0);
            for (int y = 0; y < height; y++) {
                int rgb = bi.getRGB(x, y);
                if (background != rgb) {
                    isBackground = false;
                } else {
                    isBackground = true;
                }
            }
            if (true == isBackground) {
                break;
            }
        }
        return background;
    }

    private static boolean isBorder(int[][] matrix, int index) {
        boolean isBorder = false;
        int point = matrix[index][0];
        for (int y = 0; y < matrix[index].length; y++) {
            int pixel = matrix[index][y];
            if (point == pixel) {
                isBorder = true;
            } else {
                isBorder = false;
                break;
            }
        }
        return isBorder;
    }

    public static BufferedImage getBufferedImage(InputStream is) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        BufferedImage bi = javax.imageio.ImageIO.read(iis);
        return bi;
    }
}
