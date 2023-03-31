package com.ateam.lionbuy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ateam.lionbuy.service.CrawlingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
    
    final private CrawlingService crawlingService;

    @RequestMapping({"", "/"})
    public String home() {
        return "home";
    }

    @PostMapping(value = "/keyword_crawling")
    public String keyword_crawling(@RequestParam("link") String link, Model model) {
        String keyword = crawlingService.getKeyword(link);
        model.addAttribute("keyword", keyword);
        return "key_crawling_result";
    }

    @PostMapping(value = "/data_crawling")
    public String data_crawling(@RequestParam("keyword") String keyword, Model model) {
        String html_result = crawlingService.start_crawling(keyword);
        model.addAttribute("html_result", html_result);
        return "data_crawling_result";
    }
}
