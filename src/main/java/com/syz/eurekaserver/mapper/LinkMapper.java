package com.syz.eurekaserver.mapper;

import com.syz.eurekaserver.entity.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syz.eurekaserver.thread.RequestVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Generated;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author steven.sheng
 * @since 2020-08-03
 */
public interface LinkMapper extends BaseMapper<Link> {

    @Select("select * from t_link where id = #{id}")
    Link getById(RequestVO vo);
}
