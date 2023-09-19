package com.baimuii.controller;

import com.baimuii.pojo.vo.PortalVo;
import com.baimuii.service.HeadlineService;
import com.baimuii.service.TypeService;
import com.baimuii.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("portal")
public class PortalController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.findAllTypes();
        System.out.println("result = " + result);
        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        System.out.println("result = " + result);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(@Param("hid") Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        System.out.println("result = " + result);
        return result;
    }
}
