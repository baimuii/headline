package com.baimuii.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baimuii.utils.JwtHelper;
import com.baimuii.utils.MD5Util;
import com.baimuii.utils.Result;
import com.baimuii.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baimuii.pojo.User;
import com.baimuii.service.UserService;
import com.baimuii.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author baimuii
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2023-09-19 17:15:41
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtHelper jwtHelper;

    /**
     * 用户登录
     * @param user
     * @return
     *
     * 1.根据用户id从数据库获取对象
     * 2.是否存在对象
     * 3.如果存在则对比账号密码,对比Md5加密以后的密码
     * 4.如果登录成功，返回成功码+token
     * 5.如果失败则返回对应的失败码
     */
    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginuser = userMapper.selectOne(lambdaQueryWrapper);
        if(loginuser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        if(!StringUtils.isEmpty(user.getUserPwd())
            && loginuser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))){
            String token = jwtHelper.createToken(Long.valueOf(loginuser.getUid()));
            Map data = new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }

        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根据token获取用户数据
     * @param token
     * @return
     * 1.根据token获取用户id
     * 2.根据用户id查询用户数据即可
     * 3.成功则返回成功和用户数据
     * 4.失败则返回失败码
     */
    @Override
    public Result getUserInfo(String token) {
        if(jwtHelper.isExpiration(token)){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        user.setUserPwd(null);
        Map data = new HashMap();
        data.put("loginUser",user);
        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        Long count = userMapper.selectCount(lambdaQueryWrapper);
        if(count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);

        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }
}




