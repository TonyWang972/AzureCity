package org.jeecg.modules.companyComplaints.mapper;

import org.jeecg.modules.companyComplaints.entity.CompanyComplaint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 企业整改投诉情况
 * @Author: Tonywang
 * @Date:   2021-07-08
 * @Version: V1.0
 */
public interface CompanyComplaintMapper extends BaseMapper<CompanyComplaint> {
    /**
     * 增加企业被投诉次数
     */
    int addCompanyComplant(String companyName);

    /**
     * 查询企业是否被投诉
     */
    int selectByCompanyName(String companyName);
}
