package org.jeecg.modules.azurecity.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.azurecity.authentication.to.UserAuthTo;

import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/6
 */
public interface AuthMapper extends BaseMapper<UserAuthTo> {

	List getAllByPage();

}
