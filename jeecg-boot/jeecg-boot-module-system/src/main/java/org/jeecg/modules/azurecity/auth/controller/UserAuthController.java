package org.jeecg.modules.azurecity.auth.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.azurecity.auth.entity.UserAuth;
import org.jeecg.modules.azurecity.auth.service.IUserAuthService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.azurecity.auth.service.UserService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 实名申请统计表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Api(tags="实名申请统计表")
@RestController
@RequestMapping("/auth/user-auth")
@Slf4j
public class UserAuthController extends JeecgController<UserAuth, IUserAuthService> {
	@Autowired
	private IUserAuthService userAuthService;
	@Autowired
	private UserService userService;

	@AutoLog(value = "实名申请统计表-通过审核")
	@ApiOperation(value = "实名申请统计表-通过审核", notes = "实名申请统计表-通过审核")
	@GetMapping(value = "/pass")
	public Result<?> passAuth(@RequestParam("id") String id) {
		userAuthService.passAuth(id);
		return Result.OK("已通过！");
	}

	/**
	 * 分页列表查询
	 *
	 * @param userAuth
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "实名申请统计表-分页列表查询")
	@ApiOperation(value="实名申请统计表-分页列表查询", notes="实名申请统计表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(UserAuth userAuth,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserAuth> queryWrapper = QueryGenerator.initQueryWrapper(userAuth, req.getParameterMap());
		Page<UserAuth> page = new Page<UserAuth>(pageNo, pageSize);
		IPage<UserAuth> pageList = userAuthService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param userAuth
	 * @return
	 */
	@AutoLog(value = "实名申请统计表-添加")
	@ApiOperation(value="实名申请统计表-添加", notes="实名申请统计表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody UserAuth userAuth) {
		// 先查询，如果已经实名则不让提交
		Integer status = userService.selectStatus(userAuth.getUserId());
		if (status.equals(1)) {
			return Result.error("该用户已经实名过！");
		} else {
			userAuthService.save(userAuth);
			return Result.OK("添加成功！");
		}
	}
	
	/**
	 *  编辑
	 *
	 * @param userAuth
	 * @return
	 */
	@AutoLog(value = "实名申请统计表-编辑")
	@ApiOperation(value="实名申请统计表-编辑", notes="实名申请统计表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody UserAuth userAuth) {
		userAuthService.updateById(userAuth);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "实名申请统计表-通过id删除")
	@ApiOperation(value="实名申请统计表-通过id删除", notes="实名申请统计表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		userAuthService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "实名申请统计表-批量删除")
	@ApiOperation(value="实名申请统计表-批量删除", notes="实名申请统计表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userAuthService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "实名申请统计表-通过id查询")
	@ApiOperation(value="实名申请统计表-通过id查询", notes="实名申请统计表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		UserAuth userAuth = userAuthService.getById(id);
		if(userAuth==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(userAuth);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userAuth
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserAuth userAuth) {
        return super.exportXls(request, userAuth, UserAuth.class, "实名申请统计表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, UserAuth.class);
    }

}
