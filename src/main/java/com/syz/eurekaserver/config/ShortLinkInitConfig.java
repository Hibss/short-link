package com.syz.eurekaserver.config;

import com.syz.eurekaserver.controller.LinkController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//主动初始化所有链接转换短链接
@Component
@Slf4j
public class ShortLinkInitConfig implements CommandLineRunner {

    @Autowired
    private LinkController linkController;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("init short link start");
        String shortLink = linkController.initShortLink();
        log.info("short link info :{}", shortLink);
        log.info("init short link finish");
    }
}
