package org.jeecg.modules.notice.to;

import lombok.Data;

import java.util.Date;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
@Data
public class UserNoticeTo {
    // nm.id, nm,send_time, nm.titile, nm.sender, nm.msg_abstract, un.user_id, un.read_flag

    /**消息id*/
    private String id;

    private String userId;

    private Date sendTime;

    private String titile;

    private String sender;

    private String msgAbstract;

    private Integer readFlag;

}
