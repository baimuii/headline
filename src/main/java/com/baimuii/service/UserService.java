package com.baimuii.service;

import com.baimuii.pojo.User;
import com.baimuii.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.el.parser.Token;

/**
* @author baimuii
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-09-19 17:15:41
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param user
     * @return
     */
    Result login(User user);


    /**
     * 获取用户信息
     * @param token
     * @return
     */
    Result getUserInfo(String token);
    /**
     * 检查用户名是否存在
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Result regist(User user);
}
