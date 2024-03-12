package com.example.sp.controller;

import com.example.sp.pojo.Result;
import com.example.sp.pojo.User;
import com.example.sp.service.UserService;
import com.example.sp.utils.JwtUtils;
import com.example.sp.utils.MD5Util;
import com.example.sp.utils.ThreadLocalUtils;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,20}$") String username, //对账号密码长度校验@Pattern(regexp = "^\\S{5,20}$")
                           @Pattern(regexp = "^\\S{5,20}$")String password){

            User u = userService.findByUserName(username);
            if (u==null){
                userService.register(username,password);
                return Result.success();
            }else {
                return Result.error("用户名已存在");
            }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,20}$") String username,
                                @Pattern(regexp = "^\\S{5,20}$")String password){
        User loginUser = userService.findByUserName(username);
        if (loginUser==null){
            return Result.error("用户名不存在");
        }
        if (MD5Util.getMd5(password).equals(loginUser.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtils.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    //查询
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name="Authorization")String token*/){
//        Map<String, Object> map = JwtUtils.parseToken(token);
//        String username = (String) map.get("username");
        Map<String,Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }


    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        //判断原密码是否正确
        //调用userService根据用户名拿到原密码，在和old_pwd比对
        Map<String,Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(MD5Util.getMd5(oldPwd))){
            return Result.error("原密码错误");
        }

        //新密码两次是否一致
        if (!newPwd.equals(rePwd)){
            return Result.error("两次输入密码不一致");
        }

        //调用service完成密码更新
        userService.updatePwd(newPwd);
        return Result.success();
    }
}
