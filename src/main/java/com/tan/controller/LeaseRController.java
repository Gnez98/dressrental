package com.tan.controller;
import com.tan.domain.Consumer;
import com.tan.domain.LeaseR;
import com.tan.service.LeaseRService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/leaseR")
public class LeaseRController {
    @Autowired
    private LeaseRService leaseRService;
/**
 * 查询租赁记录
 * @param leaseR 租赁记录的查询条件
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 */
@RequestMapping("/searchLeaseRs")
public ModelAndView searchLeaseRs(LeaseR leaseR, HttpServletRequest request, Integer pageNum, Integer pageSize){
    if(pageNum==null){
        pageNum=1;
    }
    if(pageSize==null){
        pageSize=5;
    }
    //获取当前登录用户的信息
    Consumer consumer = ((Consumer) request.getSession().getAttribute("Consumer_SESSION"));
    PageResult pageResult=leaseRService.searchLeaseRs(leaseR, consumer,pageNum,pageSize);
    ModelAndView modelAndView=new ModelAndView();
    modelAndView.setViewName("leaseR");
    //将查询到的数据存放在 ModelAndView的对象中
    modelAndView.addObject("pageResult",pageResult);
    //将查询的参数返回到页面，用于回显到查询的输入框中
    modelAndView.addObject("search", leaseR);
    //将当前页码返回到页面，用于分页插件的分页显示
    modelAndView.addObject("pageNum",pageNum);
    //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
    modelAndView.addObject("gourl", request.getRequestURI());
    return modelAndView;
}
}

