package com.syz.eurekaserver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.syz.eurekaserver.entity.Link;
import com.syz.eurekaserver.service.LinkService;
import com.syz.eurekaserver.utils.JacksonUtil;
import com.syz.eurekaserver.utils.ShortUrl62Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EurekaServerApplicationTests {


	@Autowired
	private LinkService linkService;


	@Autowired
	WebApplicationContext applicationContext;

	@Test
	public void contextLoads() {
	}

	@Test
	public void cityList() throws Exception {
		System.out.println(linkService.getCityList());
	}

@Test
	public void getAllUrl() {
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		// 获取url与类和方法的对应信息
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

//		List<String> urlList = new ArrayList<>();
//		for (RequestMappingInfo info : map.keySet()) {
//			// 获取url的Set集合，一个方法可能对应多个url
//			Set<String> patterns = info.getPatternsCondition().getPatterns();
//
//			for (String url : patterns) {
//				urlList.add(url);
//			}
//		}
//		System.out.println("-----------------------------------");
//
//		urlList.stream().forEach(System.out::println);
		System.out.println("-----------------------------------");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			Map<String, String> map1 = new HashMap<String, String>();
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			for (String url : p.getPatterns()) {
				map1.put("url", url);
			}
			map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
			map1.put("package",method.getMethod().getDeclaringClass().getPackage().getName());
			map1.put("method", method.getMethod().getName()); // 方法名
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			for (RequestMethod requestMethod : methodsCondition.getMethods()) {
				map1.put("type", requestMethod.toString());
			}

			list.add(map1);
		}
		list.stream().forEach(System.out::println);
		System.out.println("-----------------------------------");
	}

}
