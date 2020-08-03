package com.syz.eurekaserver.service.impl;

import com.syz.eurekaserver.entity.Country;
import com.syz.eurekaserver.mapper.CountryMapper;
import com.syz.eurekaserver.service.CountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author steven.sheng
 * @since 2020-08-03
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements CountryService {

}
