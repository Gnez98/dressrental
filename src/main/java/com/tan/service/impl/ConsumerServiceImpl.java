package com.tan.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tan.domain.Dress;
import com.tan.domain.Consumer;
import com.tan.mapper.ConsumerMapper;
import com.tan.service.ConsumerService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *用户接口实现类
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {
    //注入consumerMapper
    @Autowired
    private ConsumerMapper consumerMapper;
    //通过Consumer的用户账号和用户密码查询用户信息
    @Override
    public Consumer login(Consumer consumer) {
        return consumerMapper.login(consumer);
    }

    /**
     * 新增用户
     * @param consumer 新增的用户信息
     */
    public void addConsumer(Consumer consumer) {
//        新增的用户 默认状态都设置为0,即在职状态
        consumer.setStatus("0");
        consumerMapper.addConsumer(consumer);
    }

    /**
     * 根据id办理用户离职
     * @param id 离职用户的id
     */
    public void delConsumer(Integer id) {
//        根据id查询出用户的完整信息
        Consumer consumer = this.findById(id);
//设置用户为离职状态
        consumer.setStatus("1");
//      设置当天为用户的离职时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        consumer.setDeparturedate(dateFormat.format(new Date()));
        consumerMapper.editConsumer(consumer);
    }
    public void delConsumer1(Integer id){
        consumerMapper.delConsumer1(id);
    }

    /**
     * 编辑用户信息
     * @param consumer 更新之后的用户信息
     */
    public void editConsumer(Consumer consumer) {
        consumerMapper.editConsumer(consumer);
    }

    /**
     * 搜索用户
     * @param consumer 搜索的条件
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return
     */
    @Override
    public PageResult searchConsumers(Consumer consumer, Integer pageNum, Integer pageSize) {
        // 使用分页插件:
        PageHelper.startPage(pageNum, pageSize);
        Page<Consumer> page =  consumerMapper.searchConsumers(consumer);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     */
    public Consumer findById(Integer id) {
        return consumerMapper.findById(id);
    }
    public Consumer findByName(String name) {
        return consumerMapper.findByName(name);
    }

    /**
     * 检查用户名是否已经存在
     * @param name 待检查的用户名
     */
    @Override
    public Integer checkName(String name) {
        return consumerMapper.checkName(name);
    }

    /**
     * 检查用户邮箱是否存储
     * @param email 待检查的用户邮箱
     */
    @Override
    public Integer checkEmail(String email) {
        return consumerMapper.checkEmail(email);
    }
}
