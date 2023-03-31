package com.ateam.lionbuy.service;

public interface CrawlingService {

    String getKeyword(String link);

    String start_crawling(String keyword);
}
