package org.jeecg.modules.notice.controller;

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
import org.jeecg.modules.notice.entity.NoticeMessage;
import org.jeecg.modules.notice.service.INoticeMessageService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.notice.service.UserNoticeService;
import org.jeecg.modules.notice.to.UserNoticeTo;
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
 * @Description: 通知信息表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Api(tags="通知信息表")
@RestController
@RequestMapping("/notice/noticeMessage")
@Slf4j
public class NoticeMessageController extends JeecgController<NoticeMessage, INoticeMessageService> {
	@Autowired
	private INoticeMessageService noticeMessageService;
	@Autowired
	private UserNoticeService userNoticeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param noticeMessage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "通知信息表-分页列表查询")
	@ApiOperation(value="通知信息表-分页列表查询", notes="通知信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NoticeMessage noticeMessage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NoticeMessage> queryWrapper = QueryGenerator.initQueryWrapper(noticeMessage, req.getParameterMap());
		Page<NoticeMessage> page = new Page<NoticeMessage>(pageNo, pageSize);
		IPage<NoticeMessage> pageList = noticeMessageService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param noticeMessage
	 * @return
	 */
	@AutoLog(value = "通知信息表-添加")
	@ApiOperation(value="通知信息表-添加", notes="通知信息表-添加")
	@PostMapping(value = "/add")
	@Transactional
	public Result<?> add(@RequestBody NoticeMessage noticeMessage) {
		noticeMessageService.save(noticeMessage);
		// 给关联表添加信息
		userNoticeService.saveRelation(noticeMessage.getId());

		return Result.OK("添加成功！");
	}

	 /**
	  *   添加
	  *
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "通知信息表-查询")
	 @ApiOperation(value="通知信息表-查询", notes="通知信息表-查询")
	 @GetMapping(value = "/list/user")
	 public Result<?> selectByUserId(@RequestParam("userId") String userId) {
	 	List<UserNoticeTo> message = userNoticeService.selectByUser(userId);

	 	return Result.OK(message);
	 }

	 /**
	  *   修改已读
	  *
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "通知信息表-修改已读")
	 @ApiOperation(value="通知信息表-修改已读", notes="通知信息表-修改已读")
	 @GetMapping(value = "/read")
	 public Result<?> updateReadFlag(@RequestParam String noticeId, @RequestParam String userId) {
		 userNoticeService.updateReadFlag(noticeId, userId);

		 return Result.OK("修改成功！");
	 }
	
	/**
	 *  编辑
	 *
	 * @param noticeMessage
	 * @return
	 */
	@AutoLog(value = "通知信息表-编辑")
	@ApiOperation(value="通知信息表-编辑", notes="通知信息表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NoticeMessage noticeMessage) {
		noticeMessageService.updateById(noticeMessage);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "通知信息表-通过id删除")
	@ApiOperation(value="通知信息表-通过id删除", notes="通知信息表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		noticeMessageService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "通知信息表-批量删除")
	@ApiOperation(value="通知信息表-批量删除", notes="通知信息表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.noticeMessageService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "通知信息表-通过id查询")
	@ApiOperation(value="通知信息表-通过id查询", notes="通知信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		NoticeMessage noticeMessage = noticeMessageService.getById(id);
		if(noticeMessage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(noticeMessage);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param noticeMessage
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NoticeMessage noticeMessage) {
        return super.exportXls(request, noticeMessage, NoticeMessage.class, "通知信息表");
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
        return super.importExcel(request, response, NoticeMessage.class);
    }

}
