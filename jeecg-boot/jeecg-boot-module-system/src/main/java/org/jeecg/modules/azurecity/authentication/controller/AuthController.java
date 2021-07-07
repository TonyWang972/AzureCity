package org.jeecg.modules.azurecity.authentication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.azurecity.authentication.entity.UserEntity;
import org.jeecg.modules.azurecity.authentication.exception.RepeatSubmitException;
import org.jeecg.modules.azurecity.authentication.service.AuthService;
import org.jeecg.modules.azurecity.authentication.to.UserAuthTo;
import org.jeecg.modules.azurecity.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;

/**
 * @author Zzzhangyxi
 * @Description 实名认证相关模块
 * @date 2021/7/6
 */
@RequestMapping("/sys/auth")
@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户提交实名审核表单
     * @param user
     * @return
     */
    @ApiOperation("提交申请")
    @PostMapping("/submit-information")
    public R submitInformation(@RequestBody UserAuthTo user) {
        try {
            authService.submitInformation(user);
            return R.ok();
        } catch (RepeatSubmitException e) {
            return R.error(500, e.getMessage());
        }
    }

    /**
     * 用户修改实名表单信息
     * @param user
     * @return
     */
    @ApiOperation("修改申请")
    @PostMapping("/update-information")
    public R updateInformation(@RequestBody UserAuthTo user) {
        try {
            authService.updateInformation(user);
            return R.ok();
        } catch (RepeatSubmitException e) {
            return R.error(500, e.getMessage());
        }
    }

    /**
     * 管理员手动通过用户的实名申请
     * @param id
     * @return
     */
    @ApiOperation("通过认证")
    @GetMapping("/pass-auth")
    public R passAuth(@RequestParam String id) {
        authService.passAuth(id);

        return R.ok();
    }

    /**
     * 获取所有提交的申请
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return
     */
    @ApiOperation("获取申请")
    @GetMapping("/list")
    public R listAll(int currentPage, int pageSize) {
        List<UserAuthTo> list = authService.getAuthList(currentPage, pageSize);

        return R.ok().put("data", list);
    }

    /**
     * 获取某个用户信息
     * @param id
     * @return
     */
    @ApiOperation("获取用户")
    @GetMapping("/user/{id}")
    public R getUserById(@PathVariable("id") String id) {
        UserEntity user = authService.getUserById(id);

        return R.ok().put("data", user);
    }

}
