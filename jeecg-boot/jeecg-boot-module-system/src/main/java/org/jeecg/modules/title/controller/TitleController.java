package org.jeecg.modules.title.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.title.service.UserTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/9
 */
@Api(tags = "用户标题")
@RestController
public class TitleController {

    @Autowired
    private UserTitleService userTitleService;

    @AutoLog(value = "用户标题")
    @ApiOperation(value="用户标题", notes="用户标题")
    @GetMapping("/title")
    public Result<?> getUserTitle(@RequestParam("username") String username) {
      String result = userTitleService.getUserTitle(username);

      return Result.OK("查询成功！", result);
    }
}
