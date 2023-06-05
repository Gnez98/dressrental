package com.tan.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tan.domain.Dress;
import com.tan.domain.LeaseR;
import com.tan.domain.Consumer;
import com.tan.mapper.DressMapper;
import com.tan.service.DressService;
import com.tan.service.LeaseRService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class DressServiceImpl implements DressService {
    @Autowired
    private DressMapper dressMapper;

    /**
     * 根据当前页码和每页需要展示的数据条数，查询最新上架的礼服信息
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult selectNewDresss(Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Dress> page=dressMapper.selectNewDresss();
        return new PageResult(page.getTotal(),page.getResult());
    }
/**
 * 根据id查询礼服信息
 * @param id 礼服id
 */
public Dress findById(String id) {
    return dressMapper.findById(id);
}

/**
 * 租赁礼服
 * @param dress
 * @return
 */
@Override
public Integer borrowDress(Dress dress) {
    //根据id查询出需要租赁的完整礼服信息
    Dress b = this.findById(dress.getId()+"");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //设置当天为租赁时间
    dress.setBorrowTime(dateFormat.format(new Date()));
    //设置所租赁的礼服状态为租赁中
    dress.setStatus("1");
    //将礼服的价格设置在dress对象中
    dress.setPrice(b.getPrice());
    //将礼服的上架设置在dress对象中
    dress.setUploadTime(b.getUploadTime());
    return dressMapper.editDress(dress);
}

/**
 * @param dress 封装查询条件的对象
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 */
@Override
public PageResult search(Dress dress, Integer pageNum, Integer pageSize) {
    // 设置分页查询的参数，开始分页
    PageHelper.startPage(pageNum, pageSize);
    Page<Dress> page=dressMapper.searchDresss(dress);
    return new PageResult(page.getTotal(),page.getResult());
}

/**
 * 新增礼服
 * @param dress 页面提交的新增礼服信息
 */
public Integer addDress(Dress dress) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //设置新增礼服的上架时间
    dress.setUploadTime(dateFormat.format(new Date()));
    return  dressMapper.addDress(dress);
}

/**
 * 编辑礼服信息
 * @param dress 礼服信息
 */
public Integer editDress(Dress dress) {
    return dressMapper.editDress(dress);
}

/**
 * 查询用户当前租赁的礼服
 * @param dress 封装查询条件的对象
 * @param consumer 当前登录用户
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 */
@Override
public PageResult searchBorrowed(Dress dress, Consumer consumer, Integer pageNum, Integer pageSize) {
    // 设置分页查询的参数，开始分页
    PageHelper.startPage(pageNum, pageSize);
    Page<Dress> page;
    //将当前登录的用户放入查询条件中
    dress.setBorrower(consumer.getName());
    //如果是管理员，查询自己租赁但未归还的礼服和所有待确认归还的礼服
    if("ADMIN".equals(consumer.getRole())){
        page= dressMapper.selectBorrowed(dress);
    }else {
        //如果是普通用户，查询自己租赁但未归还的礼服
        page= dressMapper.selectMyBorrowed(dress);
    }
    return new PageResult(page.getTotal(),page.getResult());
}

/**
 * 归还礼服
 * @param id 归还的礼服的id
 * @param consumer 归还的人员，也就是当前礼服的租赁者
 */
@Override
public boolean returnDress(String id,Consumer consumer) {
    //根据礼服id查询出礼服的完整信息
    Dress dress = this.findById(id);
    //再次核验当前登录人员和礼服租赁者是不是同一个人
    boolean rb=dress.getBorrower().equals(consumer.getName());
    //如果是同一个人，允许归还
    if(rb){
        //将礼服租赁状态修改为归还中
        dress.setStatus("2");
        dressMapper.editDress(dress);
    }
    return rb;
}
@Autowired
//注入LeaseRService对象
private LeaseRService leaseRService;
/**
 * 归还确认
 * @param id 待归还确认的礼服id
 */
@Override
public Integer returnConfirm(String id) {
    //根据礼服id查询礼服的完整信息
    Dress dress = this.findById(id);
    //根据归还确认的礼服信息，设置租赁记录
    LeaseR leaseR = this.setLeaseR(dress);
    //将礼服的租赁状态修改为可租赁
    dress.setStatus("0");
    //清除当前礼服的租赁人信息
    dress.setBorrower("");
    //清除当前礼服的租赁时间信息
    dress.setBorrowTime("");
    //清除当亲礼服的预计归还时间信息
    dress.setReturnTime("");
    Integer count= dressMapper.editDress(dress);
    //如果归还确认成功，则新增租赁记录
    if(count==1){
        return  leaseRService.addLeaseR(leaseR);
    }
    return 0;
}
/**
 * 根据礼服信息设置租赁记录的信息
 * @param dress 租赁的礼服信息
 */
private LeaseR setLeaseR(Dress dress){
    LeaseR leaseR=new LeaseR();
    //设置租赁记录的礼服名称
    leaseR.setDressname(dress.getName());
    //设置租赁记录的礼服isbn
    leaseR.setDressisbn(dress.getIsbn());
    //设置租赁记录的租赁人
    leaseR.setBorrower(dress.getBorrower());
    //设置租赁记录的租赁时间
    leaseR.setBorrowTime(dress.getBorrowTime());
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //设置礼服归还确认的当天为礼服归还时间
    leaseR.setRemandTime(dateFormat.format(new Date()));
    return leaseR;
}
}
