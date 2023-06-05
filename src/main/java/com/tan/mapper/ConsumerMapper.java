package com.tan.mapper;
import com.github.pagehelper.Page;
import com.tan.domain.Consumer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


/**
 * 用户操作接口
 */
@Repository
public interface ConsumerMapper {
    @Select("select * from consumer where consumer_email=#{email} AND consumer_password=#{password} AND consumer_status!='1'")
    @Results(id = "consumerMap",value = {
            //id字段默认为false，表示不是主键
            //column表示数据库表字段，property表示实体类属性名。
            @Result(id = true,column = "consumer_id",property = "id"),
            @Result(column = "consumer_name",property = "name"),
            @Result(column = "consumer_password",property = "password"),
            @Result(column = "consumer_email",property = "email"),
            @Result(column = "consumer_hiredate",property = "hiredate"),
            @Result(column = "consumer_role",property = "role"),
            @Result(column = "consumer_status",property = "status"),
            @Result(column = "consumer_departuredate",property = "departuredate")
    })
    //用户登录
    Consumer login(Consumer consumer);
    //新增用户
    void addConsumer(Consumer consumer);
    //编辑用户信息
    void editConsumer(Consumer consumer);
    //删除已离职用户信息
    @Delete(" delete from consumer where consumer_id=#{id}")
    void delConsumer1(Integer id);

    @Select({"<script>" +
            "SELECT * FROM consumer " +
            "where 1=1 " +
            "<if test=\"id != null\"> AND  consumer_id  like  CONCAT('%',#{id},'%')</if>" +
            "<if test=\"name != null\"> AND consumer_name like  CONCAT('%', #{name},'%') </if>" +
            "order by consumer_status" +
            "</script>"
    })
    @ResultMap("consumerMap")
    //搜索用户
    Page<Consumer> searchConsumers(Consumer consumer );

    @Select(" select * from consumer where consumer_id=#{id}")
    @ResultMap("consumerMap")
    //根据用户id查询用户信息
    Consumer findById(Integer id);

    @Select(" select * from consumer where consumer_name=#{name}")
    @ResultMap("consumerMap")
        //根据用户名查询用户信息
    Consumer findByName(String name);

    @Select("select count(consumer_name) from consumer where consumer_name=#{name}")
    //检查用户名是否已经存在
    Integer checkName(String name);

    @Select("select count(consumer_email) from consumer where consumer_email=#{email}")
    //检查用户邮箱是否已经存在
    Integer checkEmail(String email);
}
