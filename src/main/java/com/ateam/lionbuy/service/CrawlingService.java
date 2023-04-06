package com.ateam.lionbuy.service;

import java.io.IOException;
import java.util.Map;

import com.ateam.lionbuy.entity.Category;
import com.ateam.lionbuy.entity.Product;
import com.ateam.lionbuy.entity.Product_lowprice;
import com.ateam.lionbuy.entity.Product_mall;
import com.ateam.lionbuy.util.FileMake;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface CrawlingService {

    FileMake fm = new FileMake();

    String getKeyword(String link);

    String start_crawling(String keyword);

    default String[] data_preprocessing(String response) {
        String split_response = (response.split("application/json")[1]).split(">")[1].split("</scrip")[0];
        String list_split_response = (split_response.split("\"list\":\\[\\{")[1]).split("\\],\"total\":")[0];
        return list_split_response.split(",\"type\":\"product\"\\},\\{\"item\":");
    }

    default Map<String, Object> StringToMap(String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> returnMap = mapper.readValue(result, Map.class);
        return returnMap;
    }

    default Product product_build(Map<String, Object> returnMap) {
        Product product = Product.builder()
            .pd_name(String.valueOf(returnMap.get("productTitle")))
            .image_url(String.valueOf(returnMap.get("imageUrl")))
            .pd_lowprice(String.valueOf(returnMap.get("lowPrice")))
            .build();
        return product;
    }

    default Category category_build(String producttitle, String categories) {
        Product product = Product.builder()
            .pd_name(producttitle)
            .build();
        Category category = Category.builder()
            .product(product)
            .categories(categories)
            .build();
        return category;
    }

    default Product_lowprice lowprice_build(Map<String, Object> returnmap) {
        Product product = Product.builder()
            .pd_name(String.valueOf(returnmap.get("productTitle")))
            .build();
        Product_lowprice lowprice = Product_lowprice.builder()
            .product(product)
            .pd_lowprice(String.valueOf(returnmap.get("lowPrice")))
            .build();
        return lowprice;
    }

    default Product_mall mall_build_entity(Map<String, Object> returnmap, String mall_name, String price) {
        Product product = Product.builder()
            .pd_name(String.valueOf(returnmap.get("productTitle")))
            .build();
        Product_mall mall = Product_mall.builder()
            .product(product)
            .mall_name(mall_name)
            .price(price)
            .build();
        return mall;
    }


}
