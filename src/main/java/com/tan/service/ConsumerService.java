package com.tan.service;
import com.tan.domain.Consumer;
import entity.PageResult;

/**
 *用户接口
 */
public interface ConsumerService {
    //通过Consumer的用户账号和用户密码查询用户信息
    Consumer login(Consumer consumer);
//添加用户
    void addConsumer(Consumer consumer);
//根据用户id办理用户离职
    void delConsumer(Integer id);
    void delConsumer1(Integer id);
//编辑用户信息
    void editConsumer(Consumer consumer);
//搜索用户
    PageResult searchConsumers(Consumer consumer, Integer pageNum, Integer pageSize);
//根据id查询用户信息
    Consumer findById(Integer id);
    //根据用户名查询用户信息
    Consumer findByName(String name);

    Integer checkName(String name);

    Integer checkEmail(String email);
}
