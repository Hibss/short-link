package com.syz.eurekaserver;

import com.syz.eurekaserver.entity.Link;
import com.syz.eurekaserver.service.LinkService;
import com.syz.eurekaserver.utils.ShortUrlUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EurekaServerApplicationTests {


	@Autowired
	private LinkService linkService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void cityList() throws Exception {
		System.out.println(linkService.getCityList());
	}

	@Test
	public void insertLink(){
		String uri = "link/countryList";
		Link link = new Link();
		link.setFullUrl(uri);
		linkService.insert(link);
		String code = ShortUrlUtils.base62Encode(link.getId());
		link.setShortUrl(code);
		linkService.updateById(link);
	}

}
