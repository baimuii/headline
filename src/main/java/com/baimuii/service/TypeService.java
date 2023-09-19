package com.baimuii.service;

import com.baimuii.pojo.Type;
import com.baimuii.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author baimuii
* @description 针对表【news_type】的数据库操作Service
* @createDate 2023-09-19 17:15:41
*/
public interface TypeService extends IService<Type> {
    /**
     * 查询所有头条类别
     * @return
     */
    Result findAllTypes();

}
