package org.jeecg.modules.notice.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.notice.entity.NoticeMessage;
import org.jeecg.modules.notice.to.UserNoticeTo;

import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
public interface UserNoticeMapper {
	List<String> getAllUserId();

	void saveAll(@Param("users") List<String> users, @Param("id") String id);

	List<UserNoticeTo> selectByUser(@Param("userId") String userId);

	void updateReadFlag(@Param("noticeId") String noticeId, @Param("userId") String userId);
}
