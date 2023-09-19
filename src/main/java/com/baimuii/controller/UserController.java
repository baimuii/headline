package com.baimuii.controller;

import com.baimuii.pojo.User;
import com.baimuii.service.UserService;
import com.baimuii.service.impl.UserServiceImpl;
import com.baimuii.utils.JwtHelper;
import com.baimuii.utils.Result;
import com.baimuii.utils.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.apache.el.parser.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private JwtHelper jwtHelper;

    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        System.out.println("result = " + result);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(@Param("username") String username){
        Result result = userService.checkUserName(username);
        System.out.println("result = " + result);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){

        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration){
            //已经过期了
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
