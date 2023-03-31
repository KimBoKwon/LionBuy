package com.ateam.lionbuy;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsoupTest {
    
    @Test
    public void jsoupTest() {
        String url = "https://search.shopping.naver.com/search/all?query=%ED%85%80%EB%B8%94%EB%9F%AC&cat_id=&frm=NVSHATC";
        Connection conn = Jsoup.connect(url);

        try {
            Document doc = conn.get();

            File file = new File("C:/Users/me/Desktop/ateam_project/buyproject/src/test/java/com/ateam/buyproject/jsouptest.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(doc.html());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
