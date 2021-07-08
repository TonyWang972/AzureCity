package org.jeecg.modules.azurecity.auth.service;

import org.jeecg.modules.azurecity.auth.entity.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 实名申请统计表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
public interface IUserAuthService extends IService<UserAuth> {

	void passAuth(String id);

}
