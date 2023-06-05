package com.tan.service;
import com.tan.domain.Dress;
import com.tan.domain.Consumer;
import entity.PageResult;
/**
 * 礼服接口
 */
public interface DressService {
//查询最新上架的礼服
PageResult selectNewDresss(Integer pageNum, Integer pageSize);
//根据id查询礼服信息
Dress findById(String id);
//租赁礼服
Integer borrowDress(Dress dress);
//分页查询礼服
PageResult search(Dress dress, Integer pageNum, Integer pageSize);
//新增礼服
Integer addDress(Dress dress);
//编辑礼服信息
Integer editDress(Dress dress);
//查询当前租赁的礼服
PageResult searchBorrowed(Dress dress, Consumer consumer, Integer pageNum, Integer pageSize);
//归还礼服
boolean returnDress(String  id,Consumer consumer);
//归还确认
Integer returnConfirm(String id);

}
