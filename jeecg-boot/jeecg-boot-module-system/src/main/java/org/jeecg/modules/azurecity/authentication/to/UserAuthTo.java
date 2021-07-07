package org.jeecg.modules.azurecity.authentication.to;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Zzzhangyxi
 * @Description 用户在实名认证时需要提交的信息
 * @date 2021/7/6
 */
@Data
@TableName("sys_user_auth")
public class UserAuthTo {

    @TableId
    private String id;

    private String realname;

    private String phone;

    // 身份证正面照片url
    private String idCardFront;

    // 身份证反面照片url
    private String idCardBack;

    // 提交或更新日期
    private Date updateTime;

}
