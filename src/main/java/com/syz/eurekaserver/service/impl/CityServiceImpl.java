package com.syz.eurekaserver.service.impl;

import com.syz.eurekaserver.entity.City;
import com.syz.eurekaserver.mapper.CityMapper;
import com.syz.eurekaserver.service.CityService;
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
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

}
