package com.ateam.lionbuy.service.shopingmall;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Mall {
    
    public String mall_parsing(String link) {
        Document document;
        Elements parsingText;
        String keyworld;
        String name = mall_name(link);
        switch(name) {
            case "11번가":
                document = jsoup_conn(link);
                parsingText = document.getElementsByTag("title");
                keyworld = parsingText.text();
                return keyworld;
            case "옥션":
                document = jsoup_conn(link);
                parsingText = document.getElementsByClass("itemtit");
                keyworld = parsingText.text();
                return keyworld;
            case "G마켓":
                document = jsoup_conn(link);
                parsingText = document.getElementsByClass("itemtit");
                keyworld = parsingText.text();
                return keyworld;
            case "인터파크":
                document = jsoup_conn(link); 
                parsingText = document.getElementsByTag("title");
                keyworld = parsingText.text().split(" - ")[0];
                return keyworld;
            case "쿠팡":
                String url = link;
                try {
                    document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                        .get();
                    System.out.println(document.html());
                    parsingText = document.getElementsByTag("title");
                    keyworld = parsingText.text().split(" | ")[1];
                    return keyworld;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case "e마트":
                document = jsoup_conn(link);
                parsingText = document.getElementsByClass("cdtl_info_tit_name");
                keyworld = parsingText.text();
                return keyworld;
            case "신세계몰":
                document = jsoup_conn(link);
                parsingText = document.getElementsByClass("cdtl_info_tit_name");
                keyworld = parsingText.text();
                return keyworld;
        }
        return "알지 못한 쇼핑몰입니다.";
    }

    String mall_name(String link) {
        if(link.contains("11st.co.kr")) {
            return "11번가";
        }else if(link.contains("auction.co.kr")) {
            return "옥션";
        }else if(link.contains("gmarket.co.kr")) {
            return "G마켓";
        }else if(link.contains("shopping.interpark.com")) {
            return "인터파크";
        }else if(link.contains("coupang.com")) {
            return "쿠팡";
        }else if(link.contains("emart.ssg.com")) {
            return "e마트";
        }else if(link.contains("shinsegaemall.ssg.com")) {
            return "신세계몰";
        }else {
            return null;
        }
    }

    Document jsoup_conn(String link) {
        String url = link;
        Connection conn = Jsoup.connect(url);

        try {
            Document document = conn.get();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
