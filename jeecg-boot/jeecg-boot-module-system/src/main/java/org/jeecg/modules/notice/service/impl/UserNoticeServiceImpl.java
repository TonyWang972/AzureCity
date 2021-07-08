package org.jeecg.modules.notice.service.impl;

import org.jeecg.modules.notice.entity.NoticeMessage;
import org.jeecg.modules.notice.mapper.UserNoticeMapper;
import org.jeecg.modules.notice.service.UserNoticeService;
import org.jeecg.modules.notice.to.UserNoticeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
@Service
public class UserNoticeServiceImpl implements UserNoticeService {

	@Autowired
	private UserNoticeMapper userNoticeMapper;

	/**
	 * 保存所有的关联信息
	 * @param id
	 */
	@Override
	public void saveRelation(String id) {
		List<String> list = userNoticeMapper.getAllUserId();

		userNoticeMapper.saveAll(list, id);
	}

	@Override
	public List<UserNoticeTo> selectByUser(String userId) {
		List<UserNoticeTo> messages = userNoticeMapper.selectByUser(userId);

		return messages;
	}

	@Override
	public void updateReadFlag(String noticeId, String userId) {
		userNoticeMapper.updateReadFlag(noticeId, userId);
	}

}
