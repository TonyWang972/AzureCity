package org.jeecg.modules.notice.service;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.notice.entity.NoticeMessage;
import org.jeecg.modules.notice.to.UserNoticeTo;

import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
@Mapper
public interface UserNoticeService {
    void saveRelation(String id);

    List<UserNoticeTo> selectByUser(String userId);

    void updateReadFlag(String noticeId, String userId);
}
