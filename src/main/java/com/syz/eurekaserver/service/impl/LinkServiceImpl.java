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
import com.syz.eurekaserver.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountryMapper countryMapper;

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
}
