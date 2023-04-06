package com.ateam.lionbuy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileMake {
    
    public void file_make(String content) {
        File file = new File("C:/Users/me/Desktop/ateam_project/lionbuy/src/main/java/com/ateam/lionbuy/util/full.txt");

        if(!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw;
                try {
                    fw = new FileWriter(file);
                    BufferedWriter writer = new BufferedWriter(fw);
                    writer.write(content);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}
