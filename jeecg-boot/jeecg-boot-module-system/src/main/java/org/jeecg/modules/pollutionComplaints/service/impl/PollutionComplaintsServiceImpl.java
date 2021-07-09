package org.jeecg.modules.pollutionComplaints.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.pollutionComplaints.entity.ComplaintData;
import org.jeecg.modules.pollutionComplaints.entity.PollutionComplaints;
import org.jeecg.modules.pollutionComplaints.mapper.PollutionComplaintsMapper;
import org.jeecg.modules.pollutionComplaints.service.IPollutionComplaintsService;
import org.jeecg.modules.utill.DateUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 污染投诉表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
@Service
public class PollutionComplaintsServiceImpl extends ServiceImpl<PollutionComplaintsMapper, PollutionComplaints> implements IPollutionComplaintsService {
    @Resource
    private PollutionComplaintsMapper pollutionComplaintsMapper;

    @Override
    public List<ComplaintData> getComplimentData() {
        List<ComplaintData>  complaintDataList = new ArrayList<>();
        //循环七天3
        List pastSevenDays = DateUtil.getDaysBetwwen(7);
        for(int i=0 ; i<8;i++){
            ComplaintData complaintData = new ComplaintData();
            complaintData.setDate(pastSevenDays.get(i).toString());
            int num = pollutionComplaintsMapper.selectComplimentNumByDay(pastSevenDays.get(i).toString());
            String strNum = Integer.toString(num);
            complaintData.setComplaintNum(strNum);
            complaintDataList.add(complaintData);
        }
        return complaintDataList;
    }
}
