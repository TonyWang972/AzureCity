package org.jeecg.modules.title.service.impl;

import org.jeecg.modules.title.mapper.UserComplainMapper;
import org.jeecg.modules.title.service.UserTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/9
 */
@Service
public class UserTitleServiceImpl implements UserTitleService {
    @Autowired
    private UserComplainMapper userComplainMapper;

    @Override
    public String getUserTitle(String username) {
        String res;
        Integer num = userComplainMapper.getUserComplainsNum(username);

        int n = num.intValue();
        if (n < 10) {
            res = "环保萌新";
        } else if (n < 20) {
            res = "环保勇士";
        } else if (n < 30) {
            res = "环保达人";
        } else if (n < 40) {
            res = "环保健将";
        } else {
            res = "环保的神";
        }

        return res;
    }
}
