package com.tan.mapper;
import com.github.pagehelper.Page;
import com.tan.domain.Dress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 礼服接口
 */
@Repository
public interface DressMapper {
    @Select("SELECT * FROM dress where dress_status !='3' order by dress_uploadtime DESC")
    @Results(id = "dressMap",value = {
            //id字段默认为false，表示不是主键
            //column表示数据库表字段，property表示实体类属性名。
            @Result(id = true,column = "dress_id",property = "id"),
            @Result(column = "dress_name",property = "name"),
            @Result(column = "dress_isbn",property = "isbn"),
            @Result(column = "dress_press",property = "press"),
            @Result(column = "dress_author",property = "author"),
            @Result(column = "dress_pagination",property = "pagination"),
            @Result(column = "dress_price",property = "price"),
            @Result(column = "dress_uploadtime",property = "uploadTime"),
            @Result(column = "dress_status",property = "status"),
            @Result(column = "dress_borrower",property = "borrower"),
            @Result(column = "dress_borrowtime",property = "borrowTime"),
            @Result(column = "dress_returntime",property = "returnTime")
    })
    Page<Dress> selectNewDresss();


@Select("SELECT * FROM dress where dress_id=#{id}")
@ResultMap("dressMap")
//根据id查询礼服信息
Dress findById(String id);

    @Select({"<script>" +
            "SELECT * FROM dress " +
            "where dress_status !='3'" +
            "<if test=\"name != null\"> AND  dress_name  like  CONCAT('%',#{name},'%')</if>" +
            "<if test=\"press != null\"> AND dress_press like  CONCAT('%', #{press},'%') </if>" +
            "<if test=\"author != null\"> AND dress_author like  CONCAT('%', #{author},'%')</if>" +
            "order by dress_borrowtime" +
            "</script>"
    })
    @ResultMap("dressMap")
    //分页查询礼服
    Page<Dress> searchDresss(Dress dress);
    //新增礼服
    Integer addDress(Dress dress);
//编辑礼服信息
    Integer editDress(Dress dress);


@Select(
        {"<script>" +
                "SELECT * FROM dress " +
                "where dress_borrower=#{borrower}" +
                "AND dress_status ='1'"+
                "<if test=\"name != null\"> AND  dress_name  like  CONCAT('%',#{name},'%')</if>" +
                "<if test=\"press != null\"> AND dress_press like  CONCAT('%', #{press},'%') </if>" +
                "<if test=\"author != null\"> AND dress_author like  CONCAT('%', #{author},'%')</if>" +
                "or dress_status ='2'"+
                "<if test=\"name != null\"> AND  dress_name  like  CONCAT('%',#{name},'%')</if>" +
                "<if test=\"press != null\"> AND dress_press like  CONCAT('%', #{press},'%') </if>" +
                "<if test=\"author != null\"> AND dress_author like  CONCAT('%', #{author},'%')</if>" +
                "order by dress_borrowtime" +
                "</script>"})
@ResultMap("dressMap")
//查询租赁但未归还的礼服和待归还确认的礼服
Page<Dress> selectBorrowed(Dress dress);

@Select({"<script>"  +
        "SELECT * FROM dress " +
        "where dress_borrower=#{borrower}" +
        "AND dress_status in('1','2')"+
        "<if test=\"name != null\"> AND  dress_name  like  CONCAT('%',#{name},'%')</if>" +
        "<if test=\"press != null\"> AND dress_press like  CONCAT('%', #{press},'%') </if>" +
        "<if test=\"author != null\"> AND dress_author like  CONCAT('%', #{author},'%')</if>" +
        "order by dress_borrowtime" +
        "</script>"})
@ResultMap("dressMap")
//查询租赁但未归还的礼服
Page<Dress> selectMyBorrowed(Dress dress);

}
