package com.ateam.lionbuy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class CrawlingTest {
    
    @Test
    public void crawling() throws IOException{
        URL url = new URL("https://search.shopping.naver.com/search/all?query=%ED%85%80%EB%B8%94%EB%9F%AC&cat_id=&frm=NVSHATC");
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
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

        // 데이터 전처리
        String split_response = (response.split("application/json")[1]).split(">")[1].split("</scrip")[0];
        String list_split_response = (split_response.split("\"list\":\\[\\{")[1]).split("\\],\"total\":")[0];
        String[] list_split_response_Arr = list_split_response.split(",\"type\":\"product\"\\},\\{\"item\":");
        String[] data_Arr = new String[10];
        for (int i = 0; i < data_Arr.length; i++) {
            data_Arr[i] = list_split_response_Arr[i];
            File file = new File(String.format("C:/Users/me/Desktop/ateam_project/buyproject/src/test/java/com/ateam/buyproject/test_%d.txt", i));
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(data_Arr[i]);
            writer.close();
        }
        System.out.println(data_Arr[0]);
        for (int i = 0; i < data_Arr.length; i++) {
            if(i == 0) {
                String data = data_Arr[i].split("item\":")[1];
            }else {

            }
        }

        // 데이터를 파일로 저장
        File file = new File("C:/Users/me/Desktop/ateam_project/buyproject/src/test/java/com/ateam/buyproject/test.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(Arrays.toString(data_Arr));
        writer.close();

    }

    //String 객체를 Map타입으로 변환(jackson)
    public Map<String, Object> jsonToMap(String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> returnMap = mapper.readValue(result, Map.class);
        return returnMap;
    }
}
