package org.jeecg.modules.azurecity.authentication.service;

import org.jeecg.modules.azurecity.authentication.entity.UserEntity;
import org.jeecg.modules.azurecity.authentication.exception.RepeatSubmitException;
import org.jeecg.modules.azurecity.authentication.to.UserAuthTo;

import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/6
 */
public interface AuthService {

    void submitInformation(UserAuthTo user) throws RepeatSubmitException ;

	void updateInformation(UserAuthTo user);

    void passAuth(String id);

	List<UserAuthTo> getAuthList(int currentPage, int pageSize);

	UserEntity getUserById(String id);

}
