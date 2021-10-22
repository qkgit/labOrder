package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.entity.SDict;
import com.bdu.laborder.service.SDictService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @Author Qi
 * @data 2021/10/2 16:52
 */
@RestController
@RequestMapping("/dict")
public class SDictController extends BaseController {

    @Autowired
    SDictService sDictService;

    @PostMapping("/type/list")
    public Result getTypeList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SDict sDict = getParam(pageQuery, SDict.class);
        List<SDict> sDictPageInfo = sDictService.selectSDictTypeList(sDict);
        return getPageInfo(sDictPageInfo);
    }

    @PostMapping("/data/list")
    public Result getDataList(@RequestBody PageQuery pageQuery) {
        startPage(pageQuery);
        SDict sDict = getParam(pageQuery, SDict.class);
        List<SDict> dictPageInfo = sDictService.selectSDictList(sDict);
        return getPageInfo(dictPageInfo);
    }

    @GetMapping("/{dictId}")
    public Result getInfo(@PathVariable String dictId){
        SDict sDict = sDictService.selectSDictById(dictId);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,sDict);
    }

    public Result addDict(@RequestBody SDict sDict){
        // 判断code是否唯一
        if (sDictService.checkDictTypeUnique(sDict)){
            return error("新增字典'"+sDict.getName()+"'失败，字典类型已存在");
        }
        sDict.setCreateBy(getUserName());

        UUID uuid = UUID.randomUUID();
        sDict.setUuid(uuid.toString());
        int i = sDictService.insertSDict(sDict);
        return null;
    }
}
