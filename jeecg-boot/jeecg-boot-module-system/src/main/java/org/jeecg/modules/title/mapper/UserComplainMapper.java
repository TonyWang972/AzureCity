package org.jeecg.modules.title.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/9
 */
public interface UserComplainMapper {
    Integer getUserComplainsNum(@Param("username") String username);
}
