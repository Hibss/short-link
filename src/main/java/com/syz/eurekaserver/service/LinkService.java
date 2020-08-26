package com.syz.eurekaserver.service;

import com.syz.eurekaserver.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syz.eurekaserver.thread.RequestVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author steven.sheng
 * @since 2020-08-03
 */
public interface LinkService extends IService<Link> {

    Link queryById(Long linkId);

    String getCityList() throws Exception;

    String getCountryList() throws Exception;

    Long insert(Link link);

    String initAllRestInterface() throws Exception;

    Link getById(RequestVO vo);

    Link getById(String id);

    Link queryByShort(String substring);

    List<Link> getByIds(String[] ids);
}
