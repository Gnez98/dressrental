package com.tan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tan.domain.Dress;
import com.tan.domain.Consumer;
import com.tan.domain.LeaseR;
import com.tan.mapper.LeaseRMapper;
import com.tan.service.LeaseRService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LeaseRServiceImpl implements LeaseRService {
    @Autowired
    private LeaseRMapper leaseRMapper;

/**
 * 新增租赁记录
 * @param leaseR 新增的租赁记录
 */
@Override
public Integer addLeaseR(LeaseR leaseR) {
    return leaseRMapper.addLeaseR(leaseR);
}

/**
 * 查询租赁记录
 * @param leaseR 租赁记录的查询条件
 * @param consumer 当前的登录用户
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 */
@Override
public PageResult searchLeaseRs(LeaseR leaseR, Consumer consumer, Integer pageNum, Integer pageSize) {
    // 设置分页查询的参数，开始分页
    PageHelper.startPage(pageNum, pageSize);
    //如果不是管理员，则查询条件中的租赁人设置为当前登录用户
    if(!"ADMIN".equals(consumer.getRole())){
        leaseR.setBorrower(consumer.getName());
    }
    Page<LeaseR> page= leaseRMapper.searchLeaseRs(leaseR);
    return new PageResult(page.getTotal(),page.getResult());
}

}
