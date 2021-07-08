package org.jeecg.modules.azurecity.auth.service.impl;

import org.jeecg.modules.azurecity.auth.mapper.SimpleSysUserMapper;
import org.jeecg.modules.azurecity.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SimpleSysUserMapper userMapper;

	public void updateAuthStatus(String id) {
		userMapper.updateAuthStatus(id);
	}

	public Integer selectStatus(String id) {
		return userMapper.selectStatus(id);
	}

}
