package org.jeecg.modules.azurecity.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
@Mapper
public interface SimpleSysUserMapper {
    void updateAuthStatus(@Param("id") String id);

    Integer selectStatus(String id);
}
