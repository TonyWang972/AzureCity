package org.jeecg.modules.companyComplaints.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.lang.Console;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.companyComplaints.entity.CompanyComplaint;
import org.jeecg.modules.companyComplaints.service.ICompanyComplaintService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 企业整改投诉情况
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Api(tags="企业整改投诉情况")
@RestController
@RequestMapping("/companyComplaints/companyComplaint")
@Slf4j
public class CompanyComplaintController extends JeecgController<CompanyComplaint, ICompanyComplaintService> {
	@Autowired
	private ICompanyComplaintService companyComplaintService;
	
	/**
	 * 分页列表查询
	 *
	 * @param companyComplaint
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "企业整改投诉情况-分页列表查询")
	@ApiOperation(value="企业整改投诉情况-分页列表查询", notes="企业整改投诉情况-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CompanyComplaint companyComplaint,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CompanyComplaint> queryWrapper = QueryGenerator.initQueryWrapper(companyComplaint, req.getParameterMap());
		Page<CompanyComplaint> page = new Page<CompanyComplaint>(pageNo, pageSize);
		IPage<CompanyComplaint> pageList = companyComplaintService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param companyComplaint
	 * @return
	 */
	@AutoLog(value = "企业整改投诉情况-添加")
	@ApiOperation(value="企业整改投诉情况-添加", notes="企业整改投诉情况-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CompanyComplaint companyComplaint) {
		companyComplaintService.save(companyComplaint);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param companyComplaint
	 * @return
	 */
	@AutoLog(value = "企业整改投诉情况-编辑")
	@ApiOperation(value="企业整改投诉情况-编辑", notes="企业整改投诉情况-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CompanyComplaint companyComplaint) {
		companyComplaintService.updateById(companyComplaint);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "企业整改投诉情况-通过id删除")
	@ApiOperation(value="企业整改投诉情况-通过id删除", notes="企业整改投诉情况-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		companyComplaintService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "企业整改投诉情况-批量删除")
	@ApiOperation(value="企业整改投诉情况-批量删除", notes="企业整改投诉情况-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.companyComplaintService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "企业整改投诉情况-通过id查询")
	@ApiOperation(value="企业整改投诉情况-通过id查询", notes="企业整改投诉情况-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CompanyComplaint companyComplaint = companyComplaintService.getById(id);
		if(companyComplaint==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(companyComplaint);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param companyComplaint
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CompanyComplaint companyComplaint) {
        return super.exportXls(request, companyComplaint, CompanyComplaint.class, "企业整改投诉情况");
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
        return super.importExcel(request, response, CompanyComplaint.class);
    }

}
