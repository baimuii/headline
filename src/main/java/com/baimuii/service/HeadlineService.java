package com.baimuii.service;

import com.baimuii.pojo.Headline;
import com.baimuii.pojo.vo.PortalVo;
import com.baimuii.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author baimuii
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-09-19 17:15:41
*/
public interface HeadlineService extends IService<Headline> {
    /**
     * 查询首页头条信息
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateData(Headline headline);
}
