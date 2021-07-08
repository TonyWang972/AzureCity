package org.jeecg.modules.azurecity.auth.service;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/8
 */
public interface UserService {

	void updateAuthStatus(String id);

	Integer selectStatus(String id);
}
