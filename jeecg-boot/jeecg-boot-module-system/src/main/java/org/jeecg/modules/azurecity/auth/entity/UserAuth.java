package org.jeecg.modules.azurecity.auth.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
 * @Description: 实名申请统计表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Data
@TableName("user_auth")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_auth对象", description="实名申请统计表")
public class UserAuth implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private java.lang.String userId;
	/**真实姓名*/
	@Excel(name = "真实姓名", width = 15)
    @ApiModelProperty(value = "真实姓名")
    private java.lang.String realname;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phone;
	/**身份证正面*/
	@Excel(name = "身份证正面", width = 15)
    @ApiModelProperty(value = "身份证正面")
    private java.lang.String idCardFront;
	/**身份证反面*/
	@Excel(name = "身份证反面", width = 15)
    @ApiModelProperty(value = "身份证反面")
    private java.lang.String idCardBack;
	/**提交或更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "提交或更新日期")
    private java.util.Date updateTime;
	/**状态*/
	@ApiModelProperty(value = "审核状态")
	private java.lang.Integer status;
}
