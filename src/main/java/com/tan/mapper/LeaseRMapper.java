package com.tan.mapper;
import com.github.pagehelper.Page;
import com.tan.domain.LeaseR;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface LeaseRMapper {
//新增租赁记录
Integer addLeaseR(LeaseR leaseR);
@Select({"<script>" +
        "SELECT * FROM leaser " +
        "where 1=1" +
        "<if test=\"borrower != null\">AND leaseR_borrower like  CONCAT('%',#{borrower},'%')</if>" +
        "<if test=\"dressname != null\">AND leaseR_dressname  like  CONCAT('%',#{dressname},'%') </if>" +
        "order by leaseR_remandtime DESC" +
        "</script>"
})
@Results(id = "leaseRMap",value = {
        //id字段默认为false，表示不是主键
        //column表示数据库表字段，property表示实体类属性名。
        @Result(id = true,column = "leaseR_id",property = "id"),
        @Result(column = "leaseR_dressname",property = "dressname"),
        @Result(column = "leaseR_dressisbn",property = "dressisbn"),
        @Result(column = "leaseR_borrower",property = "borrower"),
        @Result(column = "leaseR_borrowtime",property = "borrowTime"),
        @Result(column = "leaseR_remandtime",property = "remandTime")
})
//查询租赁记录
Page<LeaseR> searchLeaseRs(LeaseR leaseR);
}
