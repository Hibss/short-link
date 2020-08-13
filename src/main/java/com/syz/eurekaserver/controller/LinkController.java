package com.syz.eurekaserver.controller;


import com.syz.eurekaserver.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author steven.sheng
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("countryList")
    public String getCountryList() throws Exception {
        return linkService.getCountryList();
    }

    @GetMapping("cityList")
    public String getCityList() throws Exception {
        return linkService.getCityList();
    }

    @GetMapping("initShortLink")
    public String initShortLink() throws Exception {
        return linkService.initAllRestInterface();
    }

}

