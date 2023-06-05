package com.tan.service;
import com.tan.domain.LeaseR;
import com.tan.domain.Consumer;
import entity.PageResult;

/**
 * 租赁记录接口
 */
public interface LeaseRService {
    //新增租赁记录
    Integer addLeaseR(LeaseR leaseR);
//查询租赁记录
PageResult searchLeaseRs(LeaseR leaseR, Consumer consumer, Integer pageNum, Integer pageSize);
}
