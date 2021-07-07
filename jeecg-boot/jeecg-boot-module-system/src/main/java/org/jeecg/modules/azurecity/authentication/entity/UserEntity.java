package org.jeecg.modules.azurecity.authentication.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Zzzhangyxi
 * @Description 用户认证类
 * @date 2021/7/6
 */
@Data
@TableName("sys_user")
public class UserEntity {

    @TableId
    private String id;

    private String username;

    private String realname;

    private String phone;

    private Integer auth;

}
