package com.ccwme.bugmanager.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccwme.bugmanager.Bean.Users;
import com.ccwme.bugmanager.Service.Users.UserService;
import com.ccwme.bugmanager.Util.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class UsersController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Message first(HttpServletRequest request,@RequestBody Users user){
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("uname",user.getUname());
        List<Users> list = userService.list(usersQueryWrapper);
        if(list.size()>0){
            if(list.get(0).getPw().equals(user.getPw())){
                System.out.println(request.getSession());
                System.out.println(request.getSession().getAttribute("uid"));
                System.out.println("uid为"+list.get(0).getUid());
                request.getSession().setAttribute("uid",list.get(0).getUid());
                System.out.println(request.getSession().getAttribute("uid"));
                request.getSession().setAttribute("uname",list.get(0).getUname());
                return Message.success("login success");
            }
            return Message.fail("password error");
        }
        return Message.fail("no this user");
    }
    @PostMapping("/register")
    @ResponseBody
    public Message register(@RequestBody Users user){
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("uname",user.getUname());
        List<Users> list = userService.list(usersQueryWrapper);
        if(list.size()>0){
            return Message.fail("用户名已注册");
        }
        String uid = UUID.randomUUID().toString();
        user.setUid(uid);
        user.setState("0");
        boolean save = userService.save(user);
        if(save){
            return Message.success("注册成功请登录");
        }
        System.out.println(user);
        return Message.fail("注册失败，请稍后再试");
    }
    @RequestMapping("/loginPage")
    public String loginPage(HttpServletRequest request){
        return "loginPage.html";
    }
    @RequestMapping("/indexPage")
    public String indexPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        if(null==request.getSession().getAttribute("uid")){
            response.sendRedirect("/loginPage");
        }
        return "indexPage.html";
    }
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Message getUserInfo(HttpServletRequest request){
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("uid",request.getSession().getAttribute("uid"));
        List<Users> list = userService.list(usersQueryWrapper);
        return Message.success(list.get(0));
    }
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/indexPage");
    }
}
