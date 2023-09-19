package com.baimuii.mapper;

import com.baimuii.pojo.Headline;
import com.baimuii.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author baimuii
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-09-19 17:15:41
* @Entity com.baimuii.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selcetMyPages(IPage page, @Param("portalVo") PortalVo portalVo);

    Map selectDetailMap(Integer hid);
}




