package com.baimuii.service.impl;

import com.baimuii.pojo.vo.PortalVo;
import com.baimuii.utils.JwtHelper;
import com.baimuii.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baimuii.pojo.Headline;
import com.baimuii.service.HeadlineService;
import com.baimuii.mapper.HeadlineMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author baimuii
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-09-19 17:15:41
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Resource
    private HeadlineMapper headlineMapper;
    @Resource
    private JwtHelper jwtHelper;
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        IPage<Headline> headlineIPage = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selcetMyPages(headlineIPage,portalVo);
        List<Headline> records = headlineIPage.getRecords();
        Map data = new HashMap();
        data.put("pageData",records);
        data.put("pageNum",headlineIPage.getCurrent());
        data.put("pageSize",headlineIPage.getSize());
        data.put("totalPage",headlineIPage.getPages());
        data.put("totalSize",headlineIPage.getTotal());
        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);
        return Result.ok(pageInfo);
    }

    @Override
    public Result showHeadlineDetail(Integer hid) {
        //1.实现根据id的查询(多表
        Map headLineDetail = headlineMapper.selectDetailMap(hid);
        //2.拼接头条对象(阅读量和version)进行数据更新
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setPageViews((Integer) headLineDetail.get("pageViews")+1); //阅读量+1
        headline.setVersion((Integer) headLineDetail.get("version")); //设置版本
        headlineMapper.updateById(headline);

        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("headline",headLineDetail);
        return Result.ok(pageInfoMap);
    }

    @Override
    public Result publish(Headline headline, String token) {
        //token查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        //数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version); //乐观锁
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




