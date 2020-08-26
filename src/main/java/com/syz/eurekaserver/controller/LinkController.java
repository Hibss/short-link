package com.syz.eurekaserver.controller;


import com.syz.eurekaserver.service.LinkService;
import com.syz.eurekaserver.thread.RequestVO;
import com.syz.eurekaserver.thread.ThreadLocalCache;
import com.syz.eurekaserver.utils.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.management.monitor.StringMonitor;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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

    @GetMapping("info")
    public String info() throws Exception {
        RequestVO vo = ThreadLocalCache.baseSignatureRequestThreadLocal.get();
        Map<String, String> res = new HashMap<>();
        res.put("getByVO", JacksonUtil.obj2json(linkService.getById(vo)));
        Map<String, String[]> parameterMap = ThreadLocalCache.requestMapLocalThread.get();
        if(parameterMap.containsKey("id")){
            String[] ids = parameterMap.get("id");
            if(ids.length > 1){
                res.put("getByParameterMap",JacksonUtil.obj2json(linkService.getByIds(ids)));
            }else{
                res.put("getByParameterMap",JacksonUtil.obj2json(linkService.getById(ids[0])));
            }
        }
        return JacksonUtil.obj2json(res);
    }

}

