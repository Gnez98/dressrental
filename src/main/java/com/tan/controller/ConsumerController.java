package com.tan.controller;

import com.tan.domain.Consumer;
import com.tan.service.ConsumerService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登录和注销Controller
 */
@Controller
@RequestMapping("/consumer")
public class ConsumerController {
    //注入ConsumerService
    @Autowired
    private ConsumerService consumerService;
    /*
   用户登录
    */
    @RequestMapping("/login")
    public String login(Consumer consumer, HttpServletRequest request) {
        try {
            Consumer u = consumerService.login(consumer);
            /*
            用户账号和密码是否查询出用户信息
                是：将用户信息存入Session，并跳转到后台首页
                否：Request域中添加提示信息，并转发到登录页面
             */
            if (u != null) {
                request.getSession().setAttribute("Consumer_SESSION", u);
                String role = u.getRole();
                if ("ADMIN".equals(role)) {
                    return "redirect:/admin/main.jsp";
                } else {
                    return "redirect:/admin/index.jsp";
                }

            }
            request.setAttribute("msg", "用户名或密码错误");
            return "forward:/admin/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/admin/login.jsp";
        }
    }

    /*
    注销登录
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            //销毁Session
            session.invalidate();
            return "forward:/admin/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/admin/login.jsp";
        }
    }

    /**
     * 新增用户
     * @param consumer 新增的用户信息
     */
    @ResponseBody
    @RequestMapping("/addConsumer")
    public Result addConsumer(Consumer consumer) {
        try {
            consumerService.addConsumer(consumer);
            return new Result(true, "新增成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增失败!");
        }
    }

    /**
     * 办理用户离职
     * @param id 离职的用户id
     */
    @ResponseBody
    @RequestMapping("/delConsumer")
    public Result delConsumer(Integer id) {
        try {
            consumerService.delConsumer(id);
            return new Result(true, "离职办理成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "离职办理失败!");
        }
    }

    /**
     * 办理已离职用户的删除
     * @param id 离职的用户id
     */
    @ResponseBody
    @RequestMapping("/delConsumer1")
    public Result delConsumer1(Integer id) {
        try {
            consumerService.delConsumer1(id);
            return new Result(true, "删除办理成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除办理失败!");
        }
    }

    /**
     * 修改用户信息
     * @param consumer 修改的用户信息
     */
    @ResponseBody
    @RequestMapping("/editConsumer")
    public Result editConsumer(Consumer consumer) {
        try {
            consumerService.editConsumer(consumer);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    /**
     * 查询用户
     * @param consumer 查询的条件
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/search")
    public ModelAndView search(Consumer consumer, Integer pageNum, Integer pageSize , HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        PageResult pageResult = consumerService.searchConsumers(consumer, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("consumer");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("search", consumer);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    /**
     * 根据id查询用户
     * @param id 用户id，用作查询条件
     */
    @ResponseBody
    @RequestMapping("/findById")
    public Consumer findById(Integer id) {
        return consumerService.findById(id);
    }

    /**
     * 根据name查询用户
     * @param name 用户name，用作查询条件
     */
    @ResponseBody
    @RequestMapping("/findByName")
    public Consumer findByName(String name) {
        return consumerService.findByName(name);
    }

    /**
     * 检查用户名称是否已经存在
     * @param name 用户名称
     */
    @ResponseBody
    @RequestMapping("/checkName")
    public Result checkName(String name) {
        Integer count = consumerService.checkName(name);
        if (count > 0) {
            return new Result(false, "名字重复!");
        } else {
            return new Result(true, "名字可用!");
        }
    }

    /**
     * 校验用户的邮箱是否已经存在
     * @param email 被校验的用户邮箱
     */
    @ResponseBody
    @RequestMapping("/checkEmail")
    public Result checkEmail(String email) {
        Integer count = consumerService.checkEmail(email);
        if (count > 0) {
            return new Result(false, "邮箱重复!");
        } else {
            return new Result(true, "邮箱可用!");
        }
    }
}
