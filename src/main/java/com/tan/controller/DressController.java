package com.tan.controller;
import com.tan.domain.Consumer;
import com.tan.domain.Dress;
import com.tan.service.DressService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
礼服信息Controller
 */
@Controller
@RequestMapping("/dress")
public class DressController {
    //注入dressService对象
    @Autowired
    private DressService dressService;
    /**
     * 查询最新上架的礼服
     */
    @RequestMapping("/selectNewDresss")
    public ModelAndView selectNewDresss() {
        //查询最新上架的5个的礼服信息
        int pageNum = 1;
        int pageSize = 5;
        PageResult pageResult = dressService.selectNewDresss(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dresss_new");
        modelAndView.addObject("pageResult", pageResult);
        return modelAndView;
    }
/**
 * 根据礼服id查询礼服信息
 * @param id 查询的礼服id
 */
@ResponseBody
@RequestMapping("/findById")
public Result<Dress> findById(String id) {
    try {
        Dress dress=dressService.findById(id);
        if(dress==null){
            return new Result(false,"查询礼服失败！");
        }
        return new Result(true,"查询礼服成功",dress);
    }catch (Exception e){
        e.printStackTrace();
        return new Result(false,"查询礼服失败！");
    }
}
/**
 * 租赁礼服
 * @param dress 租赁的礼服
 * @return
 */
@ResponseBody
@RequestMapping("/borrowDress")
public Result borrowDress(Dress dress, HttpSession session) {
    //获取当前登录的用户姓名
    String pname = ((Consumer) session.getAttribute("Consumer_SESSION")).getName();
    dress.setBorrower(pname);
    try {
        //根据礼服的id和用户进行礼服租赁
        Integer count = dressService.borrowDress(dress);
        if (count != 1) {
            return new Result(false, "租赁礼服失败!");
        }
        return new Result(true, "租赁成功，请到七七工作室取礼服!");
    } catch (Exception e) {
        e.printStackTrace();
        return new Result(false, "租赁礼服失败!");
    }
}

/**
 * 分页查询符合条件且未下架礼服信息
 * @param dress 查询的条件封装到dress中
 * @param pageNum  数据列表的当前页码
 * @param pageSize 数据列表1页展示多少条数据
 */
@RequestMapping("/search")
public ModelAndView search(Dress dress, Integer pageNum, Integer pageSize, HttpServletRequest request) {
    if (pageNum == null) {
        pageNum = 1;
    }
    if (pageSize == null) {
        pageSize = 5;
    }
    //查询到的礼服信息
    PageResult pageResult = dressService.search(dress, pageNum, pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("dresss");
    //将查询到的数据存放在 ModelAndView的对象中
    modelAndView.addObject("pageResult", pageResult);
    //将查询的参数返回到页面，用于回显到查询的输入框中
    modelAndView.addObject("search", dress);
    //将当前页码返回到页面，用于分页插件的分页显示
    modelAndView.addObject("pageNum", pageNum);
    //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
    modelAndView.addObject("gourl", request.getRequestURI());
    return modelAndView;
}

/**
 * 新增礼服
 * @param dress 页面表单提交的礼服信息
 * 将新增的结果和向页面传递信息封装到Result对象中返回
 */
@ResponseBody
@RequestMapping("/addDress")
public Result addDress(Dress dress) {
    try {
        Integer count=dressService.addDress(dress);
        if(count!=1){
            return new Result(false, "新增礼服失败!");
        }
        return new Result(true, "新增礼服成功!");
    }catch (Exception e){
        e.printStackTrace();
        return new Result(false, "新增礼服失败!");
    }
}

/**
 * 编辑礼服信息
 * @param dress 编辑的礼服信息
 */
@ResponseBody
@RequestMapping("/editDress")
public Result editDress(Dress dress) {
    try {
        Integer count= dressService.editDress(dress);
        if(count!=1){
            return new Result(false, "编辑失败!");
        }
        return new Result(true, "编辑成功!");
    }catch (Exception e){
        e.printStackTrace();
        return new Result(false, "编辑失败!");
    }
}

/**
 *分页查询当前被租赁且未归还的礼服信息
 * @param pageNum  数据列表的当前页码
 * @param pageSize 数据列表1页展示多少条数据
 */
@RequestMapping("/searchBorrowed")
public ModelAndView searchBorrowed(Dress dress, Integer pageNum, Integer pageSize, HttpServletRequest request) {
    if (pageNum == null) {
        pageNum = 1;
    }
    if (pageSize == null) {
        pageSize = 5;
    }
    //获取当前登录的用户
    Consumer consumer = (Consumer) request.getSession().getAttribute("Consumer_SESSION");
    PageResult pageResult = dressService.searchBorrowed(dress, consumer, pageNum, pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("dress_borrowed");
    //将查询到的数据存放在 ModelAndView的对象中
    modelAndView.addObject("pageResult", pageResult);
    //将查询的参数返回到页面，用于回显到查询的输入框中
    modelAndView.addObject("search", dress);
    //将当前页码返回到页面，用于分页插件的分页显示
    modelAndView.addObject("pageNum", pageNum);
    //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
    modelAndView.addObject("gourl", request.getRequestURI());
    return modelAndView;
}
/**
 * 归还礼服
 * @param id 归还的礼服的id
 */
@ResponseBody
@RequestMapping("/returnDress")
public Result returnDress(String id, HttpSession session) {
    //获取当前登录的用户信息
    Consumer consumer = (Consumer) session.getAttribute("Consumer_SESSION");
    try {
        boolean flag = dressService.returnDress(id, consumer);
        if (!flag) {
            return new Result(false, "归还失败!");
        }
        return new Result(true, "归还确认中，请先到七七工作室归还!");
    }catch (Exception e){
        e.printStackTrace();
        return new Result(false, "归还失败!");
    }
}

/**
 * 确认礼服归还
 * @param id 确认归还的礼服的id
 */
@ResponseBody
@RequestMapping("/returnConfirm")
public Result returnConfirm(String id) {
    try {
        Integer count=dressService.returnConfirm(id);
        if(count!=1){
            return new Result(false, "确认失败!");
        }
        return new Result(true, "确认成功!");
    }catch (Exception e){
        e.printStackTrace();
        return new Result(false, "确认失败!");
    }
}

}

