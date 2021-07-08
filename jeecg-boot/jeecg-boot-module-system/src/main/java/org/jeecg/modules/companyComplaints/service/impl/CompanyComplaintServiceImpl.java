package org.jeecg.modules.companyComplaints.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.companyComplaints.entity.CompanyComplaint;
import org.jeecg.modules.companyComplaints.mapper.CompanyComplaintMapper;
import org.jeecg.modules.companyComplaints.service.ICompanyComplaintService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 企业整改投诉情况
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Service
public class CompanyComplaintServiceImpl extends ServiceImpl<CompanyComplaintMapper, CompanyComplaint> implements ICompanyComplaintService {
    @Resource
    private CompanyComplaintMapper companyComplaintMapper;

    @Override
    public Result addCompanyComplant(String companyName) {
        companyComplaintMapper.addCompanyComplant(companyName);
        return Result.OK();
        }

    @Override
    public Boolean companyComplantIsExist(String companyName){
        if(companyComplaintMapper.selectByCompanyName(companyName)>0){
            return true;
        }else{
            return false;
        }
    }
}
