package org.jeecg.modules.azurecity.authentication.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.azurecity.authentication.entity.UserEntity;
import org.jeecg.modules.azurecity.authentication.exception.RepeatSubmitException;
import org.jeecg.modules.azurecity.authentication.mapper.AuthMapper;
import org.jeecg.modules.azurecity.authentication.mapper.SimpleSysUserMapper;
import org.jeecg.modules.azurecity.authentication.service.AuthService;
import org.jeecg.modules.azurecity.authentication.to.UserAuthTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description 实名认证操作
 * @date 2021/7/6
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private SimpleSysUserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String AUTH_CACHE_PREFIX = "auth:";

    @Override
    public void submitInformation(UserAuthTo user) throws RepeatSubmitException {
        // 先去缓存查
        String key = AUTH_CACHE_PREFIX + user.getId();
        String s = redisTemplate.opsForValue().get(key);
        // 先判断当前用户有没有提交过实名申请，如果提交过就不能重复提交申请，抛出异常
        if (s == null) {
            user.setUpdateTime(new Date());
            authMapper.insert(user);
            redisTemplate.opsForValue().set(key, JSON.toJSONString(user));
        } else {
            throw new RepeatSubmitException();
        }
    }

    @Override
    public void updateInformation(UserAuthTo user) {
        String key = AUTH_CACHE_PREFIX + user.getId();
        // 延迟双删
        redisTemplate.delete(key);
        user.setUpdateTime(new Date());
        authMapper.updateById(user);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisTemplate.delete(key);
    }

    @Override
    public void passAuth(String id) {
        String key = AUTH_CACHE_PREFIX + id;
        // 延迟双删
        redisTemplate.delete(key);
        UserEntity userEntity = userMapper.selectById(id);
        userEntity.setAuth(1);
        userMapper.updateById(userEntity);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisTemplate.delete(key);
    }

    @Cacheable
	@Override
	public List<UserAuthTo> getAuthList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageInfo<UserAuthTo> result = new PageInfo<UserAuthTo>(authMapper.getAllByPage());

        return result.getList();
    }

    @Cacheable
    @Override
    public UserEntity getUserById(String id) {
        return userMapper.selectById(id);
    }


}
