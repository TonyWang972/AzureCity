package org.jeecg.modules.upload.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/9
 */
@Mapper
public interface AvatarMapper {
  void updateAvatar(@Param("userId") String userId, @Param("avatar") String avatar);
}
