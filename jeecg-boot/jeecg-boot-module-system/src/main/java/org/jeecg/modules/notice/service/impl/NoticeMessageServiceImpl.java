package org.jeecg.modules.notice.service.impl;

import org.jeecg.modules.notice.entity.NoticeMessage;
import org.jeecg.modules.notice.mapper.NoticeMessageMapper;
import org.jeecg.modules.notice.service.INoticeMessageService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 通知信息表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Service
public class NoticeMessageServiceImpl extends ServiceImpl<NoticeMessageMapper, NoticeMessage> implements INoticeMessageService {

}
