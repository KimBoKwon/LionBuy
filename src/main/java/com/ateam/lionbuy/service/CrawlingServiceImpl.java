package com.ateam.lionbuy.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ateam.lionbuy.entity.Category;
import com.ateam.lionbuy.entity.Product;
import com.ateam.lionbuy.entity.Product_lowprice;
import com.ateam.lionbuy.entity.Product_mall;
import com.ateam.lionbuy.repository.CategoryRepository;
import com.ateam.lionbuy.repository.ProductLowpriceRepository;
import com.ateam.lionbuy.repository.ProductMallRepository;
import com.ateam.lionbuy.repository.ProductRepository;
import com.ateam.lionbuy.service.shopingmall.Mall;

@Service
public class CrawlingServiceImpl implements CrawlingService{

    @Autowired
    private ProductRepository pRepository;
    @Autowired
    private ProductMallRepository pmRepository;
    @Autowired
    private ProductLowpriceRepository plRepository;
    @Autowired
    private CategoryRepository cRepository;

    @Override
    public String getKeyword(String link) {
        Mall mall = new Mall();
        String name = mall.mall_parsing(link);
        return name;
    }

    @Override
    public String start_crawling(String keyword) {
        URL url;
        try {
            String encodedKeyword;
            try {
                encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
                url = new URL("https://search.shopping.naver.com/search/all?query="+ encodedKeyword +"&cat_id=&frm=NVSHATC");
                HttpURLConnection httpConn;
                try {
                    httpConn = (HttpURLConnection) url.openConnection();
                    httpConn.setRequestMethod("GET");
                    httpConn.setRequestProperty("authority", "search.shopping.naver.com");
                    httpConn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    httpConn.setRequestProperty("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
                    httpConn.setRequestProperty("accept-encoding", "UTF-8");
                    httpConn.setRequestProperty("cookie", "NNB=YXHZ2D3QVJOGG; _ga=GA1.2.1812297761.1669099651; autocomplete=use; AD_SHP_BID=21; ASID=1b75238d00000184bdd17e5f0000004e; nx_ssl=2; nid_inf=1219929219; NID_AUT=gNhBRdOTZDD6cGFkfvy1sXLPUIv2g2Xo60KmE9VzJpkVZZxZe0coN2N9SvOtr71O; NID_JKL=MUxjatGV/etdNSlpLL0ivPNZBmfdeDdZY71EUMpzBLg=; NID_SES=AAABh209tIVGpwQ7RjSHXKBQ1hk3MH41oTJzZ9obrO7k2i5fbuB10jJck4PDvxTmLcTdB3ZEdriPFYnP3iIttzg0rNRbbQAT3Go4YOtgcSL9GA1rd8PDfiz3fDODhWDIzNtAhBoP5EFsV169FTpcEDvlm7WT4vU1HCGdipb/6v6/dw92fRM6W4gkt9JG9BepWugRCGzDy+EhaGZb0b9fsPzGmosTWNm1HvBtot/SWm2yftwKgDan1XUcKu7MrH7J6tu6WSDPH95Afsn1t2mGBLtue0OtHY/GvUAi5N/4VlH20wYRSOK2nurliphbmNNv0f9zk02QUi7mq4I4eXWrfIW6Bx49KWrpp7caFD1K2r0LeSOFJgrqgsZbsd0GrGoOKbMIBQINkvhfQvFU0xZuV+HIQwcvBii0AcGN44n/8uJvOpY4/v5riEsjkU0RhwDYSpRt951DGB2UiKkgmZExh5uzAWuKPn8v/aNXp7zdeGC5AsbxQiZHFEn1rbOcO9Y0JU9/ZdhAZw6yEv2M401Qq+49myY=; _naver_usersession_=9FNhHfSCpw+R9wplbGxMPsbH; SHP_BUCKET_ID=1; spage_uid=");
                    httpConn.setRequestProperty("referer", "https://shopping.naver.com/home");
                    httpConn.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"110\", \"Not A(Brand\";v=\"24\", \"Google Chrome\";v=\"110\"");
                    httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
                    httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
                    httpConn.setRequestProperty("sec-fetch-dest", "document");
                    httpConn.setRequestProperty("sec-fetch-mode", "navigate");
                    httpConn.setRequestProperty("sec-fetch-site", "same-site");
                    httpConn.setRequestProperty("sec-fetch-user", "?1");
                    httpConn.setRequestProperty("upgrade-insecure-requests", "1");
                    httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
            
                    InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                            ? httpConn.getInputStream()
                            : httpConn.getErrorStream();
                    Scanner s = new Scanner(responseStream, "UTF-8").useDelimiter("\\A");
                    String response = s.hasNext() ? s.next() : "";

                    String[] ppc_data = data_preprocessing(response);
                    String[] data_Arr = new String[10];
                    for (int i = 0; i < data_Arr.length; i++) {
                        data_Arr[i] = ppc_data[i];
                    }
                    for (int i = 0; i < data_Arr.length; i++) {
                        if(i == 0) {
                            String data = data_Arr[i].split("item\":")[1];
                            Map<String, Object> returnMap = StringToMap(data);
                            Product product = product_build(returnMap);
                            pRepository.save(product);
                            String categories = "";
                            for (int j = 0; j < 4; j++) {
                                String col = String.format("category%dName", j);
                                categories += String.valueOf(returnMap.get(col)) + " ";
                            }
                            Category category = category_build(String.valueOf(returnMap.get("productTitle")), categories);
                            cRepository.save(category);
                            Product_lowprice lowprice = lowprice_build(returnMap);
                            plRepository.save(lowprice);
                        }else {
                            Map<String, Object> returnMap = StringToMap(data_Arr[i]);
                            if(returnMap.get("lowMallList") == null) {
                                Product product = product_build(returnMap);
                                pRepository.save(product);
                                String categories = "";
                                for (int j = 0; j < 4; j++) {
                                    String col = String.format("category%dName", j);
                                    categories += String.valueOf(returnMap.get(col)) + " ";
                                }
                                Category category = category_build(String.valueOf(returnMap.get("productTitle")), categories);
                                cRepository.save(category);
                                Product_lowprice lowprice = lowprice_build(returnMap);
                                plRepository.save(lowprice);
                            }else{
                                Product product = product_build(returnMap);
                                pRepository.save(product);
                                String categories = "";
                                for (int j = 0; j < 4; j++) {
                                    String col = String.format("category%dName", j);
                                    categories += String.valueOf(returnMap.get(col)) + " ";
                                }
                                Category category = category_build(String.valueOf(returnMap.get("productTitle")), categories);
                                cRepository.save(category);
                                Category category2 = category_build(String.valueOf(returnMap.get("productTitle")), String.valueOf(returnMap.get("characterValue")));
                                cRepository.save(category2);
                                Product_lowprice lowprice = lowprice_build(returnMap);
                                plRepository.save(lowprice);
                                // List<Map<String, Object>> mall_list = (List<Map<String, Object>>) returnMap.get("lowMallList");
                                // for (int z = 0; z < mall_list.size(); z++) {
                                //     Product_mall mall = mall_build(mall_list.get(z), String.valueOf(returnMap.get("productTitle")));
                                //     pmRepository.save(mall);
                                // }
                            }
                        }
                    }
                    return "성공!";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "잘못되었습니다.";
    }
    
}
