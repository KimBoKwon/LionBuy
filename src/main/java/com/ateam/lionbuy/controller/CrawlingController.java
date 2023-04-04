package com.ateam.lionbuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.lionbuy.dto.LinkDTO;
import com.ateam.lionbuy.service.CrawlingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/crawling")
@RequiredArgsConstructor
public class CrawlingController {
    
    @Autowired
    private final CrawlingService crawlingService;

    @PostMapping(value = "/start_crawling")
    public ResponseEntity<String> start_crawling(@RequestBody LinkDTO linkdto) {
        String keyword = crawlingService.getKeyword(linkdto.getLink());
        String crawling_result = crawlingService.start_crawling(keyword);
        return ResponseEntity.ok().body(crawling_result);
    }
}
