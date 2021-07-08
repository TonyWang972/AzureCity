package org.jeecg.modules.pollutionComplaints.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 污染投诉表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Data
@TableName("pollution_complaints")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pollution_complaints对象", description="污染投诉表")
public class PollutionComplaints implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**投诉对象名称*/
	@Excel(name = "投诉对象名称", width = 15)
    @ApiModelProperty(value = "投诉对象名称")
    private java.lang.String cptObject;
	/**投诉描述 */
	@Excel(name = "投诉描述 ", width = 15)
    @ApiModelProperty(value = "投诉描述 ")
    private java.lang.String cptDescribe;
	/**投诉图片*/
	@Excel(name = "投诉图片", width = 15)
    @ApiModelProperty(value = "投诉图片")
    private java.lang.String cptPic;
	/**投诉状态*/
	@Excel(name = "投诉状态", width = 15, dicCode = "complaint_code")
	@Dict(dicCode = "complaint_code")
    @ApiModelProperty(value = "投诉状态")
    private java.lang.String cptState;
	/**污染投诉类型*/
	@Excel(name = "污染投诉类型", width = 15)
    @ApiModelProperty(value = "污染投诉类型")
    private java.lang.String cptType;
	/**污染投诉类型*/
	@Excel(name = "污染投诉类型", width = 15)
    @ApiModelProperty(value = "污染投诉类型")
    private java.lang.String cptTypeText;
	/**投诉简要位置*/
	@Excel(name = "投诉简要位置", width = 15)
    @ApiModelProperty(value = "投诉简要位置")
    private java.lang.String cptPositionBrief;
	/**投诉详细位置*/
	@Excel(name = "投诉详细位置", width = 15)
    @ApiModelProperty(value = "投诉详细位置")
    private java.lang.String cptPositionDetailil;
	/**投诉位置经度*/
	@Excel(name = "投诉位置经度", width = 15)
    @ApiModelProperty(value = "投诉位置经度")
    private java.lang.String cptPositionLongitude;
	/**投诉位置纬度*/
	@Excel(name = "投诉位置纬度", width = 15)
    @ApiModelProperty(value = "投诉位置纬度")
    private java.lang.String cptPositionLatitude;
	/**投诉提交日期*/
	@Excel(name = "投诉提交日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "投诉提交日期")
    private java.util.Date cptSubmitTime;
}
