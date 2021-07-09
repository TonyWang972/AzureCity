package org.jeecg.modules.pollutionComplaints.service;

import org.jeecg.modules.pollutionComplaints.entity.ComplaintData;
import org.jeecg.modules.pollutionComplaints.entity.PollutionComplaints;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 污染投诉表
 * @Author: jeecg-boot
 * @Date:   2021-07-08
 * @Version: V1.0
 */
public interface IPollutionComplaintsService extends IService<PollutionComplaints> {
    /**
     * 获取投诉信息列表
     * @return
     */
    List<ComplaintData> getComplimentData();
}
