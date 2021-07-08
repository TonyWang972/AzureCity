package org.jeecg.modules.companyComplaints.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.companyComplaints.entity.CompanyComplaint;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 企业整改投诉情况
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
public interface ICompanyComplaintService extends IService<CompanyComplaint> {
    /**
     * 增加企业被投诉次数
     * @param companyName
     * @return
     */
    Result addCompanyComplant(String companyName);

    /**
     * 查询企业是否曾经被投诉
     * @param companyName
     * @return
     */
    Boolean companyComplantIsExist(String companyName);
}
