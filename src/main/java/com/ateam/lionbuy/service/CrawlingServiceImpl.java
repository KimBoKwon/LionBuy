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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CrawlingServiceImpl implements CrawlingService{

    // repository 들을 가져온 부분
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
                // 가져온 keyword를 인코딩하는 코드
                encodedKeyword = URLEncoder.encode(keyword, "UTF-8");

                // 우리가 크롤링할 url을 지정하는 코드
                url = new URL("https://search.shopping.naver.com/search/all?query="+ encodedKeyword +"&cat_id=&frm=NVSHATC");

                // url에 연결하여 요청하는데 필요한 설정(header)값을 지정
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
                    // 최종적으로 가져온 html 코드를 response 변수에 할당
                    String response = s.hasNext() ? s.next() : "";

                    // data_preprocessing 메서드를 사용하여 데이터 전처리
                    // data_preprocessing 메서드는 CrawlingService 클래스에 있음
                    String[] ppc_data = data_preprocessing(response);

                    // 많은 데이터중에 10개만 담을 배열 선언
                    String[] data_Arr = new String[10];

                    // data_Arr 배열에 10개의 데이터를 담는 코드
                    for (int i = 0; i < data_Arr.length; i++) {
                        data_Arr[i] = ppc_data[i];
                    }

                    // 데이터들을 DB에 넣는 과정
                    for (int i = 0; i < data_Arr.length; i++) {
                        // 첫번째 데이터에는 앞에 item": 가 붙어있어서 제거후 작업
                        if(i == 0) {
                            // split 함수를 사용하여 제거
                            String data = data_Arr[i].split("item\":")[1];

                            // 가져온 String data 객체를 Map으로 변환하는 과정
                            // StringToMap 메서드는 CrawlingService 클래스에 있음
                            Map<String, Object> returnMap = StringToMap(data);

                            // Map타입의 데이터를 Entity로 변환하여 디비에 저장하는 코드
                            // product_build 메서드는 CrawlingService 클래스에 있음
                            Product product = product_build(returnMap);
                            pRepository.save(product);

                            // 데이터에 카테고리가 여러개의 key에 존재하고 있어서 한 String 변수에 뭉쳐서 디비에 넣은 코드
                            // 데이터에서 category1Name 이런식으로 key가 정의되어 있음
                            String categories = "";
                            for (int j = 0; j < 4; j++) {
                                String col = String.format("category%dName", j+1);
                                categories += String.valueOf(returnMap.get(col)) + " ";
                            }
                            // DB에 넣을 데이터를 Entity에 넣어 디비에 저장
                            // category_build 메서드는 CrawlingService 클래스에 있음
                            Category category = category_build(String.valueOf(returnMap.get("productTitle")), categories);
                            cRepository.save(category);
                            Category category2 = category_build(String.valueOf(returnMap.get("productTitle")), String.valueOf(returnMap.get("characterValue")));
                            cRepository.save(category2);

                            // 최저가 테이블에 최저가 데이터를 넣는 코드
                            // DB에 넣을 데이터를 Entity에 넣어 디비에 저장
                            // lowprice_build 메서드는 CrawlingService 클래스에 있음
                            Product_lowprice lowprice = lowprice_build(returnMap);
                            plRepository.save(lowprice);
                        }else {
                            Map<String, Object> returnMap = StringToMap(data_Arr[i]);
                            // 쇼핑몰리스트의 값이 있는지 없는지의 기준으로 if문을 나누었음
                            if(returnMap.get("lowMallList") == null) {
                                // 위에 코드와 같다
                                Product product = product_build(returnMap);
                                pRepository.save(product);
                                String categories = "";
                                for (int j = 0; j < 4; j++) {
                                    String col = String.format("category%dName", j+1);
                                    categories += String.valueOf(returnMap.get(col)) + " ";
                                }
                                Category category = category_build(String.valueOf(returnMap.get("productTitle")), categories);
                                cRepository.save(category);
                                Category category2 = category_build(String.valueOf(returnMap.get("productTitle")), String.valueOf(returnMap.get("characterValue")));
                                cRepository.save(category2);
                                Product_lowprice lowprice = lowprice_build(returnMap);
                                plRepository.save(lowprice);
                            }else{
                                // 위에 코드와 같으나 차이점이 있다면 요기에 들어오는 데이터들은 쇼핑몰 데이터가 있어서
                                // product_mall 테이블에 쇼핑몰 데이터를 저장
                                Product product = product_build(returnMap);
                                pRepository.save(product);
                                String categories = "";
                                for (int j = 0; j < 4; j++) {
                                    String col = String.format("category%dName", j+1);
                                    categories += String.valueOf(returnMap.get(col)) + " ";
                                }
                                Category category = category_build(String.valueOf(returnMap.get("productTitle")), categories);
                                cRepository.save(category);
                                log.info(">>>" + String.valueOf(returnMap.get("characterValue")));
                                Category category2 = category_build(String.valueOf(returnMap.get("productTitle")), String.valueOf(returnMap.get("characterValue")));
                                cRepository.save(category2);
                                Product_lowprice lowprice = lowprice_build(returnMap);
                                plRepository.save(lowprice);

                                // 요부분이 쇼핑몰 데이터를 저장하는 부분
                                // 쇼핑몰 데이터가 Object 타입으로 되어있지만 실제론 List<Map<String, Object>> 타입으로 되어있다
                                // 하나 하나 데이터를 따로 넣고 싶어서 ObjectMapper 클래스를 사용하여
                                // Object를 list로 만들었다
                                // mall_build_entity 메서드는 넣고 싶은 데이터들을 Entity로 정의하는 메서드 이다
                                // mall_build_entity 메서드는 CrawlingService 클래스에 있다
                                ObjectMapper ObjectMapper = new ObjectMapper();
                                String lowMallStr = ObjectMapper.writeValueAsString(returnMap.get("lowMallList"));
                                List<Map<String, Object>> lowMallList = ObjectMapper.readValue(lowMallStr, new TypeReference<List<Map<String, Object>>>(){});
                                for(Map<String, Object> lowMallMap : lowMallList) {
                                    Product_mall mall = mall_build_entity(returnMap, String.valueOf(lowMallMap.get("name")), String.valueOf(lowMallMap.get("price")));
                                    pmRepository.save(mall);
                                }
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
