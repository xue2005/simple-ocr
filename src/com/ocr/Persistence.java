/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author xueliang
 */
public class Persistence {

    public static void save(Map<String, String> map) {
        if (map == null) {
            System.out.println("map is null");
            return;
        }
        Map<String, String> data = read();
        if (data != null) {
            for (Entry<String, String> kv : map.entrySet()) {
                data.put(kv.getKey(), kv.getValue());
            }
        }
        String path = Persistence.class.getResource("data.ocr").getFile().replaceFirst("/", "");
        java.io.File file = new File(path);
        java.io.FileWriter writer = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new java.io.FileWriter(file);
            for (Entry<String, String> kv : data.entrySet()) {
                writer.write(kv.getKey() + ":" + kv.getValue() + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    static Map<String, String> read() {
        InputStream is = Persistence.class.getResourceAsStream("data.ocr");
        BufferedReader reader = null;
        Map<String, String> map = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            map = new HashMap<String, String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] kv = line.split(":");
                if (kv.length == 2) {
                    map.put(kv[0], kv[1]);
                }
            }
        } catch (Exception e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                }
            }
        }
        return map;
    }
}
