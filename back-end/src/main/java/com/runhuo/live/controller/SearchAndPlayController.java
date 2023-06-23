package com.runhuo.live.controller;

import com.runhuo.live.service.SearchAndPlayService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 17:04
 */
@RestController
@RequestMapping("/searchAndPlay")
@SuppressWarnings("all")
public class SearchAndPlayController {
    @Autowired
    private SearchAndPlayService searchAndPlayService;

    @GetMapping("/getVideo/{index}/{keyword}")
    public R searchVideoByKeyword(@PathVariable("index")Integer index,
                                  @PathVariable("keyword")String keywords){
        return searchAndPlayService.searchVideoByKeyword(index,keywords);
    }
}
