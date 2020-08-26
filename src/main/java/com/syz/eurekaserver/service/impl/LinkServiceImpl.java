package com.syz.eurekaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.syz.eurekaserver.entity.City;
import com.syz.eurekaserver.entity.Country;
import com.syz.eurekaserver.entity.Link;
import com.syz.eurekaserver.mapper.CityMapper;
import com.syz.eurekaserver.mapper.CountryMapper;
import com.syz.eurekaserver.mapper.LinkMapper;
import com.syz.eurekaserver.service.LinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syz.eurekaserver.thread.RequestVO;
import com.syz.eurekaserver.utils.JacksonUtil;
import com.syz.eurekaserver.utils.ShortUrl62Utils;
import com.syz.eurekaserver.utils.ShortUrlMD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author steven.sheng
 * @since 2020-08-03
 */
@Service(value = "linkService")
@Slf4j
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    private static final String CONTROLLER_PACKAGE = "com.syz.eurekaserver";
    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public Link queryById(Long linkId) {
        log.info("link queryById :{}",linkId);
        return linkMapper.selectById(linkId);
    }

    @Override
    public String getCityList() throws Exception {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("countryCode","CHN");
        List<City> cityList = cityMapper.selectList(queryWrapper);
        if(cityList.isEmpty()){
            return "";
        }else{
            List<String> collect = cityList.stream().map(City::getName).collect(Collectors.toList());
            return JacksonUtil.obj2json(collect);
        }
    }

    @Override
    public String getCountryList() throws Exception {
        QueryWrapper<Country> queryWrapper = new QueryWrapper<>();
        List<Country> countryList = countryMapper.selectList(queryWrapper);
        if(countryList.isEmpty()){
            return "";
        }else{
            List<String> collect = countryList.stream().map(Country::getName).collect(Collectors.toList());
            return JacksonUtil.obj2json(collect);
        }
    }

    @Override
    public Long insert(Link link) {
        linkMapper.insert(link);
        return link.getId();
    }

    @Override
    public String initAllRestInterface() throws Exception {
        List<Link> linkList = this.getAll();
        if(!CollectionUtils.isEmpty(linkList)){
            return JacksonUtil.obj2json(linkList);
        }
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.entrySet().stream()
                .filter(entry->entry.getValue().getMethod().getDeclaringClass().getPackage().getName().contains(CONTROLLER_PACKAGE))
                .forEach(entry->{
                    Map<String, String> map1 = new HashMap<String, String>();
                    RequestMappingInfo info = entry.getKey();
//                    HandlerMethod method = entry.getValue();
                    PatternsRequestCondition p = info.getPatternsCondition();

                    for (String url : p.getPatterns()) {

                        map1.put("url", url);
                    }
//                    map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
//                    map1.put("package",method.getMethod().getDeclaringClass().getPackage().getName());
//                    map1.put("method", method.getMethod().getName()); // 方法名
//                    RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
//                    for (RequestMethod requestMethod : methodsCondition.getMethods()) {
//                        map1.put("type", requestMethod.toString());
//                    }
                    //1.使用ShortUrl62Utils 62进制进行短链接处理
//                    Link link = Link.builder()
//                            .fullUrl(map1.get("url"))
//                            .build();
//                    this.insert(link);
//                    link.setShortUrl(ShortUrl62Utils.base62Encode(link.getId()));
//                    this.updateById(link);
//                    map1.put("id",link.getId().toString());

                    //2.使用md5进行加密
                    Link link = Link.builder()
                            .fullUrl(map1.get("url"))
                            .shortUrl(ShortUrlMD5Util.shortUrl(map1.get("url")))
                            .build();
                    this.baseMapper.insert(link);
                    linkList.add(link);
                });
        linkList.stream().forEach(System.out::println);
        return JacksonUtil.obj2json(linkList);
    }

    @Override
    public Link queryByShort(String shortUrl) {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Link::getShortUrl,shortUrl);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Link> getByIds(String[] ids) {
        if(ids == null || ids.length == 0){
            return Collections.EMPTY_LIST;
        }
        List<Long> idList =
                Arrays.stream(ids).map(obj->Long.parseLong(obj)).collect(Collectors.toList());
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(Link::getId,idList);
        return linkMapper.selectList(queryWrapper);
    }

    @Override
    public Link getById(RequestVO vo) {
        return linkMapper.getById(vo.getId());
    }

    @Override
    public Link getById(String id) {
        return linkMapper.getById(Long.parseLong(id));
    }

    private List<Link> getAll() {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectList(queryWrapper);
    }
}
