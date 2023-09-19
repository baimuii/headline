package com.baimuii.service.impl;

import com.baimuii.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baimuii.pojo.Type;
import com.baimuii.service.TypeService;
import com.baimuii.mapper.TypeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author baimuii
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-09-19 17:15:41
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{
    @Resource
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> typeList = typeMapper.selectList(null);
        return Result.ok(typeList);
    }

}




