package org.jeecg.modules.azurecity.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.azurecity.auth.entity.UserAuth;
import org.jeecg.modules.azurecity.auth.mapper.UserAuthMapper;
import org.jeecg.modules.azurecity.auth.service.IUserAuthService;
import org.jeecg.modules.azurecity.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 实名申请统计表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService {

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public void passAuth(String id) {
		UserAuth auth = this.getById(id);
		auth.setStatus(1);
		this.updateById(auth);

		userService.updateAuthStatus(auth.getUserId());
	}

}
